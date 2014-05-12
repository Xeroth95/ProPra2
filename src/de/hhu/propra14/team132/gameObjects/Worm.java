package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.MouseMessage;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject {
    private String name;
    private int life;
    //constructors:
    public Worm(int teamID, Map map, String name) {
        super(teamID, map);
        this.name = name;
    }

    public Worm(ConvexCollisionShape[] shapes, int teamID, Map map, String name) {
        super(shapes, teamID, map);
        this.name = name;
    }

    public Worm(ConvexCollisionShape shape, int teamID, Map map, String name) {
        super(shape, teamID, map);
        this.name = name;
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
        g.setPaint(paint);
        g.fillOval(posX, posY, 10, 10);
    }

    @Override
    public void draw(Graphics2D g, Paint paint, int posX, int posY, int sizeX, int sizeY) {

    }

    @Override
    public void draw(Graphics2D g, Paint paint) {

    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		ArrayList<Effect> effects = new ArrayList<>();
		effects.add(Gravity.GLOBAL_GRAVITY);
		return effects;
	}
}