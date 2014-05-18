package de.hhu.propra14.team132.gameSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.rule.PassiveRule;
import de.hhu.propra14.team132.gameMechanics.rule.Rule;
import de.hhu.propra14.team132.gameMechanics.rule.RuntimeRule;
import de.hhu.propra14.team132.gameMechanics.rule.StartUpRule;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.physics.Effect;

import java.io.*;
import java.util.*;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    private boolean stopped; //is there to pause the thread; true, if game if paused and false, if game continues
    private boolean beforeStart;  //is for the loop before the gamestart
    private boolean waiting;
    private int waitUntil; //with this yu can stop the GameManager so that it wait till a specific tic
    //declares the necessary objects
    transient public Map gameMap;


    transient public MainFrame mainFrame;
    transient public File introSoundFile;
    HashMap<MessageType,ArrayList<Communicable>> hashMap; //arrayList with all the Objects who want to receive Message
    public static int ticksPerSecond;
    public static long lengthOfTickInNanoSeconds;
    public static Integer currentTick;

    int playerCount;
    Queue<Message> MessageList;

    public static final long LENGTH_OF_A_SECOND_IN_NANOSECONDS =1000000000L;
    int Round;
    public GameManager() throws IOException {
        beforeStart=true;
        waiting=true;
        stopped =false;
        waitTillTick(100);
        currentTick=0;
        ticksPerSecond=240; //todo:where should this be declared?
        lengthOfTickInNanoSeconds= LENGTH_OF_A_SECOND_IN_NANOSECONDS /ticksPerSecond;
        hashMap =new HashMap<MessageType, ArrayList<Communicable>>();
        playerCount=2;//TODO: Was soll ich denn hier machen?
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



        MessageList=new LinkedList<Message>();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = "+System.getProperty("user.dir"));
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        //gameManager.beforeStart();
        gameManager.start();  //starts the game
    }
    public void save(String path){
        try {
        	GsonBuilder gB=new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();
        	gB.registerTypeAdapter(GameObject.class, new JsonAdapter<GameObject>());
        	gB.registerTypeAdapter(Effect.class, new JsonAdapter<Effect>());
        	gB.registerTypeAdapter(Rule.class, new JsonAdapter<Rule>());
        	gB.registerTypeAdapter(StartUpRule.class, new JsonAdapter<StartUpRule>());
        	gB.registerTypeAdapter(RuntimeRule.class, new JsonAdapter<RuntimeRule>());
        	gB.registerTypeAdapter(PassiveRule.class, new JsonAdapter<PassiveRule>());
            Gson gson1=gB.create();
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
    public void restart() { //is there to start a new Game
        currentTick=0;
        this.setBeforeStart(false);
        this.setStopped(false);
        this.gameMap=new Map(this,playerCount);
        this.mainFrame.mainPanel.mainGamePanel.gamePanel.refresh();
    }
    public void load(String path) {
        try {
            FileInputStream input = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            GsonBuilder gB=new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();
            gB.registerTypeAdapter(GameObject.class, new JsonAdapter<GameObject>());
        	gB.registerTypeAdapter(Effect.class, new JsonAdapter<Effect>());
        	gB.registerTypeAdapter(Rule.class, new JsonAdapter<Rule>());
        	gB.registerTypeAdapter(StartUpRule.class, new JsonAdapter<StartUpRule>());
        	gB.registerTypeAdapter(RuntimeRule.class, new JsonAdapter<RuntimeRule>());
        	gB.registerTypeAdapter(PassiveRule.class, new JsonAdapter<PassiveRule>());
            Gson gson=gB.create();

            Map mapNew=gson.fromJson(reader,Map.class);
            mapNew.setManager(this);
            this.gameMap=mapNew;
            this.gameMap.setUpAfterLoading();
            this.mainFrame.mainPanel.mainGamePanel.gamePanel.refresh();
            this.setBeforeStart(false);
            currentTick=this.gameMap.getCurrentTick();//@ISA: this is why you need currentTick in Map
            /** Map wird wieder in Datei geschrieben, wegen Testzwecke
            Map map1=gson.fromJson(reader,Map.class);
            String jsonString = gson.toJson(map1);
            FileWriter fileWriter=new FileWriter(path+"x");
            fileWriter.write(jsonString);
            fileWriter.close();
             **/

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
                    if(waiting) {
                        if(waitUntil==currentTick) {
                            waiting = false;
                        }
                    } else {
                        long t1 = System.nanoTime();
                        //Update everything;
                        mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();
                        gameMap.nextTick();
                        this.sendMessagesOfQueue(this.getCurrentTick());
                        long t2 = System.nanoTime();  //time after
                        if (t2 - t1 < lengthOfTickInNanoSeconds) {
                            double diff = lengthOfTickInNanoSeconds - (t2 - t1); //diff from how long the updates take to length of tick
                            Thread.sleep(((int) (diff / 1000000)));   //
                        }
                    }
                    currentTick++;  //is increased when GameManager is waiting, but not if it is stopped;
                  //  System.out.println("tick "+currentTick);
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
    //this method is for the Idea, that we stop the other systems.
    // When Network sends them a Message they are stored here. The System then proceed and get the messages at the right tick
    public void addMessageToMessageQueue (Message m) {  //should be used by network
        MessageList.add(m);
    }
    public void sendMessagesOfQueue(int currentTick) {
        while ((!MessageList.isEmpty())&&MessageList.element().getSentAtTick() == currentTick) {
            this.sendMessage(MessageList.remove());
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
    public void waitTillTick(int x) {
        this.waitUntil=x;
    }
    //regulary getter and setter


    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

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





