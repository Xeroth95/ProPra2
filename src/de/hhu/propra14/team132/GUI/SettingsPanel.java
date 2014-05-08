package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 02.05.14.
 */
public class SettingsPanel extends JPanel {
        //this panel is an undermenu just for the settings
        //for now, it contains Video-, Audio-, and Controlsettings

        MainPanel mainPanel;

        JButton button_video;
        JButton button_audio;
        JButton button_controls;
        JButton button_go_back;

        public SettingsPanel(MainPanel mainPanel) {
            this.mainPanel=mainPanel;

            button_video = new JButton("Video Settings");
            button_video.addActionListener(new VideoListener());
            button_audio = new JButton("Audio Settings");
            button_audio.addActionListener(new AudioListener());
            button_controls = new JButton("Control Settings");
            button_controls.addActionListener(new ControlsListener());
            button_go_back = new JButton("Go back to Main Menu");
            button_go_back.addActionListener(new GoBackListener());

            this.setLayout(new GridLayout(4, 1, 0, 10));
            this.add(button_video);
            this.add(button_audio);
            this.add(button_controls);
            this.add(button_go_back);
        }

    class VideoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("5");//switch to VideoSettingsPanel
        }
    }

    class AudioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("6");//switch to AudioSettingsPanel
        }
    }

    class ControlsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("7");//switch to ControlSettingsPanel
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("1");//switch back to MenuPanel
        }
    }
}
