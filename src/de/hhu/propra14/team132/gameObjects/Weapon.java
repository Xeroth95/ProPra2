package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

/**
 * Created by isabel on 02.05.14.
 */
public class Weapon extends GameObject {
    private Map map;
    private int teamID;
    private CollisionObject o;

    //constructors:
    public Weapon(int teamID, Map map, int teamID1) {
        super(teamID, map);
    }

    public Weapon(ConvexCollisionShape[] shapes, int teamID, Map map, int teamID1) {
        super(shapes, teamID, map);
    }

    public Weapon(ConvexCollisionShape shape, int teamID, Map map, Map map1, int teamID1) {
        super(shape, teamID, map);
    }
    @Override public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }

}
