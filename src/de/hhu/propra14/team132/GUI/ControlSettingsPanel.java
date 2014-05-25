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
public class ControlSettingsPanel extends JPanel {
    //this panel will contain the control settings
    //but for now it is just a placeholder

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;

    JPanel panelMoveControls;
    JButton GoBackButton;
    ButtonGroup groupMoveControls;
    JRadioButton buttonArrowControls;
    JRadioButton buttonWasdControls;

    public ControlSettingsPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel = mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new GridLayout(2, 1, 10, 10));

        panelMoveControls=new JPanel(new FlowLayout());
        buttonArrowControls=new JRadioButton("Control worms with arrowkeys");
        buttonWasdControls=new JRadioButton("Control worms with WASD-keys");
        groupMoveControls=new ButtonGroup();
        groupMoveControls.add(buttonArrowControls);
        groupMoveControls.add(buttonWasdControls);
        GoBackButton = new JButton("Go back to Settings Menu");
        GoBackButton.addActionListener(new GoBackListener());

        panelMoveControls.add(buttonArrowControls);
        buttonArrowControls.setSelected(true);
        panelMoveControls.add(buttonWasdControls);

        this.add(panelMoveControls);
        this.add(GoBackButton);
    }

    public int getControls() {
        if(buttonArrowControls.isSelected()) {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ControlSettingsPanel.this.soundEngine.play(klickSoundFile, mainPanel.options.getFxVolume());
            ControlSettingsPanel.this.mainPanel.showPanel("3");//switch back to setting menu
        }
    }
}
