package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject {
    private String name;
    private int life;
    private static final Vector2D[] vertices;
    private final ConvexCollisionShape shape;
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
    	vertices = vert;
    }

    BufferedImage textureImage;
    TexturePaint texture;
    
    //constructors:
    public Worm(int teamID, Map map, String name){
        super(new ConvexCollisionShape(vertices), teamID, map);
        shape=this.collisionShapes[0];
        this.name = name;
        
        try{
	        textureImage= ImageIO.read(new File("resources/img/textures/nic.png"));
	        texture=new TexturePaint(textureImage, new Rectangle(0,0,48,48));
        }catch(IOException e){
        	System.err.println("Unable to load Texture for the Worms");
        	e.printStackTrace();
        	System.exit(-1);
        }
    }
    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }

    @Override
    public double getInitialBounciness() {
        return 0.7;
    }

    @Override
    public double getInitialFriction() {
        return 0;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(texture);
        g2d.fillPolygon(shape.getPolygonToDraw());
    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		ArrayList<Effect> effects = new ArrayList<>();
		effects.add(Gravity.GLOBAL_GRAVITY);
		return effects;
	}
}