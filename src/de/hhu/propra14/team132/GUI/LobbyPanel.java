package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by fabian on 06.05.14.
 */
public class LobbyPanel extends JPanel {
    //this panel contains the chat lobby for a network game

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;


    JPanel panel1;//contains msgField and button_send
    JPanel panel2;//contains button_start_game and button_go_back
    JTextArea chatArea;
    DefaultCaret chatAreaCaret;
    JTextField msgField;
    JButton button_send;
    JButton button_start_game;
    JButton button_go_back;

    public LobbyPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new BorderLayout());

        panel1=new JPanel(new BorderLayout());
        panel2=new JPanel(new GridLayout(1,2));
        chatArea=new JTextArea();
        chatArea.setEditable(true);
        chatAreaCaret=(DefaultCaret) chatArea.getCaret();
        chatAreaCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        msgField=new JTextField();
        msgField.addKeyListener(new KeySendListener());
        button_send=new JButton("Send");
        button_send.addActionListener(new SendListener());
        button_start_game=new JButton("Start Game");
        button_start_game.addActionListener(new StartGameListener());
        button_go_back=new JButton("Go back to Main Menu");
        button_go_back.addActionListener(new GoBackListener());

        panel2.add(button_start_game);
        panel2.add(button_go_back);

        panel1.add(msgField,BorderLayout.CENTER);
        panel1.add(button_send, BorderLayout.EAST);
        panel1.add(panel2, BorderLayout.SOUTH);

        this.add(new JScrollPane(chatArea),BorderLayout.CENTER);
        this.add(panel1, BorderLayout.SOUTH);
    }

    class SendListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LobbyPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            if(LobbyPanel.this.msgField.getText().equals("clear")) {
                chatArea.setText(null);//clear chatArea on 'clear'-command
                LobbyPanel.this.msgField.setText(null);
            }
            else {
                LobbyPanel.this.chatArea.append(">" + LobbyPanel.this.msgField.getText() + "\n");
                LobbyPanel.this.msgField.setText(null);
                System.out.println(LobbyPanel.this.msgField.getText());
            }
        }
    }

    class StartGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LobbyPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            LobbyPanel.this.mainPanel.showPanel("2");
        }
    }

    class GoBackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LobbyPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            LobbyPanel.this.mainPanel.showPanel("1");
        }
    }

    class KeySendListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                LobbyPanel.this.button_send.doClick();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
