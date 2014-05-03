package de.hhu.propra14.team132.gameMechanics;

import java.util.ArrayList;

import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.CollisionSystem;


public class Map {
	CollisionSystem collsys;

	ArrayList<Integer> objectIds;
	
	int IdCounter;
	ArrayList<Integer> aviableIds;
	
	private  CollisionObject[] levelObjects;
	
	double sizeX;
	double sizeY;
	
	boolean isActive;
	
	static int MAX_OBJECT_COUNT=21000; // arbitrary value. Has direct influence on a few very important array sizes.
	
	public Map(){
		this.initializeBasics();
		sizeX=0;
		sizeY=0;
	}
	public Map(double x,double y){
		this.initializeBasics();
		sizeX=x;
		sizeY=y;
	}
	private void initializeBasics(){

		levelObjects=new CollisionObject[MAX_OBJECT_COUNT];
		
		isActive=false;
		
		aviableIds=new ArrayList<Integer>();
		
		IdCounter=1; // zero is reserved!

		this.sizeX=10000;
		this.sizeY=10000;
		objectIds=new ArrayList<Integer>(MAX_OBJECT_COUNT/2);

	}
	private int getNewMapID(){
		if(this.aviableIds.size()!=0){	//first, try to recycle available Ids
			try{
				return this.aviableIds.get(0);
			}
			finally{
				this.aviableIds.remove(0);
			}
		}
		this.IdCounter++;
		return this.IdCounter;
	}
	public void nextTick(){
		moveAllObjects();
		//TODO:Apply Rules, or something?!
	}
	private void moveAllObjects() {
		for(int i:this.objectIds){
			this.levelObjects[i].setLastCollidedWith(-1);//somewhere this has to be done...
			this.levelObjects[i].move();
		}
	}
	public void detectCollision(){//delegate
		this.collsys.calcCollision();
	}
	public void addObject(CollisionObject o){
		int newID = this.getNewMapID();
		o.setPhysicsID(newID);
		this.objectIds.add(newID);
		this.levelObjects[newID]=o;
	}
	
	public void removeObject(CollisionObject o){
		this.objectIds.remove(o.getPhysicsID());
		this.levelObjects[o.getPhysicsID()]=null;
		this.aviableIds.add(o.getPhysicsID());
	}
	
	public void removeObject(int objectID){
		this.objectIds.remove(objectID);
		this.levelObjects[objectID]=null;
		this.aviableIds.add(objectID);
	}
	
	
}
