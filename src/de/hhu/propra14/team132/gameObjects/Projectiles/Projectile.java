package de.hhu.propra14.team132.gameObjects.Projectiles;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Projectile extends GameObject implements Drawable{
    public Projectile(int teamID) {
        super(teamID);
    }

    public Projectile() {
       super();
    }
}
