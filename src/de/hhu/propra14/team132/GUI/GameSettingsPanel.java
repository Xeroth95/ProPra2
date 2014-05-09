package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 09.05.14.
 */
public class GameSettingsPanel extends JPanel {
    //this panel contain all the settings that influence the GameMechanics
    MainPanel mainPanel;

    JButton button_go_back;

    public GameSettingsPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;
        this.setLayout(new GridLayout(1,1,10,10));

        button_go_back=new JButton("Go back to Settings Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(button_go_back);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
