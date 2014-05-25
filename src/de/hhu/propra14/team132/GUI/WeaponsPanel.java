package de.hhu.propra14.team132.GUI;

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
    SoundEngine soundEngine;
    File klickSoundFile;

    ArrayList<JButton> weapons;

    public WeaponsPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
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

    public void setButtonsVisibality(ArrayList<JCheckBox> weaponsList) {
        for(int i=0; i<=weapons.size()-1; i++) {
            weapons.get(i).setVisible(weaponsList.get(i).isSelected());
        }
    }

    class WeaponsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WeaponsPanel.this.soundEngine.play(klickSoundFile);
            String pressed=((JButton)e.getSource()).getText();
        }
    }
}
