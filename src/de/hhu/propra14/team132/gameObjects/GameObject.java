package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public abstract class GameObject extends CollisionObject{
    private String name;
    private Map map;
    private ConvexCollisionShape shape;
    private ConvexCollisionShape[] shapes;
    private int teamID;
    public GameObject(int teamID, Map map) {
       super(teamID,map);
    }
    public GameObject(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes,teamID,map);
    }
    public GameObject(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape,teamID,map);
    }
  //  public abstract void furtherCollisionWith(CollisionObject o);

}
