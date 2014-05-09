package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public abstract class GameObject extends CollisionObject implements Drawable {

    private Map map;
    public ConvexCollisionShape shape;
    private ConvexCollisionShape[] shapes;
    public GameObject(int teamID, Map map) {
       super(teamID,map);
    }
    public GameObject(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes,teamID,map);
    }
    public GameObject(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape,teamID,map);
    }
}
