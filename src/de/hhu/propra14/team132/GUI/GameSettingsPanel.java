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

    JPanel timePanel;
    JLabel timeLabel;
    JTextField timeTextField;
    JButton timeButtonPlus;
    JButton timeButtonMinus;

    JButton goBackButton;

    public GameSettingsPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;
        this.setLayout(new GridLayout(2,1,10,10));

        timePanel=new JPanel(new FlowLayout());
        goBackButton=new JButton("Go back to Settings Menu");
        goBackButton.addActionListener(new GoBackListener());
        timeLabel=new JLabel("Roundlength in seconds:");
        timeTextField=new JTextField(4);
        timeTextField.setText("120");
        timeTextField.setEditable(false);
        timeButtonPlus=new JButton("+");
        timeButtonPlus.addActionListener(new TimePlusListener());
        timeButtonMinus=new JButton("-");
        timeButtonMinus.addActionListener(new TimeMinusListener());

        timePanel.add(timeLabel);
        timePanel.add(timeTextField);
        timePanel.add(timeButtonPlus);
        timePanel.add(timeButtonMinus);

        this.add(timePanel);
        this.add(goBackButton);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }

    class TimePlusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int time=Integer.parseInt(GameSettingsPanel.this.timeTextField.getText());
            if(time>=99999) {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(99999));
            }
            else {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(time + 1));
            }
        }
    }

    class TimeMinusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int time=Integer.parseInt(GameSettingsPanel.this.timeTextField.getText());
            if(time<=0) {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(0));
            }
            else {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(time - 1));
            }
        }
    }
}
