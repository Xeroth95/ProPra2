package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 06.05.14.
 */
public class StartGamePanel extends JPanel {
    //this panel lets the player choose wether he wants to start a local or a network game

    MainPanel mainPanel;

    JButton button_local_game;
    JButton button_network_game;

    public StartGamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(2,1,10,10));

        button_local_game=new JButton("Start local game");
        button_local_game.addActionListener(new LocalGameListener());
        button_network_game=new JButton("Start network game");
        button_network_game.addActionListener(new NetworkGameListener());

        this.add(button_local_game);
        this.add(button_network_game);
    }

    class LocalGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.mainPanel.showPanel("2");
        }
    }

    class NetworkGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.mainPanel.showPanel("9");
        }
    }
}
