package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Message {
    //with Message-Object one can exchange messages between the Objects
    MessageType type;
    public Message(MessageType type) {
        this.type=type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
