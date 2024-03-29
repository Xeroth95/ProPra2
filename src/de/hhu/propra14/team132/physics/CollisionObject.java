package de.hhu.propra14.team132.physics;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;


public abstract strictfp class CollisionObject {
	//constants
	public static final int TRANSLATION_BEHAVIOUR_NORMAL=1;
	public static final int TRANSLATION_BEHAVIOUR_UNMOVING=0;
	public static final int TRANSLATION_BEHAVIOUR_YIELDING=2;
	
	//instance variables

	Vector2D speed;

	Vector2D position;

	Vector2D acceleration;

    private int life;


	double bounciness;
	double friction;
	
	public ConvexCollisionShape[] collisionShapes;
	

	int playerID;
	int physicsID;
	int lastCollidedWith;

	private CollisionMode collisionMode;
	public int collisionTranslationBehaviour;


	ArrayList<Effect> effects;

	private boolean markedForDeletion;
	
	
	//constructors
    public CollisionObject() {  //is used for serializing/deseralizing
    	this(0);
    }
	public CollisionObject(ConvexCollisionShape[] shapes, int teamID) {
        this.initializeBasics(teamID);
        this.collisionShapes = shapes;
    }
	public CollisionObject(ConvexCollisionShape shape, int teamID) {
		this.initializeBasics(teamID);
		this.collisionShapes=new ConvexCollisionShape[1];
		this.collisionShapes[0]=shape;
	}
	public CollisionObject(int teamID) {
		this.initializeBasics(teamID);
		this.collisionShapes=new ConvexCollisionShape[1];
		double[] x ={
			0,10,10,0	
		};
		double[] y={
			0,0,10,10
		};
		this.collisionShapes[0]=new ConvexCollisionShape(x,y);
	}	
	//helper method for constructors
	private void initializeBasics(int teamID){
		this.playerID=teamID;
		this.speed=new Vector2D();
		this.position=new Vector2D();
		this.acceleration=new Vector2D();
		
		this.effects=this.getInitalEffects();
		this.bounciness=this.getInitialBounciness();
		this.friction=this.getInitialFriction();
		this.collisionMode=this.getInitialCollisionMode();

		collisionTranslationBehaviour=TRANSLATION_BEHAVIOUR_NORMAL;
		
		markedForDeletion=false;
	}
	
	
	//methods
	public void collideWithCheckTeam(CollisionObject o){
		if(this.playerID==Player.WORLD&&o.playerID==Player.WORLD){
			return;
		}
		collideWith(o);
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

							this.furtherCollisionWith(o);
							o.furtherCollisionWith(o);
							if(this.life<=0){
								this.setMarkedForDeletion(true);
							}
							if(o.getLife()<=0){
								o.setMarkedForDeletion(true);
							}
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
							o.furtherCollisionWith(this);
							
							if(this.life<=0){
								this.setMarkedForDeletion(true);
							}
							if(o.getLife()<=0){
								o.setMarkedForDeletion(true);
							}
							return;
						}
					}
				}
			}
		}
	}
	private void mtvCalculationsWith(Vector2D mtv,CollisionObject o){ 
		// this one is large. I hate to have this much redundant code. Maybe I can find a way around this.
		
		if(o.getCollisionTranslationBehaviour()==this.collisionTranslationBehaviour){//both want to behave the same way, so treat them equally
			mtv.multiplyWith(0.5);
			this.getPosition().addVector(mtv);//get out of that collision
			this.recalcPosition();// I hate to do this. this looks really overcomplicated. This code should not be responsible for doing this.
			mtv.multiplyWith(-1.01); // sometimes things can get stuck because of the imprecision of floating point arithmetics, so this trickery might be needed.
			o.getPosition().addVector(mtv);// you too, get out of that collision
			o.recalcPosition();
			try {
				mtv.makeUnitVector();//this is important! if this was not here, the following code would not work at all! It is necessary to do this to get accurate projections!
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// I made that code part clear to me again:		
			// It is a projection of the speed vector onto the mtv.
			
			double thisSpeedDotMtvTimesMtvX=(this.speed.getX()*mtv.getX()+this.speed.getY()*mtv.getY())*mtv.getX();//read as: (dot product of this.speed and mtv)*mtv.x
			double thisSpeedDotMtvTimesMtvY=(this.speed.getX()*mtv.getX()+this.speed.getY()*mtv.getY())*mtv.getY();
			
			double oSpeedDotMtvTimesMtvX=(o.getSpeed().getX()*mtv.getX()+o.getSpeed().getY()*mtv.getY())*mtv.getX();
			double oSpeedDotMtvTimesMtvY=(o.getSpeed().getX()*mtv.getX()+o.getSpeed().getY()*mtv.getY())*mtv.getY();
			
			//set both
			this.speed.setX(this.bounciness*oSpeedDotMtvTimesMtvX+(this.speed.getX()-thisSpeedDotMtvTimesMtvX)*this.friction);
			this.speed.setY(this.bounciness*oSpeedDotMtvTimesMtvY+(this.speed.getY()-thisSpeedDotMtvTimesMtvY)*this.friction);
			
			o.getSpeed().setX(o.getBounciness()*thisSpeedDotMtvTimesMtvX+(o.getSpeed().getX()-oSpeedDotMtvTimesMtvX)*o.getFriction());
			o.getSpeed().setY(o.getBounciness()*thisSpeedDotMtvTimesMtvY+(o.getSpeed().getY()-oSpeedDotMtvTimesMtvY)*o.getFriction());
			
			
		}
		else {
			if(this.collisionTranslationBehaviour<o.getCollisionTranslationBehaviour()){
		
				mtv.multiplyWith(-1.01);
				o.getPosition().addVector(mtv);// get out of the collision completely on your own.
				o.recalcPosition();
				try {
					mtv.makeUnitVector();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				double oSpeedDotMtvTimesMtvX=(o.getSpeed().getX()*mtv.getX()+o.getSpeed().getY()*mtv.getY())*mtv.getX();
				double oSpeedDotMtvTimesMtvY=(o.getSpeed().getX()*mtv.getX()+o.getSpeed().getY()*mtv.getY())*mtv.getY();
	
				//set only the other object
				o.getSpeed().setX(o.getBounciness()*-oSpeedDotMtvTimesMtvX+(o.getSpeed().getX()-oSpeedDotMtvTimesMtvX)*o.getFriction());
				o.getSpeed().setY(o.getBounciness()*-oSpeedDotMtvTimesMtvY+(o.getSpeed().getY()-oSpeedDotMtvTimesMtvY)*o.getFriction());
			
		}
			else{
				mtv.multiplyWith(-1.01);
				this.getPosition().addVector(mtv);// get out of the collision completely on your own.
				this.recalcPosition();
//				mtv.multiplyWith(-1.01);
				try {
					mtv.makeUnitVector();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				double thisSpeedDotMtvTimesMtvX=(this.speed.getX()*mtv.getX()+this.speed.getY()*mtv.getY())*mtv.getX();
				double thisSpeedDotMtvTimesMtvY=(this.speed.getX()*mtv.getX()+this.speed.getY()*mtv.getY())*mtv.getY();
				
				//set only this object
				this.speed.setX(this.bounciness*(-thisSpeedDotMtvTimesMtvX)+(this.speed.getX()-thisSpeedDotMtvTimesMtvX)*this.friction);
				this.speed.setY(this.bounciness*(-thisSpeedDotMtvTimesMtvY)+(this.speed.getY()-thisSpeedDotMtvTimesMtvY)*this.friction);
				
			}
		}
	}
	public abstract void furtherCollisionWith(CollisionObject o);
	
	public void recalcPosition(){
		for(ConvexCollisionShape s:this.collisionShapes){
			s.setPositionX(this.position.getX());
			s.setPositionY(this.position.getY());
		}
	}
	
	public void setPosition(Vector2D position) { // this one is special. it needs to traverse through all the ConvexCollisionShapes in order to set the position in them.
		this.position = position;
		for(ConvexCollisionShape s:this.collisionShapes){
			s.setPositionX(this.position.getX());
			s.setPositionY(this.position.getY());
		}
	}
	public void move(){
		for(int i = 0; i<this.effects.size();i++){
			Effect e = this.effects.get(i);
			e.apply(this);
		}
		this.speed.addVector(this.acceleration);
		if(this.speed.getLength()<0.001){
			this.speed.multiplyWith(0);
			this.acceleration.multiplyWith(0);
			return;
		}
		this.position.addVector(this.speed);
		for(ConvexCollisionShape s:this.collisionShapes){
			s.setPositionX(this.position.getX());
			s.setPositionY(this.position.getY());
		}
		this.acceleration.setX(0);
		this.acceleration.setY(0);
	}
	
	public void OOBReactX(){
		this.speed.setX(-this.speed.getX()*1.001);
		
	}
	public void OOBReactY(){
		this.speed.setY(-this.speed.getY()*1.001);
	}
	
	//regular getter/setter

	public abstract double getInitialBounciness();
	public abstract double getInitialFriction();
	public abstract ArrayList<Effect> getInitalEffects();
	public abstract CollisionMode getInitialCollisionMode();
	
	public ArrayList<Effect> getEffects() {
		return effects;
	}
	public Vector2D getSpeed() {
		return speed;
	}
	public double getBounciness() {
		return bounciness;
	}
	public double getFriction() {
		return friction;
	}
	public void setBounciness(double bounciness) {
		this.bounciness = bounciness;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}
	public void setCollisionMode(CollisionMode collisionMode) {
		this.collisionMode = collisionMode;
	}
	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}
	public Vector2D getPosition() {
		return position;
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

	public int getPlayerID() {
		return playerID;
	}
	protected void setPlayerID(int teamID) {
		this.playerID = teamID;
	}
	public int getPhysicsID() {
		return physicsID;
	}
	public void setPhysicsID(int physicsID) {
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
	public void addEffect(Effect e){
		this.effects.add(e);
	}
	public void removeEffect(Effect e){
		this.effects.remove(e);
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
}
