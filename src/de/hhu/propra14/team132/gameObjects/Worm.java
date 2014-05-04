package de.hhu.propra14.team132.gameObjects;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject{
    private String name;
    private int life;
    private int teamID;
    public Worm(String name, int teamID) {
        super(name);
        this.teamID = teamID;
        this.life=100;
    }
}
