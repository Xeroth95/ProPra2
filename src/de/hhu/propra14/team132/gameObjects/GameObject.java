package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.Communicatable;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public abstract class GameObject extends CollisionObject implements Drawable {

    private Map map;
    public ConvexCollisionShape shape;
    private ConvexCollisionShape[] shapes;
    private GameManager gameManager;
    public GameObject(int teamID, Map map, GameManager gameManager) {
       super(teamID,map);
       this.gameManager=gameManager;
    }
    public GameObject(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager) {
        super(shapes,teamID,map);
        this.gameManager=gameManager;
    }
    public GameObject(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager) {
        super(shape,teamID,map);
        this.gameManager=gameManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
    //  public abstract void furtherCollisionWith(CollisionObject o);

}
