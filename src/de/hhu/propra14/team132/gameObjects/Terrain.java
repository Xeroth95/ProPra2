package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public class Terrain extends GameObject {
    public Terrain(int teamID, Map map, GameManager gameManager) {
        super(teamID, map, gameManager);
    }

    public Terrain(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager) {
        super(shapes, teamID, map, gameManager);
    }

    public Terrain(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager) {
        super(shape, teamID, map, gameManager);
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
       // g.fillPolygon(this..getPolygonToDraw());
    }

    @Override
    public void draw(Graphics g, int posX, int posY, int sizeX, int sizeY) {

    }

    @Override
    public void draw(Graphics g) {

    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		// TODO Auto-generated method stub
		return null;
	}

}
