package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;

/**
 * Created by fabian on 02.05.14.
 */
public class MainFrame extends JFrame{
    //this class creates a frame which contains the MainPanel

    MainPanel mainPanel;

    public MainFrame(GameManager gameManager)
    {
        mainPanel=new MainPanel();

        this.setTitle("ProPra14 - Team 132");
        this.setSize(640, 480);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
