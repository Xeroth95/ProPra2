package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.MouseMessage;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject {
    private String name;
    private int life;
    //constructors:
    public Worm(int teamID, Map map, GameManager gameManager, String name) {
        super(teamID, map, gameManager);
        this.name = name;
    }

    public Worm(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager, String name) {
        super(shapes, teamID, map, gameManager);
        this.name = name;
    }

    public Worm(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager, String name) {
        super(shape, teamID, map, gameManager);
        this.name = name;
    }


    @Override
    public void receiveMessage(Message m)  {
        MessageType messageType=m.getMessageType();
        switch(messageType) {
            case MOUSE:
                MouseMessage.Button button=((MouseMessage)m).getButton();
                switch(button) {
                    case LEFT:
                        System.out.println("left");
                        break;
                    case RIGHT:
                        System.out.println("right");
                        break;
                    case MIDDEL:
                        System.out.println("middle");
                        break;
                }
            case KEYBOARD:
         }

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
        g.setColor(Color.PINK);
        g.fillOval(posX, posY, 10, 10);
    }

    public Worm(int teamID, Map map, GameManager gameManager) {
        super(teamID, map, gameManager);
    }

    @Override
    public void draw(Graphics g, int posX, int posY, int sizeX, int sizeY) {

    }

    @Override
    public void draw(Graphics g) {

    }
}