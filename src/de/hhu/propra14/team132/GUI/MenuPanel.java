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
public class MenuPanel extends JPanel{
    //this panel contains the Main Menu of the game

    MainPanel mainPanel;
    SoundEngine soundEngine;
    File klickSoundFile;

    JButton newGameButton;
    JButton loadGameButton;
    JButton settingsButton;
    JButton aboutButton;
    JButton exitButton;
    JFileChooser chooser;
    String pathToSavedGame;

    public MenuPanel(MainPanel mainPanel, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        newGameButton=new JButton("New Game");
        newGameButton.addActionListener(new NewGameListener());
        loadGameButton=new JButton("Load Game");
        loadGameButton.addActionListener(new LoadGameListener());
        settingsButton=new JButton("Settings");
        settingsButton.addActionListener(new SettingsListener());
        aboutButton=new JButton("About");
        aboutButton.addActionListener(new AboutListener());
        exitButton=new JButton("Exit");
        exitButton.addActionListener(new ExitListener());

        this.setLayout(new GridLayout(5,1,10,10));

        this.add(newGameButton);
        this.add(loadGameButton);
        this.add(settingsButton);
        this.add(aboutButton);
        this.add(exitButton);
    }

    class NewGameListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.soundEngine.play(klickSoundFile);
            MenuPanel.this.mainPanel.showPanel("8");//switch to GamePanel
        }

    }

    class LoadGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.soundEngine.play(klickSoundFile);
            try {
                chooser=new JFileChooser();
                chooser.showOpenDialog(null);
                MenuPanel.this.pathToSavedGame = chooser.getSelectedFile().getPath();//open a filechooser dialog
            }
            catch(Exception ex) {
                if(pathToSavedGame==null) {
                    JOptionPane.showMessageDialog(null,"No file was chosen!", "Error", JOptionPane.ERROR_MESSAGE);//show an error if no file was chosen
                }
                else {
                    JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);//show an error message when something went wrong
                }
            }

        }

    }

    class SettingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.soundEngine.play(klickSoundFile);
            MenuPanel.this.mainPanel.showPanel("3");//switch to SettingsPanel
        }

    }

    class AboutListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.soundEngine.play(klickSoundFile);
            MenuPanel.this.mainPanel.showPanel("4");//switch to AboutPanel
        }

    }

    class ExitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.soundEngine.play(klickSoundFile);
            System.exit(0);
        }

    }
}