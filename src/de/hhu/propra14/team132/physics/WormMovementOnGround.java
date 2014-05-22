package de.hhu.propra14.team132.physics;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.physics.util.Vector2D;

public class WormMovementOnGround extends Effect {
	public static final WormMovementOnGround GLOBAL_MOVE_EFFECT = new WormMovementOnGround();
	public static final Vector2D DIRECTION_RIGHT=new Vector2D();
	public static final double DESIRED_VELOCITY=0.05;
	
	@Expose boolean left;
	
	public WormMovementOnGround(){
		left=false;
	}
	@Override
	public void apply(CollisionObject collisionObject) {
		double x = collisionObject.getSpeed().getX();
		double help=0;
		if(left){
			if(x<=DESIRED_VELOCITY) return;
			else help=-DESIRED_VELOCITY-x;
		}
		else{
			if(x>=DESIRED_VELOCITY) return;
			else help=DESIRED_VELOCITY-x;
		}
		collisionObject.acceleration.addVector(new Vector2D(help,0));
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	
}
