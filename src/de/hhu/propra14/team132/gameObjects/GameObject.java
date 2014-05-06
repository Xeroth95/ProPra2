package de.hhu.propra14.team132.gameObjects;

import de.hhu.propra14.team132.physics.CollisionObject;

public abstract class GameObject extends CollisionObject{
    private String name;
    public GameObject(String name) {
        this.name=name;
    }
}
