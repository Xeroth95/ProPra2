package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Weapon extends GameObject {
    private Map map;
    //constructors:


    public Weapon(int teamID, Map map) {
        super(teamID, map);
        map = map;
    }

    public Weapon(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes, teamID, map);
        map = map;
    }

    public Weapon(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape, teamID, map);
        map = map;
    }

    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }

    @Override
    public double getInitialBounciness() {
        return 0;
    }

    @Override
    public double getInitialFriction() {
        return 0;
    }

    //Drawable Methods:
    public void draw(Graphics g, Color color, int posX, int posY) {

    }

    @Override
    public void draw(Graphics2D g2d) {

    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		// TODO Auto-generated method stub
		return null;
	}

}
