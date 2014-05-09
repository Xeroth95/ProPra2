package de.hhu.propra14.team132.physics;

import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.physics.util.Vector2D;

public class Wind extends Effect{
	public Vector2D windVector;
	public Wind(){
		windVector = new Vector2D(Math.random(),0);
	}
	@Override
	public void apply(GameObject o) {
		o.getAcceleration().addVector(windVector);
	}

}
