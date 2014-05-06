package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

/**
 * Created by isabel on 06.05.14.
 */
public class Terrain extends GameObject {
    public Terrain(int teamID, Map map) {
        super(teamID, map);
    }

    public Terrain(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes, teamID, map);
    }

    public Terrain(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape, teamID, map);
    }

    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
}
