package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Message {
    //with Message-Object one can exchange messages between the Objects
    MessageType messageType;
    public Message(MessageType type) {
        this.messageType=type;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType type) {
        this.messageType = type;
    }
}
