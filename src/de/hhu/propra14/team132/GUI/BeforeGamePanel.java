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
    JPanel player1WeaponsPanel;
    JPanel player2WeaponsPanel;
    JCheckBox player1BazookaBox;
    JCheckBox player1PistolBox;
    JCheckBox player1MachineGunBox;
    JCheckBox player1SkipBox;
    JCheckBox player2BazookaBox;
    JCheckBox player2PistolBox;
    JCheckBox player2MachineGunBox;
    JCheckBox player2SkipBox;
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

        colors=new String[]{"Pink", "Red", "Yellow", "Blue", "Nic"};
        configPanel=new JPanel(new GridLayout(4,2));
        player1TeamNamePanel=new JPanel(new GridLayout(1,2));
        player2TeamNamePanel=new JPanel(new GridLayout(1,2));
        player1WeaponsPanel=new JPanel(new FlowLayout());
        player2WeaponsPanel=new JPanel(new FlowLayout());
        player1BazookaBox=new JCheckBox("Bazooka");
        player1BazookaBox.setSelected(true);
        player1PistolBox=new JCheckBox("Pistol");
        player1PistolBox.setSelected(true);
        player1MachineGunBox=new JCheckBox("Machine Gun");
        player1MachineGunBox.setSelected(true);
        player1SkipBox=new JCheckBox("Skip");
        player1SkipBox.setSelected(true);
        player2BazookaBox=new JCheckBox("Bazooka");
        player2BazookaBox.setSelected(true);
        player2PistolBox=new JCheckBox("Pistol");
        player2PistolBox.setSelected(true);
        player2MachineGunBox=new JCheckBox("Machine Gun");
        player2MachineGunBox.setSelected(true);
        player2SkipBox=new JCheckBox("Skip");
        player2SkipBox.setSelected(true);
        player1Label=new JLabel("<HTML><U>Player 1</U></HTML>");
        player2Label=new JLabel("<HTML><U>Player 2</U></HTML>");
        player1TeamNameLabel=new JLabel("Teamname:");
        player2TeamNameLabel=new JLabel("Teamname:");
        player1TeamNameField=new JTextField("Player 1");
        player2TeamNameField=new JTextField("Player 2");
        player1ColorBox=new JComboBox(colors);
        player1ColorBox.setSelectedIndex(0);
        player1ColorBox.addActionListener(new ColorBoxListener());
        player2ColorBox=new JComboBox(colors);
        player2ColorBox.setSelectedIndex(1);
        player2ColorBox.addActionListener(new ColorBoxListener());
        startGameButton=new JButton("Start Game");
        startGameButton.addActionListener(new StartGameListener());
        previewPanel=new PreviewPanel(this);

        player1TeamNamePanel.add(player1TeamNameLabel);
        player1TeamNamePanel.add(player1TeamNameField);

        player2TeamNamePanel.add(player2TeamNameLabel);
        player2TeamNamePanel.add(player2TeamNameField);

        player1WeaponsPanel.add(player1BazookaBox);
        player1WeaponsPanel.add(player1PistolBox);
        player1WeaponsPanel.add(player1MachineGunBox);
        player1WeaponsPanel.add(player1SkipBox);

        player2WeaponsPanel.add(player2BazookaBox);
        player2WeaponsPanel.add(player2PistolBox);
        player2WeaponsPanel.add(player2MachineGunBox);
        player2WeaponsPanel.add(player2SkipBox);

        configPanel.add(player1Label);
        configPanel.add(player2Label);
        configPanel.add(player1TeamNamePanel);
        configPanel.add(player2TeamNamePanel);
        configPanel.add(player1WeaponsPanel);
        configPanel.add(player2WeaponsPanel);
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
                    if(BeforeGamePanel.this.player1TeamNameField.getText().equals(BeforeGamePanel.this.player2TeamNameField.getText())) {
                        JOptionPane.showMessageDialog(null,"The players must have different names!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        BeforeGamePanel.this.mainPanel.showPanel("2");
                        mainPanel.mainGamePanel.gamePanel.gameManager.setBeforeStart(false);
                    }
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
