package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject{
    private String name;
    private int life;
    private int teamID;
    public Worm(int teamID, Map map) {
        super(teamID,map);
    }
}
