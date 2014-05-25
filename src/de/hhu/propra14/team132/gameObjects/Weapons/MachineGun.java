package de.hhu.propra14.team132.gameObjects.Weapons;

import de.hhu.propra14.team132.gameObjects.Projectiles.BazookaProjectile;
import de.hhu.propra14.team132.gameObjects.Projectiles.MachineGunProjectile;
import de.hhu.propra14.team132.gameObjects.Projectiles.Projectile;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by isabel on 23.05.14.
 */
public class MachineGun extends Weapon {
    @Override
    public Projectile shoot(Vector2D mousePosition, Vector2D startPosition, double power) {

        Vector2D dir = Weapon.getDirectionVector(mousePosition, startPosition);
        return this.createNewProjectile(dir,startPosition,power);
    }

    //todo: How do we do the shooting?


    @Override
    public Projectile createNewProjectile(Vector2D speed, Vector2D acceleartion) {
        return new MachineGunProjectile();
    }
    @Override
    public Projectile createNewProjectile() {
        return new MachineGunProjectile();
    }
    @Override
    public Projectile createNewProjectile(Vector2D dir, Vector2D startPosition, double power) {
        Projectile projectile=new MachineGunProjectile();
        projectile.setPosition(new Vector2D(startPosition.getX()+45*dir.getX(),startPosition.getY()+45*dir.getY()));
        projectile.setSpeed(new Vector2D(dir.getX()*power*10,dir.getY()*power*10));
        return projectile;
    }
    @Override
    public void draw(Graphics2D g2d, JPanel p) {

    }
}