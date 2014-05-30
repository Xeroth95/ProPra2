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
public class AboutPanel extends JPanel{
    //this panel shows the credits
    //it has just a few labels and a button to go back to the main menu
    //therefore, it should be pretty self explanatory

    MainPanel mainPanel;

    JLabel about_label1;
    JLabel about_label2;
    JButton button_go_back;

    public AboutPanel(MainPanel mainPanel)
    {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(3,1));

        about_label1=new JLabel("ProPra14 - Team 132", JLabel.CENTER);
        about_label1.setFont(new Font("Serif", Font.BOLD, 26));
        about_label2=new JLabel("Created by Chris Rutenkolk, Sebastian Sura, Isabel Wingen and Fabian Ruhland", JLabel.CENTER);
        about_label2.setFont(new Font("Serif", Font.BOLD, 14));

        button_go_back=new JButton("Go back to Main Menu");
        button_go_back.addActionListener(new GoBackListener());

        this.add(about_label1);
        this.add(about_label2);
        this.add(button_go_back);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            AboutPanel.this.mainPanel.showPanel("1");//switch back to main menu
        }
    }
}
