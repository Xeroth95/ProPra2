package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 02.05.14.
 */
public class AudioSettingsPanel extends JPanel{
    //this panel will contain the audio settings
    //but for now it is just a placeholder

    MainPanel mainPanel;

    JButton button_go_back;

    public AudioSettingsPanel(MainPanel mainPanel)
    {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(1,1,10,10));

        button_go_back=new JButton("Go back to Settings Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(button_go_back);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AudioSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
