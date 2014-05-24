package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Projectile extends GameObject implements Drawable{
    public Projectile(int teamID) {
        super(teamID);
    }

    public Projectile() {
       super();
    }

    //CollisionObject-Methods
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
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)this.getPosition().getX(),(int)this.getPosition().getY(),10,10);
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
