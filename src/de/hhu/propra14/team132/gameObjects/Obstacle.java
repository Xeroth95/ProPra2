package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

/**
 * Created by isabel on 09.05.14.
 */
public class Obstacle extends Terrain{
    private boolean destroyable;

    public Obstacle(int teamID, Map map, boolean destroyable) {
        super(teamID, map);
        this.destroyable = destroyable;
    }

    public Obstacle(ConvexCollisionShape[] shapes, int teamID, Map map, boolean destroyable) {
        super(shapes, teamID, map);
        this.destroyable = destroyable;
    }

    public Obstacle(ConvexCollisionShape shape, int teamID, Map map, boolean destroyable) {
        super(shape, teamID, map);
        this.destroyable = destroyable;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setDestroyable(boolean destroyable) {
        this.destroyable = destroyable;
    }
}
