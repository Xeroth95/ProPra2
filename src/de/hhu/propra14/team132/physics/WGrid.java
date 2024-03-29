package de.hhu.propra14.team132.physics;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import de.hhu.propra14.team132.gameMechanics.Map;
// this is kind of like a tree structrure.
// There are several levels of Collision
// It uses multi-dimensional arrays as associative memory using the IDs as hashes that are directly mapped at array Positions
// Just one instance of this class will waste Memory like hell.
// It uses CollThreads to actually calculate the Collision.
public strictfp class WGrid extends CollisionSystem{
	
	class CollThread extends Thread{
		CountDownLatch startSignal;
		CountDownLatch doneSignal;
		CountDownLatch nextTickSignal;
		
		int start;
		int end;
		boolean ready;
		public CollThread(CountDownLatch startSignal,CountDownLatch doneSignal,CountDownLatch nextTickSignal,WGrid w){
			this.startSignal=startSignal;
			this.doneSignal=doneSignal;
			this.nextTickSignal=nextTickSignal;

			ready=false;
			this.start=0;
			this.end=0;
		}
		
		@Override
		public void run(){
			try {
				while(true){
						//wait for the start signal latch
						startSignal.await();
						//The wait is over!
						callCollisionsSyncedOwn4();
						doneSignal.countDown();
						nextTickSignal.await();
				}
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
			
				
		}

		private void callCollisionsSyncedOwn4(){
			for(int x=start;x<end;x++){
				int i=activeCells.get(x);
				int[] cellsICache=cells[i];
				for(int a=0;cellsICache[a]!=0;a++){
					CollisionObject o1=objects[cellsICache[a]];
					synchronized(o1){
						if(!o1.isMarkedForDeletion()){
							//self check
							for(int self=a+1;cellsICache[self]!=0;self++){
								CollisionObject oSelf=objects[cellsICache[self]];
								synchronized(oSelf){
									if(!oSelf.isMarkedForDeletion())o1.collideWithCheckTeam(oSelf);
								}
							}
							for(int e=0; e<magic4ofI[i].length;e++){
								//check the 5 important cells and their parents
								//check them
								for(int q=0;cells[magic4ofI[i][e]][q]!=0;q++){
									//i!=e!!! else there is redundant testing!
									//reduce magic5 to magic 4 + self test
									//every object in those cells
									CollisionObject oM4=objects[cells[magic4ofI[i][e]][q]];//wow, I never thought I might write something that ugly someday.
									synchronized(oM4){
										if(!oM4.isMarkedForDeletion()) o1.collideWithCheckTeam(oM4);
									}
								}
							}
							for(int p=0;parentsOfI[i][p]!=0;p++){
//								System.out.println("parent of Cell "+i+" is cell "+parentsOfI[i][p]);
								for(int q=0;cells[parentsOfI[i][p]][q]!=0;q++){
									//every object in those cells
									CollisionObject oParent=objects[cells[parentsOfI[i][p]][q]];
									synchronized(oParent){
										if(!oParent.isMarkedForDeletion())o1.collideWithCheckTeam(oParent);
									}
								}
							}
						}
					}
				}
				
				
			}
		}
		
		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

	}
	
	//now we are back from the CollThread to the Grid
	
	final static int MAX_OBJECT_COUNT_PER_CELL=100;
	

	private CountDownLatch startSignal;
	private CountDownLatch doneSignal;
	
	double width;
	double length;
	CollisionObject[] objects;
	
	ArrayList<Integer> IDs;
	
	static final double LN2=Math.log(2);
	static final double ONE_OVER_LN2=1/LN2;
	
	int subLevels;
	int rowcount;
	int resultingCells;
	ArrayList<Integer> activeCells;
	final int[][] cells;
	final int[] cellsBeforeLevel;
	final int[] linecountAtLevel;
	final int[][] magic4ofI;
	final int[] levelOfI;
	final int[][] parentsOfI;
	
	Map map;
	
	static final int numberOfProcessors=Runtime.getRuntime().availableProcessors()*2;
	
	final CollThread[] collThreads;

	private CountDownLatch nextTickSignal;
	
	public WGrid(double width,double length,int subLevels,Map map,ArrayList<Integer> IDs){
		this.width=width;
		this.length=length;
		this.subLevels=subLevels;
		this.map=map;
		this.IDs=IDs;
		this.objects=map.getMapObjects();
		
		activeCells=new ArrayList<Integer>(500);
		for(int i=0;i<this.subLevels;i++){
			resultingCells+=(int) Math.round(Math.pow(4, i))+1;
		}
		cellsBeforeLevel=new int[this.subLevels+1];
		linecountAtLevel=new int[this.subLevels+1];
		
		
		magic4ofI=new int[this.resultingCells+1][4];
		this.levelOfI=new int[this.resultingCells+1];
		
		int help=0;
		for(int i=0;i<this.subLevels;i++){
			help+=(int) Math.round(Math.pow(4, i));
			cellsBeforeLevel[i+1]=help; 
			linecountAtLevel[i]=(int) Math.round(Math.pow(2, i));
		}
		
		this.parentsOfI=new int[this.resultingCells][4*this.subLevels];
		for(int i=0;i<this.resultingCells;i++){
			for(int j=0;j<this.parentsOfI[i].length;j++){
				this.parentsOfI[i][j]=0; 
			}
		}
		
		for(int i=0,cellsAtThisLevel=1;i<this.subLevels;i++,cellsAtThisLevel*=4){
			//for every level
			for(int j=0;j<cellsAtThisLevel;j++){
				//for every cell in the level
		
				boolean firstRow=j<this.linecountAtLevel[i];
				boolean lastRow=cellsAtThisLevel-j<=this.linecountAtLevel[i];
				boolean secondLastRow=cellsAtThisLevel-j<=this.linecountAtLevel[i]*2;
				
				boolean lastCellInRow=false;
				if(i==0&&j==0){
					lastCellInRow=true;
				}
				if(j!=0){
					lastCellInRow=(this.linecountAtLevel[i]%j)-1==0;
				}
				
				if(lastCellInRow){
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][0]=0;
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][1]=0;
				}
				else{
					//[0] can accedentally jump to the level above !!
					this.magic4ofI[this.cellsBeforeLevel[i] +j][0]=0;
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][0]=(this.cellsBeforeLevel[i]) + j-this.linecountAtLevel[i]+1; //if i is the last cell in a row, this will make it jump to the beginning of the current row!!
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][1]=(this.cellsBeforeLevel[i]) + j+1; //if i is the last cell in a row, this will make it jump to the beginning of the next row!!
				}
				if(firstRow){
					//overwrite [0]'s value if it jumped to the level above.
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][0]=0;
				}
				
				if((secondLastRow&&lastCellInRow) | lastCellInRow ){
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][2]=0;
				}
				else{
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][2]=(this.cellsBeforeLevel[i]) + j+this.linecountAtLevel[i]+1; //if i is the last cell in a row, this will make it jump to the beginning of the row after the next row!!	
				}
				
				if(lastRow){
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][3]=0;
				}
				else{
					this.magic4ofI[(this.cellsBeforeLevel[i]) + j][3]=(this.cellsBeforeLevel[i]) + j+this.linecountAtLevel[i]; //if i is the last cell in a collumn, this will jump out of range, causing an exception!! Good this isn't c or c++, otherwise I would have to fix this earlier.
				}
				this.levelOfI[this.cellsBeforeLevel[i]+j]=i; //this is perfectly fine
			}
		}
		this.levelOfI[resultingCells]=this.levelOfI[resultingCells-1];

		//now the probably really ugly new version of the sort-in of the parent cells!
		for(int i=0;i<this.cellsBeforeLevel[this.subLevels];i++){
			//add the parents and their parents! This sounds like recursion, but we can make that in an iterative manner because we begin working at the top
			if(i==0)continue;
			ArrayList<int[]> parentsOfneighbors=new ArrayList<>();
			int currentlevel=this.levelOfI[i];
			boolean firstRow=(i-this.cellsBeforeLevel[currentlevel]-1)/this.linecountAtLevel[currentlevel]==0;
			boolean lastRow=(i-this.cellsBeforeLevel[currentlevel]-1)/this.linecountAtLevel[currentlevel]==this.linecountAtLevel[currentlevel]-1;
			boolean firstColumn=(i-this.cellsBeforeLevel[currentlevel]-1)%this.linecountAtLevel[currentlevel]==0;
			boolean lastColumn=(i-this.cellsBeforeLevel[currentlevel]-1)%this.linecountAtLevel[currentlevel]==this.linecountAtLevel[currentlevel]-1;
			wut:
			if(firstColumn){//then do not try to put the parents of the cells left of it into the array
				if(firstRow){//don't put the upper ones into it
					parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]+1));
					break wut;//this turns out to be one hell of spaghetti code once it's finished
				}
				if(lastRow){//only the upper 
					parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]+1));
					break wut;
				}
				//ok, both!
				
				parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]+1));
				parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]+1));
			}
			wut2:
			if(lastColumn){//then do not try to put the parents of the cells right of it into the array
				if(firstRow){//don't put the upper ones into it
					parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]-1));
					break wut2;
				}
				if(lastRow){//only the upper 
					parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]-1));
					break wut2;
				}
				//ok, both!
				parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]-1));
				parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]-1));
			}
			wut3:
			if(!firstColumn&&!lastColumn){//do not care for left/right just upper and lower
				if(firstRow){//don't put the upper ones into it
					parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]-1));
					parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]+1));
					break wut3;
				}
				if(lastRow){//only the upper 
					parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]-1));
					parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]+1));
					break wut3;
				}
				//add all four!
				
				parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]-1));
				parentsOfneighbors.add(getParentsOfCell(i+linecountAtLevel[currentlevel]+1));
				parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]-1));
				parentsOfneighbors.add(getParentsOfCell(i-linecountAtLevel[currentlevel]+1));
			}
			
			//now put the elements of the int[]s in parentsOfI[]. check for doubles!
			
			for (int j = 0; j < parentsOfneighbors.size(); j++) {
				
				for (int j2 = 0; j2 < parentsOfneighbors.get(j).length; j2++) {
					int parent=parentsOfneighbors.get(j)[j2];
					boolean parentAlreadySortedIn = false;
					int pos = 0;
					for (int a = 0; parentsOfI[i-1][a] != 0; a++, pos = a) { // go through until there is a free entry
						if (parentsOfI[i-1][a] == parent) {// is this parentCell already in there???
							parentAlreadySortedIn = true;
						}
					}
					if (!parentAlreadySortedIn) {// if it is in there don't put it in there multiple times!
						parentsOfI[i-1][pos] = parent;
					}
				}
			}
			
		}
		//that's it! does it work?
		rowcount=(int) Math.round(Math.pow(2, this.subLevels));
		cells=new int[this.resultingCells][MAX_OBJECT_COUNT_PER_CELL];
		
		startSignal=new CountDownLatch(1);
		nextTickSignal = new CountDownLatch(1);
		doneSignal=new CountDownLatch(WGrid.numberOfProcessors);
		
		collThreads=new CollThread[numberOfProcessors];
		for(int i=0;i!=collThreads.length;++i){
			collThreads[i]=new CollThread(startSignal,doneSignal,nextTickSignal,this);
			collThreads[i].start();
		}
	}
	private int[] getParentsOfCell(int cellnum){
		int[] parentcells = new int[this.levelOfI[cellnum]];//the parentCells of a particular Cell.
		for(int a=0,r=cellnum;a<this.levelOfI[cellnum]-1;a++){
			parentcells[a]=getParentCell(r);
			r=parentcells[a];
		}
		return parentcells;
	}

	//now look if parent is already in parentsOfI[i]. If not, add
	
	private int getParentCell(int cell){
		int i = cell-1;
		int currentlevel=this.levelOfI[i]; 
		int row=(i-this.cellsBeforeLevel[currentlevel])/this.linecountAtLevel[currentlevel];
		int column=(i-this.cellsBeforeLevel[currentlevel])%this.linecountAtLevel[currentlevel];
		int rowOfParent=row/2;
		int columnOfParent=column/2;
		int parentCellNum=rowOfParent*linecountAtLevel[currentlevel-1] + columnOfParent + cellsBeforeLevel[currentlevel-1];
		
		return parentCellNum;
	}
	
	public void syncCollisionCheck(){
		//starting new tick
		this.setUp();
		this.sortIn();
		
		int n=this.activeCells.size()/numberOfProcessors;
		for(int i=0;i<numberOfProcessors;i++){
			
			this.collThreads[i].doneSignal=this.doneSignal;
			this.collThreads[i].startSignal=this.startSignal;
			
			this.collThreads[i].setStart(i*n);
			this.collThreads[i].setEnd((i+1)*n);

		}
		collThreads[numberOfProcessors-1].setEnd(this.activeCells.size());
		
		nextTickSignal.countDown(); // prepare for start

		//setting up the new nextTickLatch for the next Tick.
		nextTickSignal=new CountDownLatch(1);
		for(int i=0;i<numberOfProcessors;i++){
			this.collThreads[i].nextTickSignal=this.nextTickSignal;//make them wait for it.
		}
		
		this.startSignal.countDown();//counted down the start signal latch

		//wait for completion
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//succesfully waited. The Threads ended their work and now wait for the next tick
		doneSignal=new CountDownLatch(WGrid.numberOfProcessors);
		startSignal=new CountDownLatch(1);

	}
	

	
	
	public void setUp(){
		for(Integer i:this.activeCells){
			for(int a=0;a<MAX_OBJECT_COUNT_PER_CELL;a++){
				cells[i][a]=0;
			}
		}
		this.activeCells=new ArrayList<Integer>(activeCells.size());

	}
	
	public void sortIn(){//sorts into the lowest possible level
		//sort it into the right cell!
		
		for (Integer i:this.IDs) {
			int level;
			//which level is the lowest it can go into??
			
			//TODO: Don't just take the first CollisionShape! Take the Max and Min on x and y Axis of the Whole Object!!
			double[] x = this.objects[i].getCollisionShapes()[0].getOnX();
			double[] y = this.objects[i].getCollisionShapes()[0].getOnY();
			
			if(x[0]<0||x[1]>this.width){//out of bounds check x axis
				this.objects[i].OOBReactX();
				continue;
			}
			if(y[0]<0||y[1]>this.length){//out of bounds check y axis
				this.objects[i].OOBReactY();
				continue;
			}
			
			double wratio = this.width/(x[1]-x[0]);
			double lratio = this.length/(y[1]-y[0]);
			
			if(lratio<wratio){
				level= (int)  ( Math.log(wratio) * WGrid.ONE_OVER_LN2 );
			}
			else{
				level= (int)  ( Math.log(lratio) * WGrid.ONE_OVER_LN2 );
			}
			int cellnum = this.cellsBeforeLevel[level] + (int)(y[0] / (this.length / this.linecountAtLevel[level])) * this.linecountAtLevel[level] + (int)(x[0] / (this.width / this.linecountAtLevel[level]));
			//a + b*a /c + d/a
//			System.out.println("Sorted into Level:"+level+" cellnum is:"+cellnum);
			for (int a = 0; a < MAX_OBJECT_COUNT_PER_CELL; a++) {
				if (cells[cellnum][a] == 0) {
					cells[cellnum][a] = this.objects[i].getPhysicsID();
					
					if (a == 0) {//only register as active cell if there is a second object added. !!SetUp has to be modified to make this work!!
						this.activeCells.add(cellnum);
					}
					break;
				}
			}
		}
		
	}
	
	private int getOrder(CollisionObject o){ // I may need this in the future
		int order;
		double[] x=o.getCollisionShapes()[0].getOnX();
		double[] y=o.getCollisionShapes()[0].getOnY();
		order=(int)(this.width/(x[1]-x[0]));// eg: 4,735 becomes 4. The remainder gets cut off
		if((int)(this.length/(y[1]-y[0]))>order) order=(int)(this.length/(y[1]-y[0]));
		return order;
	}
	@Override
	public void calcCollision() {
		this.syncCollisionCheck();
	}

	
}

