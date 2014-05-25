package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isabel on 23.05.14.
 */
public class Bazooka extends Weapon {

    public Projectile shoot(Vector2D mousePosition, Vector2D startPosition, double power) {
    	Vector2D dir = Weapon.getDirectionVector(mousePosition, startPosition);
    	return this.createNewProjectile(dir,startPosition,power);
    }

    @Override
    public Projectile createNewProjectile(Vector2D speed,Vector2D acceleration) {

        Projectile projectile=new BazookaProjectile();
        projectile.setSpeed(speed);
        projectile.setAcceleration(acceleration);
        return projectile;
    }

    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }

    @Override
    public Projectile createNewProjectile(Vector2D dir, Vector2D startPosition, double power) {
        Projectile projectile=new BazookaProjectile();
        projectile.setPosition(new Vector2D(startPosition.getX()+30*dir.getX(),startPosition.getY()+30*dir.getY()));
        projectile.setSpeed(new Vector2D(dir.getX()*power*10,dir.getY()*power*10));
        return projectile;
    }

    @Override
    public Projectile createNewProjectile() {
        return null;
    }
}
