package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 02.05.14.
 */
public class ControlSettingsPanel extends JPanel{
    MainPanel mainPanel;

    JButton button_go_back;

    public ControlSettingsPanel(MainPanel mainPanel)
    {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(1,1,10,10));

        button_go_back=new JButton("Go back to Settings Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(button_go_back);
    }

    class GoBackListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            ControlSettingsPanel.this.mainPanel.showPanel("3");
        }
    }
}
