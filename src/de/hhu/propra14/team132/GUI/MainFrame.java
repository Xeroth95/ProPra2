package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by fabian on 02.05.14.
 */
public class MainFrame extends JFrame{
    //this class creates a frame which contains the MainPanel

    public MainPanel mainPanel;

    public MainFrame(GameManager gameManager) throws IOException {
        mainPanel=new MainPanel(this, gameManager);

        this.setTitle("ProPra14 - Team 132");
        this.setSize(640, 480);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
