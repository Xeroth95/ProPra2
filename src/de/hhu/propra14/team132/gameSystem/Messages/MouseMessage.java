package de.hhu.propra14.team132.gameSystem.Messages;

import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

/**
 * Created by isabel on 07.05.14.
 */
public class MouseMessage extends Message {
    public enum Button {
        LEFT,
        RIGHT,
        MIDDLE
    }
    Button button;
    public MouseMessage(Button button) {
        super(MessageType.MOUSE);
        this.button=button;
    }

    public Button getButton() {
        return button;
    }

}
