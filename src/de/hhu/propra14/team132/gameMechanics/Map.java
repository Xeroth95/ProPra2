package de.hhu.propra14.team132.gameMechanics;

import java.util.ArrayList;

import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.CollisionSystem;


public class Map {
	CollisionSystem collsys;

	ArrayList<Integer> objectIds;
	
	int IdCounter;
	ArrayList<Integer> aviableIds;
	
	private  CollisionObject[] mapObjects;
	
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

		mapObjects=new CollisionObject[MAX_OBJECT_COUNT];
		
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
			this.mapObjects[i].setLastCollidedWith(-1);//somewhere this has to be done...
			this.mapObjects[i].move();
		}
	}
	public void detectCollision(){//delegate
		this.collsys.calcCollision();
	}
	public void addObject(CollisionObject o){
		int newID = this.getNewMapID();
		o.setPhysicsID(newID);
		this.objectIds.add(newID);
		this.mapObjects[newID]=o;
	}
	
	public void removeObject(CollisionObject o){
		this.objectIds.remove(o.getPhysicsID());
		this.mapObjects[o.getPhysicsID()]=null;
		this.aviableIds.add(o.getPhysicsID());
	}
	
	public void removeObject(int objectID){
		this.objectIds.remove(objectID);
		this.mapObjects[objectID]=null;
		this.aviableIds.add(objectID);
	}
	public CollisionObject[] getMapObjects() {
		return mapObjects;
	}
	public void setMapObjects(CollisionObject[] mapObjects) {
		this.mapObjects = mapObjects;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public double getSizeX() {
		return sizeX;
	}
	public double getSizeY() {
		return sizeY;
	}
	
	
}
