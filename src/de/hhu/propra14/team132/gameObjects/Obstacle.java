package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by isabel on 09.05.14.
 */
public class Obstacle extends Terrain{
	
    transient static BufferedImage textureImage;
    transient static TexturePaint texture;
    
    static{
    	try{
    		textureImage= ImageIO.read(new File("res/img/textures/obstacle2.png"));
            texture=new TexturePaint(textureImage, new Rectangle(0,0,128,128));
    	}catch(IOException e){
    		System.err.println("could not load the Texture for the Terrain!");
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

    public Obstacle(boolean destroyable) {
        super();
        runTimeTexture=texture;
    }

    public Obstacle(ConvexCollisionShape[] shapes) {
        super(shapes);
        runTimeTexture=texture;
    }

    public Obstacle(ConvexCollisionShape shape) {
        super(shape);
        runTimeTexture=texture;
    }
    public Obstacle() {
    	super();
    	runTimeTexture=texture;
    }

}
