package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;

/**
 * Created by isabel on 06.05.14.
 */
public class Projectile extends GameObject{
    public Projectile(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager) {
        super(shapes, teamID, map, gameManager);
    }

    public Projectile(int teamID, Map map, GameManager gameManager) {
        super(teamID, map, gameManager);
    }

    public Projectile(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager) {
        super(shape, teamID, map, gameManager);
    }
    //Communicatable-Methods
    @Override
    public void receiveMessage(Message m)  {

    }
    @Override
    public void addToMessageLists(GameManager gameManager) {

    }
    //CollsionObject-Methods
    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
    //Drawable Methods:
    public void draw(Graphics g, int posX, int posY) {

    }
}
