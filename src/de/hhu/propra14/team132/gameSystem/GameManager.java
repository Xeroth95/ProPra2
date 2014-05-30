package de.hhu.propra14.team132.gameSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.Network.Client;
import de.hhu.propra14.team132.Network.Server;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.rule.PassiveRule;
import de.hhu.propra14.team132.gameMechanics.rule.Rule;
import de.hhu.propra14.team132.gameMechanics.rule.RuntimeRule;
import de.hhu.propra14.team132.gameMechanics.rule.StartUpRule;
import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;
import de.hhu.propra14.team132.physics.Effect;
import de.hhu.propra14.team132.sound.SoundEngine;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager implements Communicable{
	private static final int GAME_PORT = 3141;
	
	
	
    private boolean stopped; //is there to pause the thread; true, if game if paused and false, if game continues
    private boolean beforeStart;  //is for the loop before the gamestart
    //declares the necessary objects
    transient public Map gameMap;

    double actualTicksPerSecond;
    double possibleTicksPerSecond;

    transient public MainFrame mainFrame;
    
    
    
    
    transient private Server server;
    transient private Client client;
    transient private Thread senderThread;
    
    
    
    HashMap<MessageType,ArrayList<Communicable>> hashMap; //arrayList with all the Objects who want to receive Message
    public static int TICKS_PER_SECOND;
    public static long TICK_LENGTH;
    public static Integer currentTick;

    int playerCount;
    Queue<Message> messageList;

    public static final long SECOND_LENGTH =1000000000L;
    public GameManager() throws IOException {
        beforeStart=true;
        stopped =false;

        currentTick=0;
        TICKS_PER_SECOND=240; //todo:where should this be declared?
        TICK_LENGTH= SECOND_LENGTH / TICKS_PER_SECOND;

        hashMap =new HashMap<MessageType, ArrayList<Communicable>>();
        playerCount=2;//TODO: Was soll ich denn hier machen?
        for(MessageType t: MessageType.values()){
            hashMap.put(t, new ArrayList<Communicable>());
        }
        gameMap=new Map(this);

        //generates Hashmaps:
        
        //create introsound
        SoundEngine.init(new File("res/audio/intro.wav"), new File("res/audio/klick.wav"));
        //create MainFrame
        mainFrame=new MainFrame(this);

        messageList=new ConcurrentLinkedQueue<Message>();
        
        this.initThread();
    }

    private void initThread() {
		senderThread = new Thread(new Sender(messageList, hashMap));
		senderThread.start();
	}
    
    private class Sender implements Runnable {

    	private final Queue<Message> outBuffer;
    	private final HashMap<MessageType,ArrayList<Communicable>> messageTypeList;
    	
    	public Sender(Queue<Message> outBuffer, HashMap<MessageType,ArrayList<Communicable>> messageTypeList) {
    		this.outBuffer = outBuffer;
    		this.messageTypeList = messageTypeList;
    	}
    	
		@Override
		public void run() {
			while (true) {
				synchronized(this.outBuffer) {
					try {
						this.outBuffer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					while (!this.outBuffer.isEmpty()) {
						Message m = this.outBuffer.poll();
						ArrayList<Communicable> receiver = messageTypeList.get(m.getMessageType());
						for (Iterator<Communicable> i = receiver.iterator(); i.hasNext();) {
							Communicable c = i.next();
							System.out.println(c.hashCode() + "<=>" + m.getSender());
							if (c.hashCode() != m.getSender()) { c.receiveMessage(m); System.out.println("SEND!");}
						}
					}
				}
			}
		}
    	
    }

	public static void main(String[] args) throws IOException {
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
        gameManager.register(gameManager,MessageType.STOP);
        gameManager.register(gameManager,MessageType.GO);
        gameManager.beforeStart();  //starts the game
    }
    public void save(String path){
        try {
        	GsonBuilder gB=new GsonBuilder().setPrettyPrinting();
        	gB.registerTypeAdapter(GameObject.class, new JsonAdapter<GameObject>());
        	gB.registerTypeAdapter(Effect.class, new JsonAdapter<Effect>());
        	gB.registerTypeAdapter(Rule.class, new JsonAdapter<Rule>());
        	gB.registerTypeAdapter(StartUpRule.class, new JsonAdapter<StartUpRule>());
        	gB.registerTypeAdapter(RuntimeRule.class, new JsonAdapter<RuntimeRule>());
        	gB.registerTypeAdapter(PassiveRule.class, new JsonAdapter<PassiveRule>());
            gB.registerTypeAdapter(Weapon.class,new JsonAdapter<Weapon>());
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
    public void load(String path) {
        try {
            FileInputStream input = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            GsonBuilder gB=new GsonBuilder().setPrettyPrinting();
            gB.registerTypeAdapter(GameObject.class, new JsonAdapter<GameObject>());
        	gB.registerTypeAdapter(Effect.class, new JsonAdapter<Effect>());
        	gB.registerTypeAdapter(Rule.class, new JsonAdapter<Rule>());
        	gB.registerTypeAdapter(StartUpRule.class, new JsonAdapter<StartUpRule>());
        	gB.registerTypeAdapter(RuntimeRule.class, new JsonAdapter<RuntimeRule>());
        	gB.registerTypeAdapter(PassiveRule.class, new JsonAdapter<PassiveRule>());
            gB.registerTypeAdapter(Weapon.class,new JsonAdapter<Weapon>());
            gB.registerTypeAdapter(Worm.class, new WormDeserializer());
            Gson gson=gB.create();

            Map mapNew=gson.fromJson(reader,Map.class);
            mapNew.setManager(this);
            this.gameMap=mapNew;
            
            
            this.hashMap =new HashMap<MessageType, ArrayList<Communicable>>();
            for(MessageType t: MessageType.values()){
                hashMap.put(t, new ArrayList<Communicable>());
            }
            
            
            this.gameMap.setUpAfterLoading();
            
            
            
            this.mainFrame.mainPanel.mainGamePanel.gamePanel.refresh();
            this.setBeforeStart(false);
            currentTick=this.gameMap.getCurrentTick();//@ISA: this is why you need currentTick in Map
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void restart() { //is there to start a new Game
        currentTick=0;
        this.setBeforeStart(false);
        this.setStopped(false);
        this.gameMap=new Map(this);
        this.mainFrame.mainPanel.mainGamePanel.gamePanel.refresh();
    }
    public void beforeStart() {
        try {
            while (beforeStart) {
                long t1 = System.nanoTime();   //time before
                //Update everything;
                mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();
          //      System.out.println("erste Schleife, Tick: "+currentTick);
                long t2 = System.nanoTime();  //time after
                if (t2 - t1 < TICK_LENGTH) {
                    double diff = TICK_LENGTH - (t2 - t1); //diff from how long the updates take to length of tick
                    Thread.sleep(((int) (diff / 1000000)));   //
                }
               currentTick++;
            }
        } catch (Exception e) {

        }
        currentTick=0;
        System.out.println("switched loops");
        this.start();
    }
    public void start() {
        SoundEngine.playIntro(mainFrame.mainPanel.options.getFxVolume());
        currentTick=gameMap.getCurrentTick();
        try {
            while (true) {
                if (!stopped) {
                    long t1 = System.nanoTime();
                    //Update everything;
                    //sendMessagesOfQueue(currentTick);

                    gameMap.nextTick();
                    mainFrame.mainPanel.mainGamePanel.gamePanel.nextTick();
                    //this.sendMessagesOfQueue(this.getCurrentTick());
                    long t2 = System.nanoTime();  //time after
                    double deltaT=(double)(t2-t1);
                    possibleTicksPerSecond = (SECOND_LENGTH / deltaT);
                    if (t2 - t1 < TICK_LENGTH) {
                        double diff = TICK_LENGTH - (t2 - t1); //diff from how long the updates take to length of tick
                        Thread.sleep(((int) (diff / 1000000)));
                    }
                    t2 = System.nanoTime();
                    deltaT=(double)(t2-t1);
                    actualTicksPerSecond = (TICK_LENGTH / deltaT);
                    currentTick++;  //is increased when GameManager is waiting, but not if it is stopped;
                    //      System.out.println("tick "+currentTick);
                } else {
                    Thread.sleep(TICK_LENGTH / 1000000);
                }
                if((currentTick%(TICKS_PER_SECOND/2))==0) {
                    mainFrame.mainPanel.mainGamePanel.gamePanel.setTickCounts(actualTicksPerSecond, possibleTicksPerSecond);
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
        /*MessageType messageType=m.getMessageType();  //reads the MessageType
        m.setSentAtTick(currentTick+1);
        addMessageToMessageQueue(m);*/
    	synchronized(this.messageList) {
    		this.messageList.add(m);
    		this.messageList.notifyAll();
    	}
    }
    public void helpSend(Message m) {
        for(Communicable o : hashMap.get(m.getMessageType())) {
            o.receiveMessage(m);
        }
    }
    /*public void sendMessagesOfQueue(int currentTick) {
        while ((!MessageList.isEmpty())&&MessageList.element().getSentAtTick() == currentTick) {
        	System.out.println("Sending some Messages!");
        	Message m = MessageList.remove();
            this.helpSend(m);
        }
    }
    //for network-messages
    public void addMessageToMessageQueue (Message m) {  //should be used by network
        MessageList.add(m);
    }*/
    public void receiveMessage(Message m) {
     //this is the receive-Method from Interface Communicable
        //Decide what to do with Message.
        MessageType messageType=m.getMessageType();
        if(messageType==MessageType.STOP) {
            this.setStopped(true);
        }

        if(messageType==MessageType.GO)  {
            this.setStopped(false);
        }


    }
    public void register(Communicable o, ArrayList<MessageType> type) {
        //add Communicable o to all the ArrayLists in type
        for(MessageType t: type) {
            if(!checkIfAlreadyRegistered(o,t)) {
                hashMap.get(t).add(o);
            }
        }
    }
    public void register(Communicable o, MessageType[] types) {
        //add Communicable o to all the ArrayLists in type
        for(MessageType t: types) {
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

    public int getPlayerCount() {
        return  playerCount;
    }

	public void createServer() {
		try {
			this.server = new Server(this, GAME_PORT);
			this.client = new Client(this, InetAddress.getLocalHost().getHostAddress(), GAME_PORT);
			this.client.register();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public void createClient(String ipAdress) {
		try {
			this.client = new Client(this, ipAdress, GAME_PORT);
			this.client.register();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Client getClient() {
		return this.client;
	}
}





