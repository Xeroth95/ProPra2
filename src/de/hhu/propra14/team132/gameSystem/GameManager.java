package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;

import java.util.ArrayList;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {
    public MainFrame mainFrame;
    public ArrayList<Object> Keyboard; //arrayList with all the Objects who want to receive Message of Type KeyboardMessage
    public GameManager() {
        Keyboard=new ArrayList<Object>(5);
    }

    public static void main(String[] args) {
        GameManager gameManager=new GameManager(); //this is the gameManager. It gives itself to all other Objects it creates
         gameManager.start();                       //testMethod to start the GUI

    }
    public void start() {
        MainFrame f=new MainFrame(this);
        f.addToMessageLists(f,this);
    }
    public void sendMessage(Message m) {
        //this Methode gets all the Messages other Objects send. It Interprets the MessageType and reads out in an ArrayList
        //which Objects want messages of this type
        //It then calls all the receiveMessage-Methods of the Objects
    }
    public void receiveMessage(Message m) {
      //  MessageType type=m.getType();
        //do a switch-case with the different Messagetype
        //cast to the right typ
        //get all the things this class needs
        //do what the message wants
    }

    public ArrayList<Object> getKeyboard() {
        return Keyboard;
    }

    public void addToKeyboard(Object o) {
        Keyboard.add(o);
    }
}
