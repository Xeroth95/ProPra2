package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.KeyboardMessage;
import de.hhu.propra14.team132.gameSystem.MessageType;

import javax.swing.*;

/**
 * Created by fabian on 02.05.14.
 */
public class MainFrame extends JFrame{
    //this class creates a frame which contains the MainPanel

    MainPanel mainPanel;
    GameManager gameManager;

    public MainFrame(GameManager gameManager)
    {
        this.gameManager=gameManager;
        mainPanel=new MainPanel(gameManager);

        this.setTitle("ProPra14 - Team 132");
        this.setSize(640, 480);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);
        //Test MessageSending
        gameManager.sendMessage(new KeyboardMessage(MessageType.KEYBOARD,"w"));
    }
    public void addToMessageLists(MainFrame mainFrame, GameManager gameManager) {
        gameManager.addToKeyboard(this);
    }
}
