package de.hhu.propra14.team132.gameMechanics;

import java.io.IOException;
import java.util.ArrayList;

import de.hhu.propra14.team132.gameMechanics.rule.RuleSet;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.physics.CollisionSystem;
import de.hhu.propra14.team132.physics.WGrid;


public class Map {
	CollisionSystem collsys;

	ArrayList<Integer> objectIds;
	
	int IdCounter;
	ArrayList<Integer> aviableIds;
	
	private  GameObject[] mapObjects;
	
	double sizeX;
	double sizeY;
	
	boolean isActive;
	
	static int MAX_OBJECT_COUNT=21000; // arbitrary value. Has direct influence on a few very important array sizes.
	
	GameManager manager;
	
	int round;
	
	Integer currentTick;
	
	RuleSet ruleset;
	
	Player[] players;
	
	Player currentPlayer;
	
	public Map(GameManager manager,int playerCount) {
		this.initializeBasics(manager, playerCount);
		sizeX=0;
		sizeY=0;
	}
	public Map(GameManager manager,int playerCount,RuleSet ruleSet){
		this(manager,playerCount);
		this.ruleset=ruleSet;
	}
	private void initializeBasics(GameManager manager, int playerCount){
		
		this.manager=manager;

		mapObjects=new GameObject[MAX_OBJECT_COUNT];
		
		isActive=false;
		
		aviableIds=new ArrayList<Integer>();
		
		IdCounter=1; // zero is reserved!

		objectIds=new ArrayList<Integer>(MAX_OBJECT_COUNT/2);
		
		players = new Player[playerCount];
		
		currentTick = manager.getCurrentTick();
		
		for(int i = 0; i<playerCount; i++){
			players[i]=new Player();
		}
		
		this.currentPlayer=players[0];
		
		this.ruleset=RuleSet.generateStandardRules(this);
		
		this.ruleset.applyStartUpRules();
		
		this.collsys=new WGrid(Math.pow(2, 13), Math.pow(2, 13), 10, this, this.objectIds);
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
	
	
}
