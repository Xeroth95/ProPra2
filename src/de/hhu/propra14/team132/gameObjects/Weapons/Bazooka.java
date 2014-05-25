package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

/**
 * Created by isabel on 23.05.14.
 */
public class Bazooka extends Weapon {
    @Override
    public Projectile shoot(Vector2D MousePosition, Vector2D StartPosition, double power) {
        //I really have no Idea how the Vectors work!
        Vector2D ProjectileSpeed=new Vector2D();
        ProjectileSpeed.setX(MousePosition.getX()-StartPosition.getX());
        ProjectileSpeed.setY(MousePosition.getY()-StartPosition.getY());
        Vector2D ProjectileAcc=new Vector2D();
        ProjectileSpeed.setX((MousePosition.getX()-StartPosition.getX())*power);
        ProjectileSpeed.setY((MousePosition.getY()-StartPosition.getY())*power);
        return this.createNewProjectile();
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
