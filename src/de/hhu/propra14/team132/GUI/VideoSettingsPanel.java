package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 02.05.14.
 */
public class VideoSettingsPanel extends JPanel{
    //this panel will contain the audio settings
    //but for now it is just a placeholder

    MainPanel mainPanel;

    JButton GoBackButton;

    public VideoSettingsPanel(MainPanel mainPanel)
    {
        this.mainPanel=mainPanel;
        this.setLayout(new GridLayout(1,1,10,10));

        GoBackButton=new JButton("Go back to Settings Menu");
        GoBackButton.addActionListener(new GoBackListener());

        this.add(GoBackButton);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VideoSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
