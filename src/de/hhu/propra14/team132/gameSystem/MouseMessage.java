package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class MouseMessage extends Message {
    public enum Button {
        LEFT,
        RIGHT,
        MIDDEL
    }
    MessageType messageType;
    Button button;
    public MouseMessage(MessageType messageType, int tick, Button button) {
        super(messageType, tick);
        this.messageType=super.getMessageType();
        this.button=button;
    }

    public Button getButton() {
        return button;
    }

}
