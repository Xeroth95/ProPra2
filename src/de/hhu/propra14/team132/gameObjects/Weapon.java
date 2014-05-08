package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;

/**
 * Created by isabel on 02.05.14.
 */
public class Weapon extends GameObject {
    private Map map;
    private int teamID;
    private CollisionObject o;

    //constructors:


    public Weapon(int teamID, Map map, GameManager gameManager, Map map1, int teamID1, CollisionObject o) {
        super(teamID, map, gameManager);
        map = map1;
        teamID = teamID1;
        this.o = o;
    }

    public Weapon(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager, Map map1, int teamID1, CollisionObject o) {
        super(shapes, teamID, map, gameManager);
        map = map1;
        teamID = teamID1;
        this.o = o;
    }

    public Weapon(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager, Map map1, int teamID1, CollisionObject o) {
        super(shape, teamID, map, gameManager);
        map = map1;
        teamID = teamID1;
        this.o = o;
    }
    @Override
    public void receiveMessage(Message m)  {

    }
    @Override
    public void register(GameManager gameManager) {

    }
    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
    //Drawable Methods:
    public void draw(Graphics g, int posX, int posY) {

    }
}
