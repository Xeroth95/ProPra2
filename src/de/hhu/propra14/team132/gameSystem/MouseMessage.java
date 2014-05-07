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
    public MouseMessage(MessageType messageType, Button button) {
        super(messageType);
        this.messageType=super.getMessageType();
        this.button=button;
    }

    public Button getButton() {
        return button;
    }
//add all the parameters and methods you need. Regarding the names:
    //Choose Name of this class as <Messagetype>Message, where <Messagetype> is the Typ of your Message in Kleinbuchstaben

}
