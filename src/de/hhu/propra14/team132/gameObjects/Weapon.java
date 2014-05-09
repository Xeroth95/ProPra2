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


    public Weapon(int teamID, Map map, GameManager gameManager, Map map1, CollisionObject o) {
        super(teamID, map, gameManager);
        map = map1;
        teamID = teamID;
        this.o = o;
    }

    public Weapon(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager, Map map1, CollisionObject o) {
        super(shapes, teamID, map, gameManager);
        map = map1;
        teamID = teamID;
        this.o = o;
    }

    public Weapon(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager, Map map1, CollisionObject o) {
        super(shape, teamID, map, gameManager);
        map = map1;
        teamID = teamID;
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

    @Override
    public double getInitialBounciness() {
        return 0;
    }

    @Override
    public double getInitialFriction() {
        return 0;
    }

    //Drawable Methods:
    public void draw(Graphics g, int posX, int posY) {

    }

    public Weapon(int teamID, Map map, GameManager gameManager) {
        super(teamID, map, gameManager);
    }

    @Override
    public void draw(Graphics g, int posX, int posY, int sizeX, int sizeY) {

    }
    @Override
    public void draw(Graphics g) {

    }

}
