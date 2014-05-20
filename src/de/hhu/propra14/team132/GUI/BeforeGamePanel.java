package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by fabian on 20.05.14.
 */
public class BeforeGamePanel extends JPanel {
    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;
    PreviewPanel previewPanel;

    String[] colors;
    JPanel configPanel;
    JPanel player1TeamNamePanel;
    JPanel player2TeamNamePanel;
    JLabel player1Label;
    JLabel player2Label;
    JLabel player1TeamNameLabel;
    JLabel player2TeamNameLabel;
    JTextField player1TeamNameField;
    JTextField player2TeamNameField;
    JComboBox player1ColorBox;
    JComboBox player2ColorBox;
    JButton startGameButton;

    public BeforeGamePanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new BorderLayout());

        colors=new String[]{"Pink", "Red", "Yellow", "Blue"};
        configPanel=new JPanel(new GridLayout(3,2));
        player1TeamNamePanel=new JPanel(new GridLayout(1,2));
        player2TeamNamePanel=new JPanel(new GridLayout(1,2));
        player1Label=new JLabel("Player 1");
        player2Label=new JLabel("Player 2");
        player1TeamNameLabel=new JLabel("Teamname:");
        player2TeamNameLabel=new JLabel("Teamname:");
        player1TeamNameField=new JTextField();
        player2TeamNameField=new JTextField();
        player1ColorBox=new JComboBox(colors);
        player1ColorBox.addActionListener(new ColorBoxListener());
        player2ColorBox=new JComboBox(colors);
        player2ColorBox.addActionListener(new ColorBoxListener());
        startGameButton=new JButton("Start Game");
        startGameButton.addActionListener(new StartGameListener());
        previewPanel=new PreviewPanel(this);

        player1TeamNamePanel.add(player1TeamNameLabel);
        player1TeamNamePanel.add(player1TeamNameField);

        player2TeamNamePanel.add(player2TeamNameLabel);
        player2TeamNamePanel.add(player2TeamNameField);

        configPanel.add(player1Label);
        configPanel.add(player2Label);
        configPanel.add(player1TeamNamePanel);
        configPanel.add(player2TeamNamePanel);
        configPanel.add(player1ColorBox);
        configPanel.add(player2ColorBox);

        this.add(configPanel, BorderLayout.NORTH);
        this.add(previewPanel, BorderLayout.CENTER);
        this.add(startGameButton, BorderLayout.SOUTH);
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BeforeGamePanel.this.soundEngine.play(klickSoundFile);
            if(BeforeGamePanel.this.player1ColorBox.getSelectedIndex()==BeforeGamePanel.this.player2ColorBox.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null,"The players must have different colors!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if(BeforeGamePanel.this.player1TeamNameField.getText().equals("") || BeforeGamePanel.this.player2TeamNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Both players must enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    BeforeGamePanel.this.mainPanel.showPanel("2");
                    mainPanel.mainGamePanel.gamePanel.gameManager.setBeforeStart(false);
                }
            }
        }
    }

    class ColorBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BeforeGamePanel.this.previewPanel.refreshPreview();
        }
    }
}
