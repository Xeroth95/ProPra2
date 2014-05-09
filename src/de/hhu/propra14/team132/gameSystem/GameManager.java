package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    //declares the necessary objects
    public Map gameMap;
    public Terrain terrain;
    public Worm worm1_1;
    public Worm worm1_2;
    public Worm worm1_3;
    public Worm worm1_4;
    public Worm worm2_1;
    public Worm worm2_2;
    public Worm worm2_3;
    public Worm worm2_4;
    public MainFrame mainFrame;
    HashMap<MessageType,ArrayList<Communicatable>> hashMap; //arrayList with all the Objects who want to receive Message
    int ticksPerSecond;
    long lengthOfTickInNanoSeconds;
    int currentTick;
    public static final long LENGTH_OF_A_SECOND_IN_NANASECONDS =1000000000L;
    public GameManager() {
        currentTick=0;
        this.ticksPerSecond=60; //todo:where should this be declared?
        this.lengthOfTickInNanoSeconds= LENGTH_OF_A_SECOND_IN_NANASECONDS /ticksPerSecond;
        hashMap =new HashMap<MessageType, ArrayList<Communicatable>>();
        gameMap=new Map(this);
        //generate the ArrayList for all the MessagesTypes:
        //hashMap.put(MessageType.KEYBOARD,new ArrayList<Communicatable>());
        //hashMap.put(MessageType.MOUSE,new ArrayList<Communicatable>());
        for(MessageType t: MessageType.values()){
            hashMap.put(t, new ArrayList<Communicatable>());
        }
    }

    public static void main(String[] args) {
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        gameManager.start();  //starts the game

    }
    public void start() {
        //creates Map

        //create vectors, that form the terrain
        Vector2D[] vertices=new Vector2D[10];
        vertices[0]=new Vector2D(0, 550);
        vertices[1]=new Vector2D(100, 450);
        vertices[2]=new Vector2D(250, 400);
        vertices[3]=new Vector2D(375, 370);
        vertices[4]=new Vector2D(500, 480);
        vertices[5]=new Vector2D(615, 300);
        vertices[6]=new Vector2D(690, 280);
        vertices[7]=new Vector2D(820, 340);
        vertices[8]=new Vector2D(900, 400);
        vertices[9]=new Vector2D(1000, 550);
        //create terrain
        terrain=new Terrain(new ConvexCollisionShape(vertices),0, gameMap);
        //create worms for team1
        worm1_1=new Worm(1, gameMap, "Worm1_1");
        worm1_2=new Worm(1, gameMap, "Worm1_2");
        worm1_3=new Worm(1, gameMap, "Worm1_3");
        worm1_4=new Worm(1, gameMap, "Worm1_4");
        //create worms for team2
        worm2_1=new Worm(2, gameMap, "Worm2_1");
        worm2_2=new Worm(2, gameMap, "Worm2_2");
        worm2_3=new Worm(2, gameMap, "Worm2_3");
        worm2_4=new Worm(2, gameMap, "Worm2_4");
        //create MainFrame
        mainFrame=new MainFrame(this);
        this.update();
    }
    public void update() {
        try {
            while (true) {   //todo: must stop if game is paused
                long t1 = System.nanoTime();
                //Update everything;
                mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();

                //System.out.println("currentTick: "+currentTick);
                long t2 = System.nanoTime();
                if (t2 - t1 < lengthOfTickInNanoSeconds) {
                    double diff = lengthOfTickInNanoSeconds - (t2 - t1);
                    Thread.sleep(((int) (diff / 1000000)));
                }
            }
         } catch (Exception e) {
                   e.printStackTrace();
         }
         currentTick++;
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
        for(Communicatable o : hashMap.get(messageType)) {
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
            hashMap.get(t).add(o);
        }
    }
    public void register(Communicatable o, MessageType type) {
        hashMap.get(type).add(o);
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



