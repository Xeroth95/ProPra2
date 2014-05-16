package de.hhu.propra14.team132.gameSystem;

import com.google.gson.InstanceCreator;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameObjects.Worm;

import java.lang.reflect.Type;

/**
 * Created by isabel on 16.05.14.
 */
public class WormInstanceCreator implements InstanceCreator<GameObject> {
    @Override
    public GameObject createInstance(Type type) {
        return new Worm();
    }
}
