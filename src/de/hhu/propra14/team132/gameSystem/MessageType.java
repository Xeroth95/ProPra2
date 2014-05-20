package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 06.05.14.
 */
public enum MessageType {
    //this is the enum for MessageType. Here we will list all the different MessageTypes
    //At the Moment I have to add them manually to the switch-Case and I manually generate the ArrayList
    //After I added the type in GameManager, Messages of this type can be send and received
    KEYBOARD,
    MOUSE,
    STOP,
    GO,
    //not added at the Moment:
    OPTION,
    CHAT,
    NETWORK,

    DRAW,
    WHATEVER,
    B,
    C,
    D
}
