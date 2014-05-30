package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by fabian on 09.05.14.
 */
public class GameSettingsPanel extends JPanel {
    //this panel contain all the settings that influence the GameMechanics
    MainPanel mainPanel;

    JPanel timePanel;
    JPanel wormNumberPanel;
    JLabel timeLabel;
    JTextField timeTextField;
    JTextField wormNumberTextField;
    JButton timeButtonPlus;
    JButton timeButtonMinus;
    JButton timeButtonPlus10;
    JButton timeButtonMinus10;
    JLabel wormNumberLabel;
    JButton wormNumberButtonPlus;
    JButton wormNumberButtonMinus;

    JButton goBackButton;

    public GameSettingsPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        this.setLayout(new GridLayout(3,1,10,10));

        timePanel=new JPanel(new FlowLayout());
        wormNumberPanel=new JPanel(new FlowLayout());
        timeLabel=new JLabel("Roundlength in seconds:");
        wormNumberLabel=new JLabel("Number of worms per Team");
        timeTextField=new JTextField(4);
        timeTextField.setText("120");
        timeTextField.setEditable(false);
        wormNumberTextField=new JTextField(1);
        wormNumberTextField.setText("4");
        wormNumberTextField.setEditable(false);
        timeButtonPlus=new JButton("+");
        timeButtonPlus.addActionListener(new TimePlusListener());
        timeButtonMinus=new JButton("-");
        timeButtonMinus.addActionListener(new TimeMinusListener());
        timeButtonPlus10=new JButton("+10");
        timeButtonPlus10.addActionListener(new TimePlus10Listener());
        timeButtonMinus10=new JButton("-10");
        timeButtonMinus10.addActionListener(new TimeMinus10Listener());
        wormNumberButtonPlus=new JButton("+");
        wormNumberButtonPlus.addActionListener(new WormNumberPlus());
        wormNumberButtonMinus=new JButton("-");
        wormNumberButtonMinus.addActionListener(new WormNumberMinus());
        goBackButton=new JButton("Go back to Settings Menu");
        goBackButton.addActionListener(new GoBackListener());

        timePanel.add(timeLabel);
        timePanel.add(timeTextField);
        timePanel.add(timeButtonPlus);
        timePanel.add(timeButtonMinus);
        timePanel.add(timeButtonPlus10);
        timePanel.add(timeButtonMinus10);

        wormNumberPanel.add(wormNumberLabel);
        wormNumberPanel.add(wormNumberTextField);
        wormNumberPanel.add(wormNumberButtonPlus);
        wormNumberPanel.add(wormNumberButtonMinus);

        this.add(timePanel);
        this.add(wormNumberPanel);
        this.add(goBackButton);
    }

    public int getRoundLength() {
        return Integer.parseInt(timeTextField.getText());
    }

    public int getWormNumber() {
        return Integer.parseInt(wormNumberTextField.getText());
    }

    class TimePlusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
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
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            int time=Integer.parseInt(GameSettingsPanel.this.timeTextField.getText());
            if(time<=0) {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(0));
            }
            else {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(time - 1));
            }
        }
    }

    class TimePlus10Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            int time=Integer.parseInt(GameSettingsPanel.this.timeTextField.getText());
            if(time>=99999) {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(99999));
            }
            else {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(time + 10));
            }
        }
    }

    class TimeMinus10Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            int time=Integer.parseInt(GameSettingsPanel.this.timeTextField.getText());
            if(time<=0) {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(0));
            }
            else {
                GameSettingsPanel.this.timeTextField.setText(String.valueOf(time - 10));
            }
        }
    }

    class WormNumberPlus implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            int time=Integer.parseInt(GameSettingsPanel.this.wormNumberTextField.getText());
            if(time>=9) {
                GameSettingsPanel.this.wormNumberTextField.setText(String.valueOf(9));
            }
            else {
                GameSettingsPanel.this.wormNumberTextField.setText(String.valueOf(time + 1));
            }
        }
    }

    class WormNumberMinus implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            int time=Integer.parseInt(GameSettingsPanel.this.wormNumberTextField.getText());
            if(time<=0) {
                GameSettingsPanel.this.wormNumberTextField.setText(String.valueOf(0));
            }
            else {
                GameSettingsPanel.this.wormNumberTextField.setText(String.valueOf(time - 1));
            }
        }
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	SoundEngine.playClick(mainPanel.options.getFxVolume());
            GameSettingsPanel.this.mainPanel.showPanel("3");//switch back to settings menu
        }
    }
}
