package de.hhu.propra14.team132.physics;

import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.physics.util.Vector2D;

public class Gravity extends Effect{
	public static double GRAVITY_CONSTANT = -0.5;
	public static Vector2D GRAVITY_ACCELERATION_VECTOR = new Vector2D(0,GRAVITY_CONSTANT);
	@Override
	public void apply(GameObject o) {
		o.getAcceleration().addVector(GRAVITY_ACCELERATION_VECTOR);
	}
	public void change(double newGravConst) {
		GRAVITY_CONSTANT = newGravConst;
		GRAVITY_ACCELERATION_VECTOR.setY(newGravConst);
	}
}
