package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class StopMessage extends Message {
    MessageType messageType;
    public StopMessage(MessageType messageType, int tick) {
        super(messageType,tick);
        this.messageType=super.getMessageType();
    }

}
