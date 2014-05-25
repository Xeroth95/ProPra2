package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectile;

/**
 * Created by isabel on 23.05.14.
 */
public class Skip extends Weapon {
	
    @Override
    public void shoot() {
        //do something with the Projetile; todo: I do not know how to "shoot"
    }
    //todo: How do we do the shooting?

    @Override
	public Projectile createNewProjectile() {
		return new BazookaProjectile();
	}
}