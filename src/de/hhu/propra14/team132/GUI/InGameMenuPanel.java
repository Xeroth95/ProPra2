package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;
import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by fabian on 15.05.14.
 */
public class InGameMenuPanel extends JPanel {
    MainPanel mainPanel;
    GameManager gameManager;
    SoundEngine soundEngine;
    File klickSoundFile;

    JButton buttonNewGame;
    JButton buttonSaveGame;
    JButton buttonLoadGame;
    JButton buttonGoBack;
    JFileChooser chooser;
    String pathToSavedGame;


    public InGameMenuPanel(MainPanel mainPanel, GameManager gameManager, SoundEngine soundEngine, File klickSoundFile) {
        this.mainPanel=mainPanel;
        this.gameManager=gameManager;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        this.setLayout(new GridLayout(4,1,10,10));

        buttonNewGame=new JButton("New Game");
        buttonNewGame.addActionListener(new NewGameListener());
        buttonSaveGame=new JButton("Save Game");
        buttonSaveGame.addActionListener(new SaveGameListener());
        buttonLoadGame=new JButton("Load Game");
        buttonLoadGame.addActionListener(new LoadGameListener());
        buttonGoBack=new JButton("Go back to the Game");
        buttonGoBack.addActionListener(new GoBackListener());

        this.add(buttonNewGame);
        this.add(buttonSaveGame);
        this.add(buttonLoadGame);
        this.add(buttonGoBack);
    }

    class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InGameMenuPanel.this.soundEngine.play(klickSoundFile);
        }
    }

    class SaveGameListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filename=JOptionPane.showInputDialog("Please enter a name for the savegame:");
            InGameMenuPanel.this.gameManager.save("res/savegames/"+filename+".ser");
        }
    }

    class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InGameMenuPanel.this.soundEngine.play(klickSoundFile);
            try {
                chooser=new JFileChooser();
                chooser.showOpenDialog(null);
                InGameMenuPanel.this.pathToSavedGame = chooser.getSelectedFile().getPath();//open a filechooser dialog
                InGameMenuPanel.this.gameManager.load(pathToSavedGame);
                InGameMenuPanel.this.mainPanel.showPanel("2");
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

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InGameMenuPanel.this.soundEngine.play(klickSoundFile);
            InGameMenuPanel.this.mainPanel.showPanel("2");
            gameManager.setStopped(false);
        }
    }
}
