package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public class Terrain extends GameObject {
    public Terrain(Map map) {
        super(Player.WORLD, map);
    }

    public Terrain(ConvexCollisionShape[] shapes, Map map) {
        super(shapes, Player.WORLD, map);
    }

    public Terrain(ConvexCollisionShape shape, Map map) {
        super(shape, Player.WORLD, map);
        this.shape=shape;
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

    @Override
    public void draw(Graphics2D g, Paint paint, int posX, int posY) {

    }

    @Override
    public void draw(Graphics2D g, Paint paint, int posX, int posY, int sizeX, int sizeY) {

    }

    @Override
    public void draw(Graphics2D g, Paint paint) {
        g.setPaint(paint);
        g.fillPolygon(shape.getPolygonToDraw());
    }
	@Override
	public ArrayList<Effect> getInitalEffects() {
		// TODO Auto-generated method stub
		return null;
	}

}
