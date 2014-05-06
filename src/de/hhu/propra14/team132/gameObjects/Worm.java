package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject{
    private String name;
    private int life;
    private int teamID;

    public Worm(int teamID, Map map) {
        super(teamID, map);
    }

    public Worm(ConvexCollisionShape[] shapes, int teamID, Map map) {
        super(shapes, teamID, map);
    }

    public Worm(ConvexCollisionShape shape, int teamID, Map map) {
        super(shape, teamID, map);
    }
    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
}
