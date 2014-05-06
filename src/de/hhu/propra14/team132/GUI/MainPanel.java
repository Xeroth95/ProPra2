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

    MainGamePanel mainGamePanel;
    MenuPanel menuPanel;
    SettingsPanel settingsPanel;
    AboutPanel aboutPanel;
    VideoSettingsPanel videoSettingsPanel;
    AudioSettingsPanel audioSettingsPanel;
    ControlSettingsPanel controlSettingsPanel;

    CardLayout mainPanelLayout;

    GameManager gameManager; //Instanz of GameManager
    public MainPanel(GameManager g) {
        //all other panels get this panel as parameter for their constructors
        //so that they can use the showPanel method to switch to another panel
        this.gameManager=g;
        mainGamePanel=new MainGamePanel(this);
        menuPanel=new MenuPanel(this);
        settingsPanel=new SettingsPanel(this);
        aboutPanel=new AboutPanel(this);
        videoSettingsPanel=new VideoSettingsPanel(this);
        audioSettingsPanel=new AudioSettingsPanel(this);
        controlSettingsPanel=new ControlSettingsPanel(this);

        mainPanelLayout=new CardLayout();

        this.setLayout(mainPanelLayout);

        //every panel gets ID-number, so that it can be identified
        //by the CardLayout mainPanelLayout
        this.add(menuPanel, "1");
        this.add(mainGamePanel, "2");
        this.add(settingsPanel, "3");
        this.add(aboutPanel, "4");
        this.add(videoSettingsPanel, "5");
        this.add(audioSettingsPanel, "6");
        this.add(controlSettingsPanel, "7");
    }

    public void showPanel(String ID)
    {
        this.mainPanelLayout.show(this, ID);
    }
}