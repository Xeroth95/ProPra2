package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class KeyboardMessage extends Message {
    MessageType messageType;
    public String key;
    public KeyboardMessage(MessageType messageType, String key) {
        super(messageType);
        this.messageType=super.getType();
        this.key=key;
    }
}
