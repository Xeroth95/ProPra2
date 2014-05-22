package de.hhu.propra14.team132.gameSystem;

import java.io.Serializable;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Message implements Serializable {
    //with Message-Object one can exchange messages between the Objects
    MessageType messageType;
    private int sentAtTick;
    public Message(MessageType type) {
        this.messageType=type;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType type) {
        this.messageType = type;
    }

    public int getSentAtTick() {
        return sentAtTick;
    }

    public void setSentAtTick(int sentAtTick) {
        this.sentAtTick = sentAtTick;
    }
}
