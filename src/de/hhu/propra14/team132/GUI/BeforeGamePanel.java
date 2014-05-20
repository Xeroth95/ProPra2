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
    JPanel colorPanel;
    JLabel player1Label;
    JLabel player2Label;
    JComboBox player1ColorBox;
    JComboBox player2ColorBox;
    JButton startGameButton;

    public BeforeGamePanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new BorderLayout());

        colors=new String[]{"Pink", "Red", "Yellow", "Blue"};
        colorPanel=new JPanel(new GridLayout(2,2));
        player1Label=new JLabel("Player 1");
        player2Label=new JLabel("Player 2");
        player1ColorBox=new JComboBox(colors);
        player1ColorBox.addActionListener(new ColorBoxListener());
        player2ColorBox=new JComboBox(colors);
        player2ColorBox.addActionListener(new ColorBoxListener());
        startGameButton=new JButton("Start Game");
        startGameButton.addActionListener(new StartGameListener());
        previewPanel=new PreviewPanel(this);

        colorPanel.add(player1Label);
        colorPanel.add(player2Label);
        colorPanel.add(player1ColorBox);
        colorPanel.add(player2ColorBox);

        this.add(colorPanel, BorderLayout.NORTH);
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
                BeforeGamePanel.this.mainPanel.showPanel("2");
                mainPanel.mainGamePanel.gamePanel.gameManager.setBeforeStart(false);
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
