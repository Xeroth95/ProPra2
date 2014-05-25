package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Projectile;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isabel on 02.05.14.
 */
public abstract class Weapon implements Drawable{
    private int currentAmmo;
    private static final int INFINITE_AMMO=-1;
    public Weapon() {
    	this(INFINITE_AMMO);
    }

    public Weapon(int ammo) {
    	this.currentAmmo=ammo;
    }


    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }
    public abstract void shoot(Vector2D MousePosition, Vector2D StartPosition, double power);

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
    }
    public abstract Projectile createNewProjectile(/*I have no Idea!*/); //TODO: this is very questionable. The responsibility should lie with the implementing classes. I just don't know what parameters to put there.

}
