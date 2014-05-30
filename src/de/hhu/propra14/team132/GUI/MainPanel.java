package de.hhu.propra14.team132.GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.io.*;

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
    InGameMenuPanel inGameMenuPanel;
    BeforeGamePanel beforeGamePanel;
    BeforeNetworkHostGamePanel beforeNetworkHostGamePanel;
    BeforeNetworkClientGamePanel beforeNetworkClientGamePanel;

    CardLayout mainPanelLayout;

    public Options options;
    File optionsFile;
    FileWriter optionsFileWriter;
    Gson gson;

    GameManager manager;
    
    public MainPanel(MainFrame mainFrame, GameManager gameManager) {
        loadOptions();
        
        manager = gameManager;

        //all other panels get this panel as parameter for their constructors
        //so that they can use the showPanel method to switch to another panel
        mainGamePanel=new MainGamePanel(mainFrame, this, gameManager);
        menuPanel=new MenuPanel(gameManager, this);
        settingsPanel=new SettingsPanel(this, options);
        aboutPanel=new AboutPanel(this);
        videoSettingsPanel=new VideoSettingsPanel(this);
        audioSettingsPanel=new AudioSettingsPanel(this);
        controlSettingsPanel=new ControlSettingsPanel(this);
        gameSettingsPanel=new GameSettingsPanel(this);
        startGamePanel=new StartGamePanel(this);
        lobbyPanel=new LobbyPanel(this);
        inGameMenuPanel=new InGameMenuPanel(this, gameManager);
        beforeGamePanel=new BeforeGamePanel(this, gameManager, options);
        beforeNetworkHostGamePanel=new BeforeNetworkHostGamePanel(this, gameManager, options);
        beforeNetworkClientGamePanel=new BeforeNetworkClientGamePanel(this, gameManager, options);

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
        this.add(inGameMenuPanel, "11");
        this.add(beforeGamePanel, "12");
        this.add(beforeNetworkHostGamePanel, "13");
        this.add(beforeNetworkClientGamePanel, "14");
    }

    public void saveOptions() {
        gson=new GsonBuilder().setPrettyPrinting().create();
        try {
            optionsFileWriter=new FileWriter("res/options/options.json");
            gson.toJson(options, optionsFileWriter);
            optionsFileWriter.close();
        }
        catch(Exception e) {
            System.err.println("Error saving Options!");
            e.printStackTrace();
        }
    }

    public void loadOptions() {
        //check if "resources/options/options.ser" exists, if yes, deserialize it, if not, create standard optionsobject
        optionsFile=new File("res/options/options.json");
        if(optionsFile.exists()) {
            //if an optionsfile already exists, load it
            try {
                gson=new Gson();
                BufferedReader optionsReader=new BufferedReader(new FileReader(optionsFile));
                options=gson.fromJson(optionsReader, Options.class);
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
                saveOptions();
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
        gameSettingsPanel.wormNumberTextField.setText(String.valueOf(options.getWormsPerTeam()));
    }

    public void showPanel(String ID)
    {
        this.mainPanelLayout.show(this, ID);
    }
}