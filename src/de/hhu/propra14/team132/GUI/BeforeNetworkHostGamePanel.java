package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.Network.Server;
import de.hhu.propra14.team132.gameObjects.Weapons.*;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.sound.SoundEngine;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by fabian on 29.05.14.
 */
public class BeforeNetworkHostGamePanel extends JPanel {
    GameManager gameManager;
    MainPanel mainPanel;
    Options options;

    String[] colors;
    JPanel configPanel;
    JPanel player1TeamNamePanel;
    JPanel player1WeaponsPanel;
    ArrayList<JCheckBox> player1WeaponsList;
    JLabel player1Label;
    JLabel player1TeamNameLabel;
    JTextField player1TeamNameField;
    JComboBox<String> player1ColorBox;
    JButton startGameButton;
    JLabel previewLabel;
    Image previewImage;


    ArrayList<Weapon> playerWeapons;

    public BeforeNetworkHostGamePanel(MainPanel mainPanel, GameManager gameManager, Options options) {
        this.gameManager=gameManager;
        this.mainPanel=mainPanel;
        this.options=options;

        this.setLayout(new BorderLayout());

        colors=new String[]{"Pink", "Red", "Yellow", "Blue", "Nic"};
        configPanel=new JPanel(new GridLayout(4,1));
        player1TeamNamePanel=new JPanel(new GridLayout(1,2));
        player1WeaponsPanel=new JPanel(new FlowLayout());
        player1WeaponsList=new ArrayList<JCheckBox>();
        player1WeaponsList.add(new JCheckBox("Bazooka"));
        player1WeaponsList.get(0).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Pistol"));
        player1WeaponsList.get(1).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Machine Gun"));
        player1WeaponsList.get(2).setSelected(true);
        player1WeaponsList.add(new JCheckBox("Skip"));
        player1WeaponsList.get(3).setSelected(true);
        player1Label=new JLabel("<HTML><U>Player 1</U></HTML>");
        player1TeamNameLabel=new JLabel("Teamname:");
        player1TeamNameField=new JTextField("Player 1");
        player1ColorBox=new JComboBox<String>(colors);
        player1ColorBox.setSelectedIndex(0);
        player1ColorBox.addActionListener(new ColorBoxListener());
        startGameButton=new JButton("Start Game");
        startGameButton.addActionListener(new StartGameListener());
        previewLabel=new JLabel();

        try {
            this.previewImage = ImageIO.read(new File("res/img/preview/" + (String) this.player1ColorBox.getSelectedItem() + "Worm.png"));
            this.previewImage=this.previewImage.getScaledInstance(200,200,Image.SCALE_SMOOTH);

            previewLabel.setIcon(new ImageIcon(previewImage));
        }
        catch (Exception ex) {
            System.err.println("Error loading preview");
            ex.printStackTrace();
        }

        player1TeamNamePanel.add(player1TeamNameLabel);
        player1TeamNamePanel.add(player1TeamNameField);

        player1WeaponsPanel.add(player1WeaponsList.get(0));
        player1WeaponsPanel.add(player1WeaponsList.get(1));
        player1WeaponsPanel.add(player1WeaponsList.get(2));
        player1WeaponsPanel.add(player1WeaponsList.get(3));

        configPanel.add(player1Label);
        configPanel.add(player1TeamNamePanel);
        configPanel.add(player1WeaponsPanel);
        configPanel.add(player1ColorBox);

        this.add(configPanel, BorderLayout.NORTH);
        this.add(previewLabel, BorderLayout.CENTER);
        this.add(startGameButton, BorderLayout.SOUTH);
    }

    public void generateWeaponsLists() {
        playerWeapons=new ArrayList <Weapon>();

        if(player1WeaponsList.get(0).isSelected()) {
            playerWeapons.add(new Bazooka());
        }
        if(player1WeaponsList.get(1).isSelected()) {
            playerWeapons.add(new Pistol());
        }
        if(player1WeaponsList.get(2).isSelected()) {
            playerWeapons.add(new MachineGun());
        }
        if(player1WeaponsList.get(3).isSelected()) {
            playerWeapons.add(new Skip());
        }
    }

    public boolean noneSelected(ArrayList<JCheckBox> list) {
        for (int i=0; i<list.size(); i++) {
            if(list.get(i).isSelected()){
                return false;
            }
        }
        return true;
    }

    class ColorBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                BeforeNetworkHostGamePanel.this.previewImage = ImageIO.read(new File("res/img/preview/" + (String) BeforeNetworkHostGamePanel.this.player1ColorBox.getSelectedItem() + "Worm.png"));
                BeforeNetworkHostGamePanel.this.previewImage=BeforeNetworkHostGamePanel.this.previewImage.getScaledInstance(200,200,Image.SCALE_SMOOTH);
                BeforeNetworkHostGamePanel.this.previewLabel.setIcon(new ImageIcon(BeforeNetworkHostGamePanel.this.previewImage));
            }
            catch (Exception ex) {
                System.err.println("Error loading preview");
                ex.printStackTrace();
            }
        }
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
                if(BeforeNetworkHostGamePanel.this.player1TeamNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Please enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if(BeforeNetworkHostGamePanel.this.noneSelected(player1WeaponsList)) {
                      JOptionPane.showMessageDialog(null,"Please select at least one weapon!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                       generateWeaponsLists();
                       BeforeNetworkHostGamePanel.this.mainPanel.lobbyPanel.setPlayerName(BeforeNetworkHostGamePanel.this.player1TeamNameField.getText());
                       BeforeNetworkHostGamePanel.this.gameManager.createServer();
                       BeforeNetworkHostGamePanel.this.mainPanel.showPanel("9");
                    }
                }
            }
        }
}