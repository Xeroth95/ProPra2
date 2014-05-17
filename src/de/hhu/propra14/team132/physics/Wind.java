package de.hhu.propra14.team132.physics;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.physics.util.Vector2D;

public class Wind extends Effect{
	@Expose public Vector2D windVector;
	public Wind(){
		windVector = new Vector2D(Math.random(),0);
	}
	@Override
	public void apply(CollisionObject o) {
		o.getAcceleration().addVector(windVector);
	}

}
