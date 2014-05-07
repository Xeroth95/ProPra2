package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.physics.CollisionObject;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

/**
 * Created by isabel on 02.05.14.
 */
public class Worm extends GameObject{
    private String name;
    private int life;
    private int teamID;

    public Worm(int teamID, Map map, GameManager gameManager, String name, int life, int teamID1) {
        super(teamID, map, gameManager);
        this.name = name;
        this.life = life;
        teamID = teamID1;
    }

    public Worm(ConvexCollisionShape[] shapes, int teamID, Map map, GameManager gameManager, String name, int life, int teamID1) {
        super(shapes, teamID, map, gameManager);
        this.name = name;
        this.life = life;
        teamID = teamID1;
    }

    public Worm(ConvexCollisionShape shape, int teamID, Map map, GameManager gameManager, String name, int life, int teamID1) {
        super(shape, teamID, map, gameManager);
        this.name = name;
        this.life = life;
        teamID = teamID1;
    }

    @Override
    public void furtherCollisionWith(CollisionObject o) {
        //do nothing
    }
}
