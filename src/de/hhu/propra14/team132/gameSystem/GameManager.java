package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Obstacle;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    private boolean stopped; //is there to pause the thread; true, if game if paused and false, if game continues
    private boolean beforeStart;  //is for the loop before the gamestart
    //declares the necessary objects
    public Map gameMap;
    public Terrain terrain;
    public Obstacle[] obstacles;
    public Worm worm1_1;
    public Worm worm1_2;
    public Worm worm1_3;
    public Worm worm1_4;
    public Worm worm2_1;
    public Worm worm2_2;
    public Worm worm2_3;
    public Worm worm2_4;
    public MainFrame mainFrame;
    HashMap<MessageType,ArrayList<Communicable>> hashMap; //arrayList with all the Objects who want to receive Message
    public static int ticksPerSecond;
    public static long lengthOfTickInNanoSeconds;
    public static int currentTick;

    public static final long LENGTH_OF_A_SECOND_IN_NANASECONDS =1000000000L;
    int Round;
    public GameManager() throws IOException {
        beforeStart=true;
        currentTick=0;
        ticksPerSecond=60; //todo:where should this be declared?
        lengthOfTickInNanoSeconds= LENGTH_OF_A_SECOND_IN_NANASECONDS /ticksPerSecond;
        hashMap =new HashMap<MessageType, ArrayList<Communicable>>();
        int playerCount=2;//TODO: Was soll ich denn hier machen?
        gameMap=new Map(this,playerCount);
        //generate the ArrayList for all the MessagesTypes:
        //hashMap.put(MessageType.KEYBOARD,new ArrayList<Communicable>());
        //hashMap.put(MessageType.MOUSE,new ArrayList<Communicable>());
        for(MessageType t: MessageType.values()){
            hashMap.put(t, new ArrayList<Communicable>());
        }
        //creates Map

        //create vectors, that form the terrain
        Vector2D[] verticesTerrain=new Vector2D[10];
        verticesTerrain[0]=new Vector2D(0, 550);
        verticesTerrain[1]=new Vector2D(100, 450);
        verticesTerrain[2]=new Vector2D(250, 400);
        verticesTerrain[3]=new Vector2D(375, 370);
        verticesTerrain[4]=new Vector2D(500, 480);
        verticesTerrain[5]=new Vector2D(615, 300);
        verticesTerrain[6]=new Vector2D(690, 280);
        verticesTerrain[7]=new Vector2D(820, 340);
        verticesTerrain[8]=new Vector2D(900, 400);
        verticesTerrain[9]=new Vector2D(1000, 550);
        //create terrain
        terrain=new Terrain(new ConvexCollisionShape(verticesTerrain), gameMap);
        //create some obstacles
        obstacles=new Obstacle[3];
        Vector2D[] verticesObstacle0=new Vector2D[4];
        verticesObstacle0[0]=new Vector2D(600,285);
        verticesObstacle0[1]=new Vector2D(630,280);
        verticesObstacle0[2]=new Vector2D(630,330);
        verticesObstacle0[3]=new Vector2D(600,330);
        obstacles[0]=new Obstacle(new ConvexCollisionShape(verticesObstacle0),gameMap,false);
        Vector2D[] verticesObstacle1=new Vector2D[4];
        verticesObstacle1[0]=new Vector2D(300,405);
        verticesObstacle1[1]=new Vector2D(330,405);
        verticesObstacle1[2]=new Vector2D(330,355);
        verticesObstacle1[3]=new Vector2D(300,350);
        obstacles[1]=new Obstacle(new ConvexCollisionShape(verticesObstacle1),gameMap,false);
        Vector2D[] verticesObstacle2=new Vector2D[4];
        verticesObstacle2[0]=new Vector2D(450,500);
        verticesObstacle2[1]=new Vector2D(510,500);
        verticesObstacle2[2]=new Vector2D(510,310);
        verticesObstacle2[3]=new Vector2D(450,290);
        obstacles[2]=new Obstacle(new ConvexCollisionShape(verticesObstacle2),gameMap,false);
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
        stopped =false;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = "+System.getProperty("user.dir"));
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        //gameManager.beforeStart();
        gameManager.save(1);
        gameManager.start();  //starts the game
        //gameManager.save();

    }
    //Idea: gameManager calls this method before the start, and when the new game starts, the method starts() will be called bei the GUI
    public void save(int x){
        try {
            File file = new File("resources/SaveGame"+x+".ser");
            FileOutputStream fileOut=new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameMap);
        } catch(Exception e) {

        }
    }
    public Map load(int x) {
        try {
            File file = new File("resources/SaveGame"+x+".ser");
            FileInputStream fileIn=new FileInputStream(file);
            ObjectInputStream in=new ObjectInputStream(fileIn);
            return (Map)in.readObject();
        } catch (Exception e) {
            return null;
        }
    }
    public void start() { //todo: start, when the game starts, not before. GUI should call this!
        try {
            while (beforeStart) {
                long t1 = System.nanoTime();   //time before
                //Update everything;
                mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();
                //System.out.println("erste Schleife, Tick: "+currentTick);
                long t2 = System.nanoTime();  //time after
                if (t2 - t1 < lengthOfTickInNanoSeconds) {
                    double diff = lengthOfTickInNanoSeconds - (t2 - t1); //diff from how long the updates take to length of tick
                    Thread.sleep(((int) (diff / 1000000)));   //
                }
                currentTick++;
            }
        } catch (Exception e) {

        }
        currentTick=0;
        System.out.println("switched loops");
        //when the game starts, the gui sets the beforeStart to false;
        try {
            while (true) {
                if(!stopped) {
                    long t1 = System.nanoTime();   //time before
                    //Update everything;
                    mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();
                    gameMap.nextTick();
                    //System.out.println("zweite Schleife, Tick: "+currentTick);
                    long t2 = System.nanoTime();  //time after
                    if (t2 - t1 < lengthOfTickInNanoSeconds) {
                        double diff = lengthOfTickInNanoSeconds - (t2 - t1); //diff from how long the updates take to length of tick
                        Thread.sleep(((int) (diff / 1000000)));   //
                    }
                    currentTick++;
                } else {
                    Thread.sleep(lengthOfTickInNanoSeconds/1000000);
                }
            }
         } catch (Exception e) {
                   e.printStackTrace();
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
        for(Communicable o : hashMap.get(messageType)) {
            o.receiveMessage(m);
        }
    }
    public void receiveMessage(Message m) {
     //this is the receive-Method from Interface Communicable
        //Decide what to do with Message.
        Message message=m;
        MessageType messageType=message.getMessageType();

    }
    public void register(Communicable o, ArrayList<MessageType> type) {
        //add Communicable o to all the ArrayLists in type
        for(MessageType t: type) {
            if(!checkIfAlreadyRegistered(o,t)) {
                hashMap.get(t).add(o);
            }
        }
    }
    public void register(Communicable o, MessageType type) {
        if(!checkIfAlreadyRegistered(o,type)) {
            hashMap.get(type).add(o);
        }
        //add Communicable to ArrayList assoziatet with type
    }
    public boolean checkIfAlreadyRegistered(Communicable o,MessageType type) {
        return (hashMap.get(type).contains(o));
    }

    //regulary getter and setter

    public boolean isBeforeStart() {
        return beforeStart;
    }

    public void setBeforeStart(boolean beforeStart) {
        this.beforeStart = beforeStart;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}





