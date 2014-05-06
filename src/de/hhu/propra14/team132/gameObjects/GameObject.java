package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.physics.CollisionObject;

public abstract class GameObject {
    private String name;
    private Map map;
    private int teamID;
    public GameObject(int teamID, Map map) {
       // super(teamID,map);
    }

}
