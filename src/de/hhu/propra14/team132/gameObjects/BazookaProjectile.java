package de.hhu.propra14.team132.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public class BazookaProjectile extends Projectile{
    public BazookaProjectile() {
    }

    public BazookaProjectile(int teamID) {
        super(teamID);
    }

	@Override
	public void draw(Graphics2D g2d, JPanel p) {//TODO: make a real draw out of this stub
		g2d.setColor(Color.BLACK);
		for(ConvexCollisionShape s: this.getCollisionShapes()){
			g2d.fillPolygon(s.getPolygonToDraw());
		}
	}

	@Override
	public void furtherCollisionWith(CollisionObject o) {
		//TODO: how about reducing the life of the object?
	}

	@Override
	public double getInitialBounciness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getInitialFriction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Effect> getInitalEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollisionMode getInitialCollisionMode() {
		// TODO Auto-generated method stub
		return null;
	}
}
