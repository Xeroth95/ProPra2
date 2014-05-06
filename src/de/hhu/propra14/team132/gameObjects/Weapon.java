package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;

/**
 * Created by isabel on 02.05.14.
 */
public class Weapon extends GameObject {
    private Map map;
    private int teamID;
    public Weapon(int teamID, Map map) {
        super(teamID,map);
    }
}
