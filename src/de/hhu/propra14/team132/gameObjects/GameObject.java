package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public abstract class GameObject extends CollisionObject implements Drawable {
	Class<?> clz;
    public GameObject(int teamID) {
       super(teamID);
    }
    public GameObject(ConvexCollisionShape[] shapes, int teamID) {
        super(shapes,teamID);
    }
    public GameObject(ConvexCollisionShape shape, int teamID) {
        super(shape,teamID);
    }
    public GameObject() {
    	super();
    }

}
