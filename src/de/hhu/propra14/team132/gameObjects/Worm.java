package de.hhu.propra14.team132.gameObjects;

import com.google.gson.annotations.Expose;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.Gravity;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject {
    private String name;

    private static final Vector2D[] vertices;

    private final ConvexCollisionShape shape; //TODO: weird behaviour after loading!!
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

    transient Image textureImage;
    String pathToTexture;
    //constructors:
    public Worm(int teamID, String name){
        super(new ConvexCollisionShape(vertices), teamID);
        shape=this.collisionShapes[0];
        this.name = name;

        try{
	        textureImage=ImageIO.read(new File(pathToTexture));
            textureImage=textureImage.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        }catch(IOException e){
        	System.err.println("Unable to load Texture for the Worms!");
        	e.printStackTrace();
        	System.exit(-1);
        }
    }

    public Worm() {

        super(new ConvexCollisionShape(vertices), 0);
        shape=this.collisionShapes[0];
        this.name = "TestName";

        try{
            textureImage=ImageIO.read(new File(pathToTexture));
            textureImage=textureImage.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        }catch(IOException e){
            System.err.println("Unable to load Texture for the Worms!");
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
        return 0.5;
    }

    @Override
    public void draw(Graphics2D g2d, JPanel p) {
        g2d.drawImage(textureImage, (int)this.collisionShapes[0].getPositionX(), (int)this.collisionShapes[0].getPositionY(), p); //TODO: using shape results in the worm being painted at 0,0 when being deserialized!
    }

	@Override
	public ArrayList<Effect> getInitalEffects() {
		ArrayList<Effect> effects = new ArrayList<>();
		effects.add(Gravity.GLOBAL_GRAVITY);
		return effects;
	}
	@Override
	public CollisionMode getInitialCollisionMode() {
		return CollisionMode.NOT_EXPLOADING;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathToTexture() {
		return pathToTexture;
	}

	public void setPathToTexture(String pathToTexture) {
		this.pathToTexture = pathToTexture;
	}
	
   
}