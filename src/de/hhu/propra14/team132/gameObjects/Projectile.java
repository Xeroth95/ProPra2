package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public class Projectile extends GameObject{
    public Projectile(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes, teamID, map);
    }

    public Projectile(int teamID, Map map, GameManager gameManager) {
        super(teamID, map);
    }

    public Projectile(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape, teamID, map);
    }

    //CollsionObject-Methods
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
    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		ArrayList<Effect> effects = new ArrayList<>();
		effects.add(Gravity.GLOBAL_GRAVITY);
		return null;
	}

	@Override
	public CollisionMode getInitialCollisionMode() {
		return CollisionMode.NOT_EXPLOADING;
	}
}
