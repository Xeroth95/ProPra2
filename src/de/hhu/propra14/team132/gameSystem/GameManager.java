package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.GUI.MainFrame;
import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;

/**
 * Created by isabel on 06.05.14.
 */
public class GameManager {

    public GameManager() {

    }

    public static void main(String[] args) {
        MainFrame frm=new MainFrame();
        GameManager G=new GameManager();
        G.receiveMessage(new Message(MessageType.BLA));

        //die Befehle der GUI abwarten
        //Spiel starten
        //Karte erstellen
        //Würmer drauf setzen
        //
    }
    public void receiveMessage(Message m) {
        //the GameManager receives the Message, inteprets the Type and decides what to do with it
        MessageType type=m.getType();
        switch (type) {
            case BLA:
                System.out.println("Das hat funktioniert! Der MessageType ist BLA");
                break;
        }
    }

}
