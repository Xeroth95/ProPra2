package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Projectile extends GameObject{
    public Projectile(int teamID) {
        super(teamID);
    }

    public Projectile() {
       super();
    }
}
