package de.hhu.propra14.team132.gameSystem;

import java.io.Serializable;

/**
 * Created by isabel on 06.05.14.
 */
public abstract class Message implements Serializable {
    //with Message-Object one can exchange messages between the Objects
    private final MessageType messageType;
    private int sentAtTick;
    private int sender;
    public Message(MessageType type, Communicable sender) {
        this.messageType=type;
        if (sender == null)
        	this.sender = -1;
        else
        	this.sender = sender.hashCode();
    }

    public MessageType getMessageType() {
        return messageType;
    }
    
    public void setSender(Communicable sender) {
    	this.sender = sender.hashCode();
    }

    public int getSentAtTick() {
        return sentAtTick;
    }

    public void setSentAtTick(int sentAtTick) {
        this.sentAtTick = sentAtTick;
    }
    
    public int getSender() {
    	return this.sender;
    }
}
