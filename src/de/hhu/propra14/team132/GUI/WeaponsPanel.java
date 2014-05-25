package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameObjects.Weapons.Bazooka;
import de.hhu.propra14.team132.gameObjects.Weapons.MachineGun;
import de.hhu.propra14.team132.gameObjects.Weapons.Pistol;
import de.hhu.propra14.team132.gameObjects.Weapons.Skip;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Messages.WeaponSelectMessage;
import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by fabian on 03.05.14.
 */
public class WeaponsPanel extends JPanel {
    MainPanel mainPanel;
    GameManager gameManager;
    SoundEngine soundEngine;
    File klickSoundFile;

    ArrayList<JButton> weapons;

    public WeaponsPanel(MainPanel mainPanel, GameManager gameManager, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.gameManager=gameManager;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        weapons=new ArrayList<JButton>();

        weapons.add(new JButton("Bazooka"));
        weapons.get(0).addActionListener(new WeaponsListener());
        weapons.add(new JButton("Pistol"));
        weapons.get(1).addActionListener(new WeaponsListener());
        weapons.add(new JButton("Machine Gun"));
        weapons.get(2).addActionListener(new WeaponsListener());
        weapons.add(new JButton("Skip"));
        weapons.get(3).addActionListener(new WeaponsListener());

        this.add(weapons.get(0));
        this.add(weapons.get(1));
        this.add(weapons.get(2));
        this.add(weapons.get(3));

        this.setVisible(false);
    }

    public void setButtonsVisibility(ArrayList<JCheckBox> weaponsList) {
        for(int i=0; i<=weapons.size()-1; i++) {
            weapons.get(i).setVisible(weaponsList.get(i).isSelected());
        }
    }

    class WeaponsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WeaponsPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            String pressed=((JButton)e.getSource()).getText();
            switch (pressed) {
                case "Bazooka":
                    gameManager.sendMessage(new WeaponSelectMessage(Bazooka.class));
                    break;
                case "Pistol":
                    gameManager.sendMessage(new WeaponSelectMessage(Pistol.class));
                    break;
                case "Machine Gun":
                    gameManager.sendMessage(new WeaponSelectMessage(MachineGun.class));
                    break;
                case "Skip":
                    gameManager.sendMessage(new WeaponSelectMessage(Skip.class));
                    break;
            }
        }
    }
}
