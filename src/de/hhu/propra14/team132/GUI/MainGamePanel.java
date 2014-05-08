package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by fabian on 03.05.14.
 */
public class MainGamePanel extends JPanel{
    MainPanel mainPanel;
    GamePanel gamePanel;
    WeaponsPanel weaponsPanel;
    JScrollPane scrollPane;

    public MainGamePanel(MainFrame mainFrame, MainPanel mainPanel, GameManager gameManager) {
        //this panel contains all in-game related panels
        //for now that would be scrollPane(GamePanel) and WeaponsPanel
        this.mainPanel=mainPanel;

        weaponsPanel=new WeaponsPanel(mainPanel);
        gamePanel=new GamePanel(mainFrame, mainPanel, this, weaponsPanel, gameManager);
        scrollPane=new JScrollPane(gamePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);
    }
}
