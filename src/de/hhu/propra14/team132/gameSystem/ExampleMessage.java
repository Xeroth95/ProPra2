package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
public class ExampleMessage extends Message {
    MessageType messageType;
    public ExampleMessage(MessageType messageType, String key) {
        super(messageType);
        this.messageType=super.getType();
    }
    //add all the parameters and methods you need. Regarding the names:
    //Choose Name of this class as <Messagetype>Message, where <Messagetype> is the Typ of your Message in Kleinbuchstaben

}
