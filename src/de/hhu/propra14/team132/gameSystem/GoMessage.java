package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class GoMessage extends Message {
    MessageType messageType;
    public GoMessage(MessageType messageType, int tick) {
        super(messageType,tick);
        this.messageType=super.getMessageType();
    }

}
