package de.hhu.propra14.team132.GUI;

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

    public MainGamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        weaponsPanel=new WeaponsPanel(mainPanel);
        gamePanel=new GamePanel(mainPanel, weaponsPanel);

        this.setLayout(new BorderLayout());

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);
    }
}
