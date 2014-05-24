package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;

/**
 * Created by isabel on 23.05.14.
 */
public class Bazooka extends Weapon {
    public Bazooka(int maxAmmo, int currentAmmo) {
        super(maxAmmo, currentAmmo);
    }

    public Bazooka(){ // actually implement a proper constructor instead of just doing nothing!
    	super(); //Constructor for INFINITE_AMMO
    }

    public Bazooka(int maxAmmo) { //Constructor for not infiniteAmmo
        super(maxAmmo);
    }

    @Override
    public void shoot() {
        //do something with the Projetile; todo: I do not know how to "shoot"
        if(this.getMaxAmmo()!=-1) {
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
