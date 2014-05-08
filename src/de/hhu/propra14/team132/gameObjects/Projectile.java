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
    public void receiveMessage(Message m)  { //Todo: Which Message should

    }
    @Override
    public void register(GameManager gameManager) {

    }
    //CollsionObject-Methods
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

    @Override
    public void draw(Graphics g, int posX, int posY, int sizeX, int sizeY) {

    }

    @Override
    public void draw(Graphics g) {

    }
}
