package de.hhu.propra14.team132.physics.util;

import java.awt.Polygon;

import com.google.gson.annotations.Expose;


public strictfp class ConvexCollisionShape {
	
//probably really nasty class with a lot of attributes and a lot of algorithms for collision. Think thrice before changing!
	@Expose double[] xPoints;
	@Expose double[] yPoints;
	@Expose double[] originalxPoints;
	@Expose double[] originalyPoints;
	@Expose Vector2D[] collisionAxes;
	@Expose double positionX;
	@Expose double positionY;
	@Expose double minOnX;
	@Expose double maxOnX;
	@Expose double minOnY;
	@Expose double maxOnY;
	@Expose double xSize;
	@Expose double ySize;
	public ConvexCollisionShape(){
		
	}
	public ConvexCollisionShape(Vector2D[] p){
		double[] x=new double[p.length];
		double[] y=new double[p.length];
		for(int i=0;i<p.length;i++){
			x[i]=p[i].getX();
			y[i]=p[i].getY();
		}
		constructHelp(x,y);
	}
	public ConvexCollisionShape(double[] x,double[] y){
		constructHelp(x,y);
	}
	private void constructHelp(double[] x,double[] y){
		minOnX=Double.MAX_VALUE;
		maxOnX=Double.MIN_VALUE;
		minOnY=Double.MAX_VALUE;
		maxOnY=Double.MIN_VALUE;
		this.originalxPoints=x;
		this.originalyPoints=y;
		this.xPoints=new double[x.length];
		this.yPoints=new double[x.length];
		for(int i=0;i<x.length;i++){
			if(x[i]<minOnX)minOnX=x[i];
			if(x[i]>maxOnX)maxOnX=x[i];
			if(y[i]<minOnY)minOnY=y[i];
			if(y[i]>maxOnY)maxOnY=y[i];
		}
		this.xSize=this.maxOnX-this.minOnX;
		this.ySize=this.maxOnY-this.minOnY;
		positionX=0;
		positionY=0;
		this.recalcY();
		this.recalcX();
		this.computeCollisionAxes();
	}
	
	
	public static boolean PolygonVsPolygonHitTest(ConvexCollisionShape shape1,ConvexCollisionShape shape2){
		//on y axis
		if(shape1.getMinOnY()>shape2.getMaxOnY()||shape1.getMaxOnY()<shape2.getMinOnY()) return false;
		//on x axis
		//IF the min of ax is greater than the max of bx, [Logical] OR the max of ax is smaller than the min of bx, THEN...
		if(shape1.getMinOnX()>shape2.getMaxOnX()||shape1.getMaxOnX()<shape2.getMinOnX()) return false;
		
		Vector2D[] axes1 = shape1.getCollisionAxes();
		Vector2D[] axes2 = shape2.getCollisionAxes();
		//Vector2D[] axes = combineAxisArrays(shape1.getCollisionAxes(), shape2.getCollisionAxes());
		//axes = excludeParallelAxes(axes);
		//check for parallel axes and replace
		// loop over the axes
		for (int i = 0; i < axes1.length; i++) {
		    Vector2D axisToTest = axes1[i];
		    // project both shapes onto the axis
		    // x value represents min projected vertex on axis and y represents the max projected vertex on the axis. It's just that I need a wrapper at this point...
		    Vector2D p1 = shape1.projectOnAxis(axisToTest);
		    Vector2D p2 = shape2.projectOnAxis(axisToTest);
		    // do the projections overlap on this axis?
		    //IF the min of p1 is greater than the max of p2, [Logical] OR the max of p1 is smaller than the min of p2, THEN...
		    if ((p1.getX()>p2.getY()||p1.getY()<p2.getX())) {  
		   // then we can guarantee that the shapes do not overlap
		     return false;
		    }
		}
		for (int i = 0; i < axes2.length; i++) {
		    Vector2D axisToTest = axes2[i];
		    // project both shapes onto the axis
		    // x value represents min projected vertex on axis and y represents the max projected vertex on the axis. It's just that I need a wrapper at this point...
		    Vector2D p1 = shape1.projectOnAxis(axisToTest);
		    Vector2D p2 = shape2.projectOnAxis(axisToTest);
		    // do the projections overlap on this axis?
		    //IF the min of p1 is greater than the max of p2, [Logical] OR the max of p1 is smaller than the min of p2, THEN...
		    if ((p1.getX()>p2.getY()||p1.getY()<p2.getX())) {  
		   // then we can guarantee that the shapes do not overlap
		     return false;
		    }
		}
		// if we get here then we know that every axis had overlap on it
		// so we can guarantee an intersection
		return true;
	}
	public static Vector2D PolygonVsPolygonGetMtv(ConvexCollisionShape shape1,ConvexCollisionShape shape2){
			//on y axis
			if(shape1.getMinOnY()>shape2.getMaxOnY()||shape1.getMaxOnY()<shape2.getMinOnY()) return null;
			//on x axis
			//IF the min of ax is greater than the max of bx, [Logical] OR the max of ax is smaller than the min of bx, THEN...
			if(shape1.getMinOnX()>shape2.getMaxOnX()||shape1.getMaxOnX()<shape2.getMinOnX()) return null;
			
			
		double overlap=Double.MAX_VALUE;//damn large value because we want to check if the found overlap is smaller, so I'm using the satic value double.MAX_VALUE here :D
		Vector2D smallestOverlapAxis=new Vector2D();
		Vector2D[] axes1 = shape1.getCollisionAxes();
		Vector2D[] axes2 = shape2.getCollisionAxes();
		//check for parallel axes and replace
		//axes2=excludeParallelAxes(axes1, axes2);
		// loop over the axes1
		for (int i = 0; i < axes1.length; i++) {
		  Vector2D axisToTest = axes1[i];
		  // project both shapes onto the axis
		  // x value represents min projected vertex on axis and y represents the max projected vertex on the axis. It's just that I need a wrapper at this point...
		  Vector2D p1 = shape1.projectOnAxis(axisToTest);
		  Vector2D p2 = shape2.projectOnAxis(axisToTest);
		  // do the projections overlap on this axis?
		  //IF the min of p1 is greater than the max of p2, [Logical] OR the max of p1 is smaller than the min of p2, THEN...
		  if ((p1.getX()>p2.getY()||p1.getY()<p2.getX())) {  
			// then we can guarantee that the shapes do not overlap
			  return null;
			  
		  }
		  else{
			  // so we DO have an overlap on that axis. the overlap and the projection together make a potential mtv. Calc it and save it.
			  if(p2.getY()>p1.getY()){
			      if(p1.getY()-p2.getX()<overlap){//mhmhmhm???
			        overlap=p1.getY()-p2.getX();
			        smallestOverlapAxis=axisToTest;
			       }
			     }
			     else{
			      if(p2.getY()-p1.getX()<overlap){//mhmhmhm???
			       overlap=p2.getY()-p1.getX();
			       smallestOverlapAxis=axisToTest;
			      }
			     }
			  
		  }
		}
		// loop over the axes2
		for (int i = 0; i < axes2.length; i++) {
		  Vector2D axisToTest = axes2[i];
		  // project both shapes onto the axis
		  Vector2D p1 = shape1.projectOnAxis(axisToTest);
		  Vector2D p2 = shape2.projectOnAxis(axisToTest);
		  // do the projections overlap?
		  if ((p1.getX()>p2.getY()||p1.getY()<p2.getX())) {
		    // then we can guarantee that the shapes do not overlap
			  return null;
		  }
		  else{
			  // so we DO have an overlap on that axis. the overlap and the projection together make a potential mtv. Calc it and save it.
			  if(p2.getY()>p1.getY()){
			      if(p1.getY()-p2.getX()<overlap){//mhmhmhm???
			        overlap=p1.getY()-p2.getX();
			        smallestOverlapAxis=axisToTest;
			       }
			     }
			     else{
			      if(p2.getY()-p1.getX()<overlap){//mhmhmhm???
			       overlap=p2.getY()-p1.getX();
			       smallestOverlapAxis=axisToTest;
			      }
			     }
			  
		  }
		}
		if(overlap==0)return null;
		smallestOverlapAxis.multiplyWith(overlap);//make it the mtv
		return smallestOverlapAxis;


	}
	
	private Vector2D projectOnAxis(Vector2D axisToTest) {
		//axisToTest.multiplyWith((double)1/axisToTest.getLength());//normalize... // no, axes will be normalized when initialized.
		//NEVER use this method with not-normalized axes! See the comment above and change it if you want. This is important so I will just leave this here for the time.
		
		//the vertexes are seen as Vectors on their own. The minimum and the maximum
		double min = axisToTest.getX()*xPoints[0]+axisToTest.getY()*yPoints[0];
		double max = min;
		for (int i = 0; i < this.xPoints.length; i++) {
		  //normalize to get accurate projections!!! I could just assume this, or, assert it with additional computing. 
		  //I hope no one will ever touch this as soon as it's finished. I do not assert the normalisation for now!! 
		  //take care if you want to change this/related code!
			double p = axisToTest.getX()*xPoints[i]+axisToTest.getY()*yPoints[i];
			if (p < min) {// AAAA! possible negative numbers ?? OR??? not sure TODO:examine if negative numbers occure or if the are bad.
		    min = p;
		  } else if (p > max) {
		    max = p;
		  }
		}
		Vector2D proj = new Vector2D(min, max);
		return proj;
	}

	public void computeCollisionAxes(){
		Vector2D[] axes=new Vector2D[xPoints.length];
		for(int i=0;i<this.xPoints.length;i++){
			//obtaining the edge: vectorb-vectora
			//edge is an relative Vector, describing the distance between the two vertices. It does NOT describe a Point on its own!
			//Vector2D edge = new Vector2D(xPoints[(i+1)%xPoints.length]-xPoints[i],yPoints[(i+1)%yPoints.length]-yPoints[i]);
			//Vector2D axis = edge.getLeftHandNormal();
			Vector2D axis = new Vector2D(yPoints[(i+1)%yPoints.length]-yPoints[i],-(xPoints[(i+1)%xPoints.length]-xPoints[i]));
			//again:the axis should be normalized to get accurate projections!!! better do that here.
			axis.multiplyWith(1/axis.getLength());
			axes[i]=axis;
		}
		this.collisionAxes = excludeParallelAxes(axes); 
	}
	
	public static Vector2D[] excludeParallelAxes(Vector2D[] oldAxes){
		int parallelCount=0;
		int[] badAxes=new int[oldAxes.length];
		//mark the parallelAxes, store their position in the array.
		for(int i=0;i<oldAxes.length;i++){
			for(int j=0;j>i&&j<oldAxes.length;i++){
				if(oldAxes[i].isParallel(oldAxes[j])){
					badAxes[parallelCount]=j;
					parallelCount++;
				}
			}
		}
		//now remove all the parallel axes, if there are any.
		if(parallelCount>0){
			//doing it this way is most probably faster, even though it looks disgustingly overcomplicated.
			Vector2D[] newAxes= new Vector2D[oldAxes.length-parallelCount];
		
			int j=0;
			for(int i=0;j<parallelCount;){
				if(i!=badAxes[j]){
					newAxes[i-j]=oldAxes[i];
				}
				else{
					j++;
				}
			}
			return newAxes;
		}
		return oldAxes;
	}
	
	public Vector2D[] getComputedCollisionAxes(){
		Vector2D[] axes=new Vector2D[xPoints.length-1];
		for(int i=0;i<this.xPoints.length-1;i++){
			//obtaining the edge: vectorb-vectora?????
			// edge is an relative Vector, describing the distance between the two vertices. It does NOT describe a Point on its own!
			Vector2D edge = new Vector2D(xPoints[i+1]-xPoints[i],yPoints[i+1]-yPoints[i]);
			
			Vector2D axis = edge.getLeftHandNormal();
			axes[i]=axis;
		}
		return axes;
	}
	

	
	public double[] getOnX(){
		double[] minMax={
				this.minOnX+this.positionX,this.maxOnX+this.positionX
		};
		return minMax;
	}
	public double getMaxOnX(){
		return this.maxOnX+this.positionX;
	}
	public double getMinOnX(){
		return this.minOnX+this.positionX;
	}
	public double[] getOnY(){
		double[] minMax={
				this.minOnY+this.positionY,this.maxOnY+this.positionY
		};
		return minMax;
	}
	public double getMaxOnY(){
		return this.maxOnY+this.positionY;
	}
	public double getMinOnY(){
		return this.minOnY+this.positionY;
	}
	
	public Polygon getPolygonToDraw(){
		int[] x=new int[xPoints.length];
		int[] y=new int[yPoints.length];
		for(int i=0;i<xPoints.length;i++){
			x[i]=(int)(xPoints[i]);
		}
		for(int i=0;i<yPoints.length;i++){
			y[i]=(int)(yPoints[i]);
		}
		Polygon p=new Polygon(x,y, x.length);
		return p;
	}
	public double[] getxPoints() {
		double[] x=new double[xPoints.length];
		for(int i=0;i<xPoints.length;i++){
			x[i]=xPoints[i]+this.positionX;
		}
		return x;
	}
	public void setxPoints(double[] xPoints) {
		this.xPoints = xPoints;
	}
	public double[] getyPoints() {
		double[] y=new double[xPoints.length];
		for(int i=0;i<xPoints.length;i++){
			y[i]=yPoints[i]+this.positionY;
		}
		return y;
	}
	
	private Vector2D[] getCollisionAxes() {
		return this.collisionAxes;
	}
	
	public void setyPoints(double[] yPoints) {
		this.yPoints = yPoints;
	}
	public void setCollisionAxes(Vector2D[] newAxes){
		this.collisionAxes=newAxes;
	}
	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
		this.recalcX();
	}
	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
		this.recalcY();
	}
	protected void recalcY() {
		for(int i=0;i<originalyPoints.length;i++){
			this.yPoints[i]=originalyPoints[i]+this.positionY;
		}
	}
	protected void recalcX() {
		for(int i=0;i<originalxPoints.length;i++){
			this.xPoints[i]=originalxPoints[i]+this.positionX;
		}
	}
	public double getxSize() {
		return xSize;
	}
	public void setxSize(double xSize) {
		this.xSize = xSize;
	}
	public double getySize() {
		return ySize;
	}
	public void setySize(double ySize) {
		this.ySize = ySize;
	}
	public void setMinOnX(double minOnX) {
		this.minOnX = minOnX;
	}
	public void setMaxOnX(double maxOnX) {
		this.maxOnX = maxOnX;
	}
	public void setMinOnY(double minOnY) {
		this.minOnY = minOnY;
	}
	public void setMaxOnY(double maxOnY) {
		this.maxOnY = maxOnY;
	}
	
	
}
