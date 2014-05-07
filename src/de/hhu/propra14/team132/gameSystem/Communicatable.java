package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */

/**
 * this Interface must be implemented by all classes, which want to receive and send Messages. It is neccesary to have a
 * Constructor with GameManager
 */
public interface Communicatable {

    public void receiveMessage(Message m);  //with this Method the Objects can receive Messages. It should contain
                                            //something to intepret the Message, that means it should get the type,
                                            //cast it to the right Object and get the things it needs to do what it should to

    /**Examplecode for receiveMessage
     *  MessageType messageType=m.getType();
    Message message;
    if(messageType==messageType.KEYBOARD) {
        message=(KeyboardMessage)m;
        System.out.println("this is a Message of Type KEYBOARD and the key is "+((KeyboardMessage)message).getKey());
    }
     **/
    public void addToMessageLists(GameManager gameManager);
    //should call all the addTo<NameOfMessageTypeItwantsToReceive> of gameManager. As a parameter it should commit itself
    //Example gameManager.addToKeyboard(this);
    //addToMessageLists should be called for the first time in the constructor
}
