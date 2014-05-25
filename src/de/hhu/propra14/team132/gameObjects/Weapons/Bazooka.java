package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

/**
 * Created by isabel on 23.05.14.
 */
public class Bazooka extends Weapon {

    public Projectile shoot(Vector2D mousePosition, Vector2D startPosition, double power) {
    	Vector2D dir = Weapon.getDirectionVector(mousePosition, startPosition);
    	return null;
    }

    @Override
    public Projectile createNewProjectile(Vector2D speed,Vector2D acceleration) {

        Projectile projectile=new BazookaProjectile();
        projectile.setSpeed(speed);
        projectile.setAcceleration(acceleration);
        return projectile;
    }

    @Override
    public Projectile createNewProjectile() {
        return null;
    }
}
