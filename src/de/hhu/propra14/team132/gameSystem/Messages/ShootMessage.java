package de.hhu.propra14.team132.gameSystem.Messages;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.physics.util.Vector2D;


/**
 * Created by isabel on 07.05.14.
 */
public class ShootMessage extends Message {
    double power;
    Vector2D mousePosition;
    public ShootMessage(double power, Vector2D mousePosition, Communicable sender) {
        super(MessageType.SHOOT, sender);
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
