package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
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
public class Weapon implements Drawable{
    
    //TODO:actually implement the things this Class needs, like the projectile it shoots etc.
    private int maxAmmo;
    private int currentAmmo;
    Projectile projectile;
    public Weapon() {

    }

    public Weapon(int maxAmmo, int currentAmmo, Projectile projectile) {
        this.maxAmmo = maxAmmo;
        this.currentAmmo = currentAmmo;
        this.projectile = projectile;
    }

    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }

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

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }
}
