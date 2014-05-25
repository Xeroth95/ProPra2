package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by fabian on 20.05.14.
 */
public class BeforeGamePanel extends JPanel {
    GameManager gameManager;
    MainPanel mainPanel;
    Options options;
    SoundEngine soundEngine;
    File klickSoundFile;
    PreviewPanel previewPanel;

    String[] colors;
    JPanel configPanel;
    JPanel player1TeamNamePanel;
    JPanel player2TeamNamePanel;
    JPanel player1WeaponsPanel;
    JPanel player2WeaponsPanel;
    ArrayList<JCheckBox> player1WeaponsList;
    ArrayList<JCheckBox> player2WeaponsList;
    JLabel player1Label;
    JLabel player2Label;
    JLabel player1TeamNameLabel;
    JLabel player2TeamNameLabel;
    JTextField player1TeamNameField;
    JTextField player2TeamNameField;
    JComboBox player1ColorBox;
    JComboBox player2ColorBox;
    JButton startGameButton;

    public BeforeGamePanel(MainPanel mainPanel, GameManager gameManager, Options options, SoundEngine soundEngine, File klickSoundFile) {
        this.gameManager=gameManager;
        this.mainPanel=mainPanel;
        this.options=options;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new BorderLayout());

        colors=new String[]{"Pink", "Red", "Yellow", "Blue", "Nic"};
        configPanel=new JPanel(new GridLayout(4,2));
        player1TeamNamePanel=new JPanel(new GridLayout(1,2));
        player2TeamNamePanel=new JPanel(new GridLayout(1,2));
        player1WeaponsPanel=new JPanel(new FlowLayout());
        player2WeaponsPanel=new JPanel(new FlowLayout());
        player1WeaponsList=new ArrayList<JCheckBox>();
        player2WeaponsList=new ArrayList<JCheckBox>();
        player1WeaponsList.add(new JCheckBox("Bazooka"));
        player1WeaponsList.get(0).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Pistol"));
        player1WeaponsList.get(1).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Machine Gun"));
        player1WeaponsList.get(2).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Skip"));
        player1WeaponsList.get(3).setSelected(true);
        player2WeaponsList.add(new JCheckBox("Bazooka"));
        player2WeaponsList.get(0).setSelected(true);
        player2WeaponsList.add(new JCheckBox("Pistol"));
        player2WeaponsList.get(1).setSelected(true);
        player2WeaponsList.add(new JCheckBox("Machine Gun"));
        player2WeaponsList.get(2).setSelected(true);
        player2WeaponsList.add(new JCheckBox("Skip"));
        player2WeaponsList.get(3).setSelected(true);
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

        player1WeaponsPanel.add(player1WeaponsList.get(0));
        player1WeaponsPanel.add(player1WeaponsList.get(1));
        player1WeaponsPanel.add(player1WeaponsList.get(2));
        player1WeaponsPanel.add(player1WeaponsList.get(3));

        player2WeaponsPanel.add(player2WeaponsList.get(0));
        player2WeaponsPanel.add(player2WeaponsList.get(1));
        player2WeaponsPanel.add(player2WeaponsList.get(2));
        player2WeaponsPanel.add(player2WeaponsList.get(3));

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

    public boolean noneSelected(ArrayList<JCheckBox> list) {
        for (int i=0; i<list.size(); i++) {
            if(list.get(i).isSelected()){
                return false;
            }
        }
        return true;
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BeforeGamePanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            if(BeforeGamePanel.this.player1ColorBox.getSelectedIndex()==BeforeGamePanel.this.player2ColorBox.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null,"The players must have different colors!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if(BeforeGamePanel.this.player1TeamNameField.getText().equals("") || BeforeGamePanel.this.player2TeamNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Both players must enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if(BeforeGamePanel.this.player1TeamNameField.getText().equals(BeforeGamePanel.this.player2TeamNameField.getText())) {
                        JOptionPane.showMessageDialog(null, "The players must have different names!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        if(BeforeGamePanel.this.noneSelected(player1WeaponsList) || BeforeGamePanel.this.noneSelected(player2WeaponsList)) {
                            JOptionPane.showMessageDialog(null,"Both players must have at least one happen!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            //RuleSet.generateCustomRuleSet()
                            BeforeGamePanel.this.mainPanel.showPanel("2");
                            mainPanel.mainGamePanel.gamePanel.gameManager.setBeforeStart(false);
                        }
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
