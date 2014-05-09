package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;

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
    StartGamePanel startGamePanel;
    LobbyPanel lobbyPanel;

    CardLayout mainPanelLayout;

    public MainPanel(MainFrame mainFrame, GameManager gameManager) {
        //all other panels get this panel as parameter for their constructors
        //so that they can use the showPanel method to switch to another panel
        mainGamePanel=new MainGamePanel(mainFrame, this, gameManager);
        menuPanel=new MenuPanel(this);
        settingsPanel=new SettingsPanel(this);
        aboutPanel=new AboutPanel(this);
        videoSettingsPanel=new VideoSettingsPanel(this);
        audioSettingsPanel=new AudioSettingsPanel(this);
        controlSettingsPanel=new ControlSettingsPanel(this);
        startGamePanel=new StartGamePanel(this);
        lobbyPanel=new LobbyPanel(this);

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
    }

    public void showPanel(String ID)
    {
        this.mainPanelLayout.show(this, ID);
    }
}