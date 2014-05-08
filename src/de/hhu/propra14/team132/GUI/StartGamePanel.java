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
    JButton button_network_game_host;
    JButton button_network_game_client;
    JButton button_go_back;

    public StartGamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(4,1,10,10));

        button_local_game=new JButton("Start local game");
        button_local_game.addActionListener(new LocalGameListener());
        button_network_game_host=new JButton("Start network game as host");
        button_network_game_host.addActionListener(new NetworkGameListener());
        button_network_game_client=new JButton("Start network game as client");
        button_network_game_client.addActionListener(new NetworkGameListener());
        button_go_back=new JButton("Go back to Main Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(button_local_game);
        this.add(button_network_game_host);
        this.add(button_network_game_client);
        this.add(button_go_back);
    }

    class LocalGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.mainPanel.showPanel("2");
        }
    }

    class NetworkGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.mainPanel.showPanel("9");
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            StartGamePanel.this.mainPanel.showPanel("1");
        }
    }
}
