package de.hhu.propra14.team132.gameSystem;

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
    public MouseMessage(int tick, Button button) {
        super(MessageType.MOUSE, tick);
        this.button=button;
    }

    public Button getButton() {
        return button;
    }

}
