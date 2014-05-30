package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.Network.Client;
import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
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
public class LobbyPanel extends JPanel implements Communicable {
	
    //this panel contains the chat lobby for a network game

    MainPanel mainPanel;


    JPanel panel1;//contains msgField and button_send
    JPanel panel2;//contains button_start_game and button_go_back
    JTextArea chatArea;
    DefaultCaret chatAreaCaret;
    JTextField msgField;
    JButton button_send;
    JButton button_start_game;
    JButton button_go_back;

    String playerName;

    public LobbyPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        this.setLayout(new BorderLayout());

        panel1=new JPanel(new BorderLayout());
        panel2=new JPanel(new GridLayout(1,2));
        chatArea=new JTextArea();
        chatArea.setEditable(false);
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
        
        this.mainPanel.manager.register(this, MessageType.CHAT);
    }

    public void setPlayerName(String playerName) {
        this.playerName=playerName;
    }

    class SendListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            if(LobbyPanel.this.msgField.getText().equals("clear")) {
                chatArea.setText(null);//clear chatArea on 'clear'-command
                LobbyPanel.this.msgField.setText(null);
            }
            else {
            	String message = ">" + playerName + ": " + LobbyPanel.this.msgField.getText() + "\n";
                LobbyPanel.this.chatArea.append(message);
                LobbyPanel.this.mainPanel.manager.sendMessage(new ChatMessage(message, LobbyPanel.this));
                LobbyPanel.this.msgField.setText(null);
            }
        }
    }

    class StartGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            LobbyPanel.this.mainPanel.showPanel("2");
        }
    }

    class GoBackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
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

	@Override
	public void receiveMessage(Message m) {
		if (m.getMessageType() != MessageType.CHAT) return;
		ChatMessage cm = (ChatMessage) m;
		this.chatArea.append(cm.getMessage());
	}
}
