package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Weapon implements Drawable{
    private Map map;
    //constructors:
    
    //TODO:actually implement the things this Class needs, like the projectile it shoots etc.

    public Weapon(int teamID, Map map) {
        this.map = map;
    }


    //Drawable Methods:
    public void draw(Graphics g, Color color, int posX, int posY) {

    }

    @Override
    public void draw(Graphics2D g2d) {

    }


}
