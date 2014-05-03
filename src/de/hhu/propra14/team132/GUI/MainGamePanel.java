package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fabian on 03.05.14.
 */
public class MainGamePanel extends JPanel{
    MainPanel mainPanel;
    GamePanel gamePanel;
    WeaponsPanel weaponsPanel;

    public MainGamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;
        gamePanel=new GamePanel(mainPanel);
        weaponsPanel=new WeaponsPanel(mainPanel);

        this.setLayout(new BorderLayout());

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);
    }
}
