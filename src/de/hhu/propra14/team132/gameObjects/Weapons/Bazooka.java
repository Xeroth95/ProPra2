package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

/**
 * Created by isabel on 23.05.14.
 */
public class Bazooka extends Weapon {
    @Override
    public void shoot(Vector2D MousePosition, Vector2D StartPosition, double power) {
        if(this.getCurrentAmmo()!=-1) {
            if(this.getCurrentAmmo()>0) {
                this.setCurrentAmmo(this.getCurrentAmmo() - 1); //decreases the currentAmmo;
            } else {
                System.out.println("Ammo is empty, reload");
            }
        }
    }

    @Override
    public Projectile createNewProjectile() {
        return null;
    }
}
