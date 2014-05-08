package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameObjects.Worm;

import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    public MainFrame mainFrame;                 //arrayList with all the Objects who want to receive Message
    public ArrayList<Communicatable> keyboard; // of Type KeyboardMessage
    public ArrayList<Communicatable> mouse;     // of Type MouseMessage
    Queue<Message> messageQueue;                //contains the Messages ready to send
    Queue<Communicatable> objectsQueue;         //contains the Objects to which the Messages from M.Queue should be send
    public GameManager() {
        //create all the ArrayLists
        keyboard=new ArrayList<Communicatable>(5);
        mouse=new ArrayList<Communicatable>(5);


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
        //makes a Decision what to do with the message
        //for now just an Example with KEYBOARD
        switch(messageType) { //here the different Messages are send to the Objects
            case KEYBOARD:
                for(int i=0; i<keyboard.size();i++) { //Keyboard is a ArrayList
                    keyboard.get(i).receiveMessage((KeyboardMessage)m);//Message wird an Objects gesendet
                    //! Problem: nächste Message wird erst gesendet, sobald alle Folgeanweisungen der ersten Message abgehandelt sind
                    // --> Queue? Messagequeue and ObjectQueue,
                    System.out.println("Message wurde weitergeleitet"); //Just for test.
                }
                break;
            case MOUSE:
                MouseMessage.Button button=((MouseMessage) m).getButton();
                for(int i=0; i<mouse.size();i++) {
                    mouse.get(i).receiveMessage((MouseMessage) m); //Message wird weiter an Object gesendet.
                    //Soll das schon getypcast werden?
                    System.out.println("Message wurde weitergeleitet"); //Just for test.
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

    public ArrayList<Communicatable> getKeyboard() {
        return keyboard;
    }

    public void addToKeyboard(Communicatable o) {
        keyboard.add(o);
    }

    public ArrayList<Communicatable> getMouse() {
        return mouse;
    }

    public void setMouse(ArrayList<Communicatable> mouse) {
        mouse = mouse;
    }
    public void addToMouse(Communicatable o) {
        mouse.add(o);
    }
}





