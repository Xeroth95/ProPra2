package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject {
    private String name;
    private int life;
    private static final ConvexCollisionShape shape;
    
    static{
    	Vector2D[] vert=new Vector2D[8];
    	vert[0]=new Vector2D(10,0);
    	vert[1]=new Vector2D(20,0);
    	vert[2]=new Vector2D(30,10);
    	vert[3]=new Vector2D(30,20);
    	vert[4]=new Vector2D(20,30);
    	vert[5]=new Vector2D(10,30);
    	vert[6]=new Vector2D(0,20);
    	vert[7]=new Vector2D(0,10);
    	shape = new ConvexCollisionShape(vert);
    }
    
    //constructors:
    public Worm(int teamID, Map map, String name) {
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