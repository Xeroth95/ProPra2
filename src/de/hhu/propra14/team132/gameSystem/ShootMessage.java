package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.physics.util.Vector2D;


/**
 * Created by isabel on 07.05.14.
 */
public class ShootMessage extends Message {
    double power;
    Vector2D mousePosition;
    public ShootMessage(double power, Vector2D mousePosition) {
        super(MessageType.SHOOT);
        this.power=power;
        this.mousePosition=mousePosition;
    }

    public double getPower() {
        return power;
    }

    public Vector2D getMousePosition() {
        return mousePosition;
    }
}
