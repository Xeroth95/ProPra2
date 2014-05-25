package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.PistolProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

/**
 * Created by isabel on 23.05.14.
 */
public class Pistol extends Weapon {
    @Override
    public Projectile shoot(Vector2D MousePosition, Vector2D StartPosition, double power) {
        return null;
    }


    //todo: How do we do the shooting?

    @Override
    public Projectile createNewProjectile(Vector2D speed, Vector2D acceleartion) {
        return new PistolProjectile();
    }
    @Override
    public Projectile createNewProjectile() {
        return new PistolProjectile();
    }

    @Override
    public Projectile createNewProjectile(Vector2D dir, Vector2D startPosition, double power) {
        return null;
    }
}