package de.hhu.propra14.team132.gameSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    transient public Map gameMap;
    transient public Terrain terrain;

    transient public MainFrame mainFrame;
    transient public File introSoundFile;
    HashMap<MessageType,ArrayList<Communicable>> hashMap; //arrayList with all the Objects who want to receive Message
    public static int ticksPerSecond;
    public static long lengthOfTickInNanoSeconds;
    public static int currentTick;

    public static final long LENGTH_OF_A_SECOND_IN_NANOSECONDS =1000000000L;
    int Round;
    public GameManager() throws IOException {
        beforeStart=true;
        currentTick=0;
        ticksPerSecond=240; //todo:where should this be declared?
        lengthOfTickInNanoSeconds= LENGTH_OF_A_SECOND_IN_NANOSECONDS /ticksPerSecond;
        hashMap =new HashMap<MessageType, ArrayList<Communicable>>();
        int playerCount=2;//TODO: Was soll ich denn hier machen?
        gameMap=new Map(this,playerCount);
        //generate the ArrayList for all the MessagesTypes:
        //hashMap.put(MessageType.KEYBOARD,new ArrayList<Communicable>());
        //hashMap.put(MessageType.MOUSE,new ArrayList<Communicable>());
        for(MessageType t: MessageType.values()){
            hashMap.put(t, new ArrayList<Communicable>());
        }
        //create introsound
        introSoundFile=new File("res/audio/intro.wav");
        //create MainFrame
        mainFrame=new MainFrame(this);
        stopped =false;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = "+System.getProperty("user.dir"));
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        //gameManager.beforeStart();


        gameManager.start();  //starts the game


    }
    public void save(String path){
        try {

            Gson gson1=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Worm.class, new WormInstanceCreator()).create();
            //Gson gson= new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC, Modifier.FINAL).create();
            //Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();

            String jsonString = gson1.toJson(this.gameMap);
            FileWriter fileWriter=new FileWriter(path);
            fileWriter.write(jsonString);
            fileWriter.close();
            /**/
        //this.saveWorms(path);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void load(String path) {
        try {
              /**/
            FileInputStream input = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Worm.class, new WormInstanceCreator()).create();
            this.gameMap=gson.fromJson(reader,Map.class);
               /**/
        //this.loadWorm(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start() {
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
        this.mainFrame.mainPanel.soundEngine.play(introSoundFile);
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





