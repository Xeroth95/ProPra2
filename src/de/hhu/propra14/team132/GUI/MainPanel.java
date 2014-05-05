package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fabian on 02.05.14.
 */
public class MainPanel extends JPanel {
    GamePanel gamePanel;
    MenuPanel menuPanel;
    SettingsPanel settingsPanel;
    AboutPanel aboutPanel;
    VideoSettingsPanel videoSettingsPanel;
    AudioSettingsPanel audioSettingsPanel;
    ControlSettingsPanel controlSettingsPanel;

    CardLayout mainPanelLayout;

    public MainPanel() {
        gamePanel=new GamePanel(this);
        menuPanel=new MenuPanel(this);
        settingsPanel=new SettingsPanel(this);
        aboutPanel=new AboutPanel(this);
        videoSettingsPanel=new VideoSettingsPanel(this);
        audioSettingsPanel=new AudioSettingsPanel(this);
        controlSettingsPanel=new ControlSettingsPanel(this);

        mainPanelLayout=new CardLayout();

        this.setLayout(mainPanelLayout);

        this.add(menuPanel, "1");
        this.add(gamePanel, "2");
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