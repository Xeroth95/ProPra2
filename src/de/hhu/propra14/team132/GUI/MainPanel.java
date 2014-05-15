package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by fabian on 02.05.14.
 */
public class MainPanel extends JPanel {
    //the MainPanel contains all other panels
    //and because it uses a CardLayout it is easy to switch between all these panels

    public MainGamePanel mainGamePanel;
    MenuPanel menuPanel;
    SettingsPanel settingsPanel;
    AboutPanel aboutPanel;
    VideoSettingsPanel videoSettingsPanel;
    AudioSettingsPanel audioSettingsPanel;
    ControlSettingsPanel controlSettingsPanel;
    GameSettingsPanel gameSettingsPanel;
    StartGamePanel startGamePanel;
    LobbyPanel lobbyPanel;

    CardLayout mainPanelLayout;

    Options options;
    File optionsFile;

    public MainPanel(MainFrame mainFrame, GameManager gameManager) throws IOException {
        loadOptions();

        //all other panels get this panel as parameter for their constructors
        //so that they can use the showPanel method to switch to another panel
        mainGamePanel=new MainGamePanel(mainFrame, this, gameManager);
        menuPanel=new MenuPanel(this);
        settingsPanel=new SettingsPanel(this, options);
        aboutPanel=new AboutPanel(this);
        videoSettingsPanel=new VideoSettingsPanel(this);
        audioSettingsPanel=new AudioSettingsPanel(this);
        controlSettingsPanel=new ControlSettingsPanel(this);
        gameSettingsPanel=new GameSettingsPanel(this);
        startGamePanel=new StartGamePanel(this);
        lobbyPanel=new LobbyPanel(this);
        applyOptions();

        mainPanelLayout=new CardLayout();

        this.setLayout(mainPanelLayout);

        //every panel gets an ID-number, so that it can be identified
        //by the CardLayout mainPanelLayout
        this.add(menuPanel, "1");
        this.add(mainGamePanel, "2");
        this.add(settingsPanel, "3");
        this.add(aboutPanel, "4");
        this.add(videoSettingsPanel, "5");
        this.add(audioSettingsPanel, "6");
        this.add(controlSettingsPanel, "7");
        this.add(startGamePanel, "8");
        this.add(lobbyPanel, "9");
        this.add(gameSettingsPanel, "10");
    }

    public void loadOptions() {
        //check if "resources/options/options.ser" exists, if yes, deserialize it, if not, create standard optionsobject
        optionsFile=new File("resources/options/options.ser");
        if(optionsFile.exists()) {
            //if an optionsfile already exists, load it
            try {
                FileInputStream fileIn = new FileInputStream(optionsFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                options = (Options) objectIn.readObject();
            }
            catch(Exception e) {
                System.err.println("Error loading Options!");
                e.printStackTrace();
            }

        }
        else {
            //if no optionsfile exists, create one
            try {
                optionsFile.getParentFile().mkdirs();
                optionsFile.createNewFile();
                options = new Options();
                options.setStandard();
                options.save();
            }
            catch (Exception e) {
                System.err.println("Error creating File!");
                e.printStackTrace();
            }
        }

    }

    public void applyOptions() {
        audioSettingsPanel.bgVolumeSlider.setValue(options.getBgVolume());
        audioSettingsPanel.fxVolumeSlider.setValue(options.getFxVolume());
        if(options.getControls()==options.ARROWS) {
            controlSettingsPanel.groupMoveControls.setSelected(controlSettingsPanel.buttonArrowControls.getModel(), true);
            controlSettingsPanel.groupMoveControls.setSelected(controlSettingsPanel.buttonWasdControls.getModel(), false);
        }
        if(options.getControls()==options.WASD) {
            controlSettingsPanel.groupMoveControls.setSelected(controlSettingsPanel.buttonWasdControls.getModel(), true);
            controlSettingsPanel.groupMoveControls.setSelected(controlSettingsPanel.buttonArrowControls.getModel(), false);
        }
        gameSettingsPanel.timeTextField.setText(String.valueOf(options.getRoundLength()));
        gameSettingsPanel.wormNumberTextField.setText(String.valueOf(options.getWormsNumber()));
    }

    public void showPanel(String ID)
    {
        this.mainPanelLayout.show(this, ID);
    }
}