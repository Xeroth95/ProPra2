package de.hhu.propra14.team132.gameMechanics;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.rule.Rule;
import de.hhu.propra14.team132.gameMechanics.rule.RuleSet;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.physics.BadCollisionSystem;
import de.hhu.propra14.team132.physics.CollisionSystem;


public class Map implements Serializable{ 
	
	transient CollisionSystem collsys;
	ArrayList<Integer> objectIds;

	int IdCounter;
    ArrayList<Integer> aviableIds;


	private  GameObject[] mapObjects;

    double sizeX;
    double sizeY;


	boolean isActive;
    boolean dead;


	static int MAX_OBJECT_COUNT=21000; // arbitrary value. Has direct influence on a few very important array sizes.
	
	transient GameManager manager;

	int round;

	int timeLeftInTicks;

	int currentTick;  //todo: Chris, ist das notwending? currentTick ist doch static. ja ist es ;)

	RuleSet ruleset;

	Player[] players;

	Player currentPlayer;
	
	public Map(GameManager manager) {
		this.initializeBasics(manager);
		sizeX=0;
		sizeY=0;
	}
	public void initializeBasics(GameManager manager){
		
		this.manager=manager;

		mapObjects=new GameObject[MAX_OBJECT_COUNT];
		
		isActive=false;
		dead=false;
		
		aviableIds=new ArrayList<Integer>();
		
		IdCounter=1; // zero is reserved!

		objectIds=new ArrayList<Integer>(MAX_OBJECT_COUNT/2);		
		
		currentTick = 0;
		
		this.collsys=new BadCollisionSystem(this);/*new WGrid(Math.pow(2, 13), Math.pow(2, 13), 10, this, this.objectIds)*/;
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
		this.currentTick++;
		this.ruleset.applyPassiveRules();
		moveAllObjects();
		this.collsys.calcCollision();
	}
	private strictfp void moveAllObjects() {
		for(int i:this.objectIds){
			this.mapObjects[i].getSpeed().multiplyWith(0.9999);
			this.mapObjects[i].setLastCollidedWith(-1);//somewhere this has to be done...
			this.mapObjects[i].move();
		}
	}
	public void detectCollision(){//delegate
		this.collsys.calcCollision();
	}
	public void addObject(GameObject o){
		int newID = this.getNewMapID();
		o.setPhysicsID(newID);
		this.objectIds.add(newID);
		this.mapObjects[newID]=o;
	}
	
	public void removeObject(GameObject o){
		this.objectIds.remove(o.getPhysicsID());
		this.mapObjects[o.getPhysicsID()]=null;
		this.aviableIds.add(o.getPhysicsID());
	}
	
	public void removeObject(int objectID){
		this.objectIds.remove(objectID);
		this.mapObjects[objectID]=null;
		this.aviableIds.add(objectID);
	}
	
	public void setUpAfterLoading(){
		this.collsys=new BadCollisionSystem(this);/*new WGrid(Math.pow(2, 13), Math.pow(2, 13), 10, this, this.objectIds)*/;
		for(Rule r:this.ruleset.getPassiveRules()){
			r.setGameMap(this);
		}
		for(Rule r:this.ruleset.getRuntimeRules()){
			r.setGameMap(this);
		}
		for(Rule r:this.ruleset.getStartUpRules()){
			r.setGameMap(this);
		}
	}
	
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Integer getCurrentTick() {
		return currentTick;
	}
	public RuleSet getRuleset() {
		return ruleset;
	}
	public void setRuleset(RuleSet ruleset) {
		this.ruleset = ruleset;
		ruleset.applyStartUpRules();
	}
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public GameObject[] getMapObjects() {
		return mapObjects;
	}
    public ArrayList<Integer> getObjectIds() {
        return objectIds;
    }
    public void setMapObjects(GameObject[] mapObjects) {
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
	public GameManager getManager() {
		return manager;
	}
	public void setManager(GameManager manager) {
		this.manager = manager;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public void setObjectIds(ArrayList<Integer> objectIds) {
		this.objectIds = objectIds;
	}
	public void setCurrentTick(Integer currentTick) {
		this.currentTick = currentTick;
	}
	public int getTimeLeftInTicks() {
		return timeLeftInTicks;
	}
	public void setTimeLeftInTicks(int timeLeftInTicks) {
		this.timeLeftInTicks = timeLeftInTicks;
	}
	
	
	
}
