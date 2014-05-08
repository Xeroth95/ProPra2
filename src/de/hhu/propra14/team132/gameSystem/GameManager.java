package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameObjects.Worm;

import java.lang.reflect.Array;
import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    public MainFrame mainFrame;                 //arrayList with all the Objects who want to receive Message
    HashMap<MessageType,ArrayList<Communicatable>> map;
    public GameManager() {

    }

    public static void main(String[] args) {
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        gameManager.start();                       //testMethod to start the GUI

    }
    public void start() {
        MainFrame f=new MainFrame();
        this.sendMessage(new MouseMessage(MessageType.MOUSE, MouseMessage.Button.LEFT));
    }
    public void sendMessage(Message m) {
        //this Methode gets all the Messages other Objects send. It Interprets the MessageType and reads out in an ArrayList
        //which Objects want messages of this type
        //It then calls all the receiveMessage-Methods of the Objects
        Message message=m;
        MessageType messageType=message.getMessageType();  //reads the MessageType
        //makes a Decision what to do with the message:s
        switch(messageType) { //here the different Messages are send to the Objects
            case KEYBOARD:


            case MOUSE:

                }

        }

    }
    public void receiveMessage(Message m) {
      //  MessageType type=m.getType();
        //do a switch-case with the different Messagetype
        //cast to the right typ
        //get all the things this class needs
        //do what the message wants
        MessageType messageType=m.getMessageType();
        Message message;
        if(messageType==messageType.KEYBOARD) {
            message = (KeyboardMessage) m;
        }

    }
    public void register(Communicatable o, ArrayList<MessageType> type) {
        //add Communicatable o to all the ArrayLists in type
        for(MessageType t: type) {
            map.get(t).add(o);
        }
    }
    public void register(Communicatable o, MessageType type) {
        map.get(type).add(o);
        //add Communicatable to ArrayList assoziatet with type
    }

}

/*
Problem/Idee: Durch das Abarbeiten der ArrayLists in einer Schleife werden nach jeder Gesendeten message erst die daraus
resultierenden Anweisung abgearbeitet. Dies bewirkt, dass die receiveMessage der selben Stufe erst spät gesendet werden oder
wir in einer Endlosschleife landen. Eine Idee wäre, wenn eine Message gesendet werden soll, sie stattdessen in eine Queue einzufügen
Neue resultierende Messages werden dann also am Ende eingefügt, der GameManager fängt nun an, vom Anfang aus die Queue zu bearbeiten
Problem: Es muss die Message und das Objekk, dass sie erhalten soll, gespeichert werden. WIe soll das in einer Queue gehen?
--> Eine MessageQueue, eine Object-Queue die zusammen gepopt und gepusht werden.
 */



