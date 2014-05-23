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

    public Bazooka(){ // actually implement a proper constuctor instead of just doing nothing!
    	super();
    }

	@Override
	public Projectile createNewProjectile() {
		return new BazookaProjectile();
	}
}
