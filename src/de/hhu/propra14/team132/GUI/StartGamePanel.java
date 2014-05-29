package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by fabian on 06.05.14.
 */
public class StartGamePanel extends JPanel {
    //this panel lets the player choose wether he wants to start a local or a network game

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;

    JButton button_local_game;
    JButton button_network_game_host;
    JButton button_network_game_client;
    JButton button_go_back;

    public StartGamePanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new GridLayout(4,1,10,10));

        button_local_game=new JButton("Start local game");
        button_local_game.addActionListener(new LocalGameListener());
        button_network_game_host=new JButton("Start network game as host");
        button_network_game_host.addActionListener(new NetworkGameHostListener());
        button_network_game_client=new JButton("Start network game as client");
        button_network_game_client.addActionListener(new NetworkGameClientListener());
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
            StartGamePanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("12");
        }
    }

    class NetworkGameHostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("13");
        }
    }

    class NetworkGameClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("14");
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartGamePanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            StartGamePanel.this.mainPanel.showPanel("1");
        }
    }
}
