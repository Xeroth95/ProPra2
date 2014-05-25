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
public class VideoSettingsPanel extends JPanel{
    //this panel will contain the audio settings
    //but for now it is just a placeholder

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;

    JButton GoBackButton;

    public VideoSettingsPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile)
    {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new GridLayout(1,1,10,10));

        GoBackButton=new JButton("Go back to Settings Menu");
        GoBackButton.addActionListener(new GoBackListener());

        this.add(GoBackButton);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VideoSettingsPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            VideoSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
