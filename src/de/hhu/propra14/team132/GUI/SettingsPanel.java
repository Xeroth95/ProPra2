package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * Created by fabian on 02.05.14.
 */
public class SettingsPanel extends JPanel {
    //this panel is an undermenu just for the settings
    //for now, it contains Video-, Audio-, and Controlsettings

    MainPanel mainPanel;
    JButton videoButton;
    JButton audioButton;
    JButton controlsButton;
    JButton gameButton;
    JButton setStandardButton;
    JButton goBackButton;
    Options options;

    public SettingsPanel(MainPanel mainPanel, Options options) {
        this.mainPanel=mainPanel;
        this.options=options;

        videoButton = new JButton("Video Settings");
        videoButton.addActionListener(new VideoListener());
        audioButton = new JButton("Audio Settings");
        audioButton.addActionListener(new AudioListener());
        controlsButton = new JButton("Control Settings");
        controlsButton.addActionListener(new ControlsListener());
        gameButton=new JButton("Game Settings");
        gameButton.addActionListener(new GameListener());
        setStandardButton=new JButton("Apply standard options");
        setStandardButton.addActionListener(new SetStandardListener());
        goBackButton = new JButton("Go back to Main Menu");
        goBackButton.addActionListener(new GoBackListener());

        this.setLayout(new GridLayout(6, 1, 0, 10));
        this.add(videoButton);
        this.add(audioButton);
        this.add(controlsButton);
        this.add(gameButton);
        this.add(setStandardButton);
        this.add(goBackButton);
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

    class GameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("10");//switch to GameSettingsPanel
        }
    }

    class SetStandardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            options.setStandard();
            SettingsPanel.this.mainPanel.audioSettingsPanel.bgVolumeSlider.setValue(options.getBgVolume());
            SettingsPanel.this.mainPanel.audioSettingsPanel.fxVolumeSlider.setValue(options.getFxVolume());
            if(options.getControls()==options.ARROWS) {
                SettingsPanel.this.mainPanel.controlSettingsPanel.groupMoveControls.setSelected(SettingsPanel.this.mainPanel.controlSettingsPanel.buttonArrowControls.getModel(), true);
                SettingsPanel.this.mainPanel.controlSettingsPanel.groupMoveControls.setSelected(SettingsPanel.this.mainPanel.controlSettingsPanel.buttonWasdControls.getModel(), false);
            }
            if(options.getControls()==options.WASD) {
                SettingsPanel.this.mainPanel.controlSettingsPanel.groupMoveControls.setSelected(SettingsPanel.this.mainPanel.controlSettingsPanel.buttonWasdControls.getModel(), true);
                SettingsPanel.this.mainPanel.controlSettingsPanel.groupMoveControls.setSelected(SettingsPanel.this.mainPanel.controlSettingsPanel.buttonArrowControls.getModel(), false);
            }
            SettingsPanel.this.mainPanel.gameSettingsPanel.timeTextField.setText(String.valueOf(options.getRoundLength()));
            SettingsPanel.this.mainPanel.gameSettingsPanel.wormNumberTextField.setText(String.valueOf(options.getWormsNumber()));
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsPanel.this.mainPanel.showPanel("1");//switch back to MenuPanel
            options.setBgVolume(SettingsPanel.this.mainPanel.audioSettingsPanel.getBgVolume());
            options.setFxVolume(SettingsPanel.this.mainPanel.audioSettingsPanel.getFxVolume());
            options.setControls(SettingsPanel.this.mainPanel.controlSettingsPanel.getControls());
            options.setRoundLength(SettingsPanel.this.mainPanel.gameSettingsPanel.getRoundLength());
            options.setWormsNumber(SettingsPanel.this.mainPanel.gameSettingsPanel.getWormNumber());
            options.save();
        }
    }
}
