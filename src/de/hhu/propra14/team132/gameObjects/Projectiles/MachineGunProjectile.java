package de.hhu.propra14.team132.gameObjects.Projectiles;

import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MachineGunProjectile extends Projectile {
    public MachineGunProjectile() {
    }

    public MachineGunProjectile(int teamID) {
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
		return e;
	}

	@Override
	public CollisionMode getInitialCollisionMode() {
		return CollisionMode.EXPLOADING;
	}
}
