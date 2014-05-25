package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;

/**
 * Created by isabel on 23.05.14.
 */
public class Pistol extends Weapon {


    @Override
    public void shoot() {
        //do something with the Projetile; todo: I do not know how to "shoot"
        if(this.getCurrentAmmo()!=-1) {
            if(this.getCurrentAmmo()>0) {
                this.setCurrentAmmo(this.getCurrentAmmo() - 1); //decreases the currentAmmo;
            } else {
                System.out.println("Ammo is empty, reload");
            }
        }
    }
    //todo: How do we do the shooting?

    @Override
	public Projectile createNewProjectile() {
		return new BazookaProjectile();
	}
}