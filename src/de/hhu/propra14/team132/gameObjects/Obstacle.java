package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.GUI.Drawable;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

import java.io.IOException;

/**
 * Created by isabel on 09.05.14.
 */
public class Obstacle extends Terrain{
    private boolean destroyable;

    public Obstacle(Map map, boolean destroyable) {
        super(map);
        this.destroyable = destroyable;
    }

    public Obstacle(ConvexCollisionShape[] shapes, Map map, boolean destroyable) {
        super(shapes, map);
        this.destroyable = destroyable;
    }

    public Obstacle(ConvexCollisionShape shape, Map map, boolean destroyable) {
        super(shape, map);
        this.destroyable = destroyable;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setDestroyable(boolean destroyable) {
        this.destroyable = destroyable;
    }
}
