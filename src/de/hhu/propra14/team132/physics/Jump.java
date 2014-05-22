package de.hhu.propra14.team132.physics;

import com.google.gson.annotations.Expose;


public class Jump extends Effect{
	@Expose public static double JUMP_VALUE = 0.01;
	@Expose public static Jump GLOBAL_JUMP_EFFECT = new Jump();
	
	@Override
	public void apply(CollisionObject o) {
		o.speed.setY(o.speed.getY()+1);
	}

}
