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
public class Worm extends GameObject {
    private String name;
    private int life;
    private int teamID;

    public Worm(int teamID, Map map, GameManager gameManager, String name, int teamID1) {
        super(teamID, map, gameManager);
        this.name = name;
        teamID = teamID1;
    }

    public Worm(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager, String name, int teamID1) {
        super(shapes, teamID, map, gameManager);
        this.name = name;
        teamID = teamID1;
    }

    public Worm(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager, String name, int teamID1) {
        super(shape, teamID, map, gameManager);
        this.name = name;
        teamID = teamID1;
    }


    @Override
    public void receiveMessage(Message m)  {

    }
    @Override
    public void addToMessageLists(GameManager gameManager) {

    }
    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
    //Drawable Methods:
    public void draw(Graphics g, int posX, int posY) {
        g.drawLine(0,0,200,200);
    }
}