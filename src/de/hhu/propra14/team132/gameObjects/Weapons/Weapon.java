package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public abstract class Weapon implements Drawable{
    private int maxAmmo;	// there is a cap??
    private int currentAmmo;
    private static final int INFINITE_AMMO=-1;
    public Weapon() {
    	this(INFINITE_AMMO);
    }

    public Weapon(int maxAmmo, int currentAmmo) { //todo: I would like to delete this constructor and replace it with the constructor below
        this.maxAmmo = maxAmmo;
        this.currentAmmo = currentAmmo;
        //what?? how is projectile not an abstract class? Why would you want to create an instance of Projectile? this should not be allowed!!
    }
    public Weapon(int currentAmmo) {
        this.currentAmmo = currentAmmo; //At the beginning the currentAmmo is the maxAmmo
    }

    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }
    public abstract void shoot();  //todo: ist this a good idea? this method should shoot the Pojectile and then creates a new one
    //getter and setter:
    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
    }
    public abstract Projectile createNewProjectile(/*I have no Idea!*/); //TODO: this is very questionable. The responsibility should lie with the implementing classes. I just don't know what parameters to put there.

}
