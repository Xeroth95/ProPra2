package de.hhu.propra14.team132.physics;

import com.google.gson.annotations.Expose;


public class Jump extends Effect{
	@Expose public final static double JUMP_VALUE = 0.1;
	@Expose public final static Jump GLOBAL_JUMP_EFFECT = new Jump();
	
	@Override
	public void apply(CollisionObject o) {
		o.speed.setY(o.speed.getY()+JUMP_VALUE);
		o.removeEffect(this);
	}
	

}
