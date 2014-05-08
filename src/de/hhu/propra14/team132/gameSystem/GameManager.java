package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    public MainFrame mainFrame;                 //arrayList with all the Objects who want to receive Message
    HashMap<MessageType,ArrayList<Communicatable>> map;
    int ticksPerSecond;
    long lengthOfTickInNanoSeconds;
    int currentTick;
    public static final long LENGTH_OF_A_SECOND_IN_NANASECONDS =1000000000L;
    public GameManager(int ticksPerSecond) {
        currentTick=0;
        this.ticksPerSecond=ticksPerSecond;
        this.lengthOfTickInNanoSeconds= LENGTH_OF_A_SECOND_IN_NANASECONDS /ticksPerSecond;
        map=new HashMap<MessageType, ArrayList<Communicatable>>();
        //generate the ArrayList for all the MessagesTypes:
        //map.put(MessageType.KEYBOARD,new ArrayList<Communicatable>());
        //map.put(MessageType.MOUSE,new ArrayList<Communicatable>());
        for(MessageType t: MessageType.values()){
            map.put(t,new ArrayList<Communicatable>());
        }
    }

    public static void main(String[] args) {
        GameManager gameManager=new GameManager(15); //this is the gameManager. It gives itself to all other Objects it creates
        gameManager.start();                       //testMethod to start the GUI

    }
    public void start() {
        MainFrame f=new MainFrame();
        this.update();
    }
    public void update() {
        while(true) {
           try {
               currentTick++;
                long t1 = System.nanoTime();
                //Update everything;
                //System.out.println("currentTick: "+currentTick);
                long t2 = System.nanoTime();
                if (t2 - t1 < lengthOfTickInNanoSeconds) {
                    double diff = lengthOfTickInNanoSeconds - (t2 - t1);
                    Thread.sleep(((int) (diff / 1000000)));
                }
            } catch(Exception e) {
                 System.out.println("exception");
            }
        }
    }
    public void sendMessage(Message m) {
        //this Methode gets all the Messages other Objects send. It Interprets the MessageType and reads out in an ArrayList
        //which Objects want messages of this type
        //It then calls all the receiveMessage-Methods of the Objects
        Message message=m;
        MessageType messageType=message.getMessageType();  //reads the MessageType
        //makes a Decision what to do with the message:
        switch(messageType) {
            case KEYBOARD:
                helpSend(MessageType.KEYBOARD, m);
            case MOUSE:
                helpSend(MessageType.MOUSE, m);
        }


    }
    public void helpSend(MessageType messageType, Message m) {
        for(Communicatable o : map.get(messageType)) {
            o.receiveMessage(m);
        }
    }
    public void receiveMessage(Message m) {
     //this is the receive-Method from Interface Communicatable
        //Decide what to do with Message.
        Message message=m;
        MessageType messageType=message.getMessageType();

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



