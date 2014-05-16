package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class ExampleMessage extends Message {
    MessageType messageType;
    public ExampleMessage(MessageType messageType, int tick) {
        super(messageType,tick);
        this.messageType=super.getMessageType();
    }

}
