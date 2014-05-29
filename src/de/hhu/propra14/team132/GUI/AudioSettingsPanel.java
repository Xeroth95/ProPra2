package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by fabian on 02.05.14.
 */
public class AudioSettingsPanel extends JPanel{
    //this panel will contain the audio settings
    //but for now it is just a placeholder

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;

    JPanel volumePanel;
    JLabel bgVolumeLabel;
    JLabel fxVolumeLabel;
    JLabel volumeLabel;
    JSlider bgVolumeSlider;
    JSlider fxVolumeSlider;
    JButton GoBackButton;

    public AudioSettingsPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new GridLayout(2,1,10,10));

        volumePanel=new JPanel(new FlowLayout());
        volumeLabel=new JLabel("<HTML><U>Audio volume:</U></HTML>");
        bgVolumeLabel=new JLabel("Music:");
        fxVolumeLabel=new JLabel("FX:");
        bgVolumeSlider=new JSlider();
        fxVolumeSlider=new JSlider();
        GoBackButton=new JButton("Go back to Settings Menu");
        GoBackButton.addActionListener(new GoBackListener());

        volumePanel.add(volumeLabel);
        volumePanel.add(bgVolumeLabel);
        volumePanel.add(bgVolumeSlider);
        volumePanel.add(fxVolumeLabel);
        volumePanel.add(fxVolumeSlider);

        this.add(volumePanel);
        this.add(GoBackButton);
    }

    public int getBgVolume() {
        return bgVolumeSlider.getValue();
    }

    public int getFxVolume() {
        return fxVolumeSlider.getValue();
    }


    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AudioSettingsPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            AudioSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
