package de.hhu.propra14.team132.gameObjects.Projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public class BazookaProjectile extends Projectile {
    public BazookaProjectile() {
    }

    public BazookaProjectile(int teamID) {
        super(teamID);
    }

	@Override
	public void draw(Graphics2D g2d, JPanel p) {//TODO: make a real draw out of this stub
		g2d.setColor(Color.BLACK);
		g2d.fillOval((int)this.getPosition().getX(), (int)this.getPosition().getY(), 10, 10);
	}

	@Override
	public void furtherCollisionWith(CollisionObject o) {
		//TODO: how about creating an explosionObject and deleting itself? the bazooka should do something like that.
		o.setLife(o.getLife()-1);
		this.setMarkedForDeletion(true);
	}

	@Override
	public double getInitialBounciness() {
		return 0;
	}

	@Override
	public double getInitialFriction() {
		return 0;
	}

	@Override
	public ArrayList<Effect> getInitalEffects() {
		ArrayList<Effect> e =new ArrayList<Effect>();
		e.add(Gravity.GLOBAL_GRAVITY);
		return e;
	}

	@Override
	public CollisionMode getInitialCollisionMode() {
		return CollisionMode.EXPLOADING;
	}
}
