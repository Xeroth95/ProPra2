package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.sound.SoundEngine;
import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by fabian on 03.05.14.
 */
public class MainGamePanel extends JPanel{
    MainPanel mainPanel;
    public GamePanel gamePanel;
    WeaponsPanel weaponsPanel;
    JScrollPane scrollPane;
    SoundEngine soundEngine;
    File klickSoundFile;

    public MainGamePanel(MainFrame mainFrame, MainPanel mainPanel, GameManager gameManager, SoundEngine soundEngine, File klickSoundFile) throws IOException {
        //this panel contains all in-game related panels
        //for now that would be scrollPane(GamePanel) and WeaponsPanel
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        weaponsPanel=new WeaponsPanel(mainPanel, soundEngine, klickSoundFile);
        gamePanel=new GamePanel(mainFrame, mainPanel, this, weaponsPanel, gameManager);
        scrollPane=new JScrollPane(gamePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

        this.setLayout(new BorderLayout());

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);
    }
}
