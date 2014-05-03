package de.hhu.propra14.team132.physics;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;


public abstract class CollisionObject {
	//constants
	public static final int TRANSLATION_BEHAVIOUR_NORMAL=1;
	public static final int TRANSLATION_BEHAVIOUR_UNMOVING=0;
	public static final int TRANSLATION_BEHAVIOUR_YIELDING=2;
	
	//instance variables
	Vector2D speed;
	Vector2D position;
	Vector2D acceleration;
	
	ConvexCollisionShape[] collisionShapes;
	
	Map mapPlacedIn;
	int playerID;
	int physicsID;
	int lastCollidedWith;
	private CollisionMode collisionMode;
	int collisionTranslationBehaviour;
	
	private boolean markedForDeletion;
	
	
	//constructors
	public CollisionObject(ConvexCollisionShape[] shapes, int teamID, Map map) {
		this.initializeBasics(teamID, map);
		this.collisionShapes=shapes;
	}
	public CollisionObject(ConvexCollisionShape shape, int teamID, Map map) {
		this.initializeBasics(teamID, map);
		this.collisionShapes=new ConvexCollisionShape[1];
		this.collisionShapes[0]=shape;
	}
	public CollisionObject(int teamID, Map map) {
		this.initializeBasics(teamID, map);
		this.collisionShapes=new ConvexCollisionShape[1];
		double[] x ={
			0,20,20,0	
		};
		double[] y={
			0,0,20,20
		};
		this.collisionShapes[0]=new ConvexCollisionShape(x,y);
	}	
	//helper method for constructors
	private void initializeBasics(int teamID, Map map){
		this.playerID=teamID;
		this.speed=new Vector2D();
		this.position=new Vector2D();
		this.acceleration=new Vector2D();
		
		this.mapPlacedIn=map;
		
		collisionTranslationBehaviour=TRANSLATION_BEHAVIOUR_NORMAL;
		
		markedForDeletion=false;
	}
	
	
	//methods
	public void collideWithCheckTeam(CollisionObject o){
		if(this.playerID!=o.playerID) collideWith(o);
	}
	
	public void collideWith(CollisionObject o){
		if (this.getLastCollidedWith() != o.getPhysicsID() && o.getLastCollidedWith() != this.getPhysicsID()){
			if (o.getCollisionMode() == CollisionMode.NOT_EXPLOADING && this.collisionMode == CollisionMode.NOT_EXPLOADING) {
				for (ConvexCollisionShape s : this.collisionShapes) {
					for (ConvexCollisionShape s2 : o.getCollisionShapes()) {
						
						Vector2D mtv = ConvexCollisionShape.PolygonVsPolygonGetMtv(s, s2);
						
						if (mtv != null) {
							this.setLastCollidedWith(o.getPhysicsID());
							o.setLastCollidedWith(this.physicsID);
							mtvCalculationsWith(mtv, o);
							/*includes further collisions. No call of furtherCollisionWith()!
							this is because the object might want to use the mtv in further CollisionWith..
							Maybe I still should get that out of there and let furtherCollisionWith take the mtv as a Vector2D.
							Or split it into 2 methods. furtherCollisionWithMtv() that takes the mtv and then calls furtherCollisionWith after it is done
							using the mtv as furtherCollisionWith is abstract and MUST be implemented.
							This is ugly. Maybe I have a better idea later.*/
							return;
						}
						
					}
					
				}
			} 
			else {
				for (ConvexCollisionShape s : this.collisionShapes) {
					for (ConvexCollisionShape s2 : o.getCollisionShapes()) {
						if (ConvexCollisionShape.PolygonVsPolygonHitTest(s,s2)) {
							this.setLastCollidedWith(o.getPhysicsID());
							o.setLastCollidedWith(this.physicsID);
							furtherCollisionWith(o);
							return;
						}
					}
				}
			}
		}
	}
	public void mtvCalculationsWith(Vector2D mtv, CollisionObject o){}
	abstract void furtherCollisionWith(CollisionObject o);
	
	
	
	
	//getter/setter
	public Vector2D getSpeed() {
		return speed;
	}
	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	public Vector2D getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}
	public ConvexCollisionShape[] getCollisionShapes() {
		return collisionShapes;
	}
	public void setCollisionShapes(ConvexCollisionShape[] collisionShapes) {
		this.collisionShapes = collisionShapes;
	}
	public Map getMapPlacedIn() {
		return mapPlacedIn;
	}
	public void setMapPlacedIn(Map mapPlacedIn) {
		this.mapPlacedIn = mapPlacedIn;
	}
	public int getPlayerID() {
		return playerID;
	}
	protected void setPlayerID(int teamID) {
		this.playerID = teamID;
	}
	public int getPhysicsID() {
		return physicsID;
	}
	protected void setPhysicsID(int physicsID) {
		this.physicsID = physicsID;
	}
	public int getLastCollidedWith() {
		return lastCollidedWith;
	}
	public void setLastCollidedWith(int lastCollidedWith) {
		this.lastCollidedWith = lastCollidedWith;
	}
	public int getCollisionTranslationBehaviour() {
		return collisionTranslationBehaviour;
	}
	public void setCollisionTranslationBehaviour(int collisionTranslationBehaviour) {
		this.collisionTranslationBehaviour = collisionTranslationBehaviour;
	}
	public boolean isMarkedForDeletion() {
		return markedForDeletion;
	}
	protected void setMarkedForDeletion(boolean markedForDeletion) {
		this.markedForDeletion = markedForDeletion;
	}
	public CollisionMode getCollisionMode() {
		return collisionMode;
	}
	
	
}
