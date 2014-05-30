package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

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
    JButton button_spectator_mode;
    JButton button_go_back;

    String server_ip;

    public StartGamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(5,1,10,10));

        button_local_game=new JButton("Start local game");
        button_local_game.addActionListener(new LocalGameListener());
        button_network_game_host=new JButton("Start network game as host");
        button_network_game_host.addActionListener(new NetworkGameHostListener());
        button_network_game_client=new JButton("Start network game as client");
        button_network_game_client.addActionListener(new NetworkGameClientListener());
        button_spectator_mode=new JButton("Spectator Mode");
        button_spectator_mode.addActionListener(new SpectatorModeListener());
        button_go_back=new JButton("Go back to Main Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(button_local_game);
        this.add(button_network_game_host);
        this.add(button_network_game_client);
        this.add(button_spectator_mode);
        this.add(button_go_back);
    }

    class LocalGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("12");
        }
    }

    class NetworkGameHostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("13");
        }
    }

    class NetworkGameClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("14");
        }
    }

    class SpectatorModeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SoundEngine.playClick(mainPanel.options.getFxVolume());
            StartGamePanel.this.server_ip=new JOptionPane().showInputDialog("Please enter the IP of the Server you want to connect to");
            /************************************************
             *@Sebastian:                                   *
             *Hier muss du dein Network-Zeugs einfügen.     *
             *server_ip enthält die IP-Adresse des Servers. *
             *um zum GamePanel zu switchen, mach folgendes: *
             *StartGamePanel.this.mainPanel.showPanel("2"); *
             ************************************************/
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("1");
        }
    }
}
