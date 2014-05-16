package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.CollisionMode;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */


public class Terrain extends GameObject {
    transient static BufferedImage textureImage;
    transient static TexturePaint texture;
    
    static{
    	try{
    		textureImage= ImageIO.read(new File("res/img/textures/terrain.jpg"));
            texture=new TexturePaint(textureImage, new Rectangle(0,0,48,48));
    	}catch(IOException e){
    		System.err.println("could not load the Texture for the Terrain!");
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

    public Terrain(Map map)  {
        super(Player.WORLD, map);
        this.collisionTranslationBehaviour=CollisionObject.TRANSLATION_BEHAVIOUR_UNMOVING;
        
    }

    public Terrain(ConvexCollisionShape[] shapes, Map map) {
        super(shapes, Player.WORLD, map);
        this.collisionTranslationBehaviour=CollisionObject.TRANSLATION_BEHAVIOUR_UNMOVING;
    }

    public Terrain(ConvexCollisionShape shape, Map map) {
        super(shape, Player.WORLD, map);
        this.collisionTranslationBehaviour=CollisionObject.TRANSLATION_BEHAVIOUR_UNMOVING;
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
    public void draw(Graphics2D g2d, JPanel p){
        g2d.setPaint(this.texture);
        for(ConvexCollisionShape s : this.collisionShapes){
        	g2d.fillPolygon(s.getPolygonToDraw());
        }
    }
	@Override
	public ArrayList<Effect> getInitalEffects() {
		return new ArrayList<Effect>();
	}

	@Override
	public CollisionMode getInitialCollisionMode() {
		return CollisionMode.NOT_EXPLOADING;
	}

}
