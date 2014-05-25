package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.sound.SoundEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

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

    public MainGamePanel(MainFrame mainFrame, MainPanel mainPanel, GameManager gameManager, SoundEngine soundEngine, File klickSoundFile) {
        //this panel contains all in-game related panels
        //for now that would be scrollPane(GamePanel) and WeaponsPanel
        this.mainPanel=mainPanel;
        this.soundEngine=soundEngine;
        this.klickSoundFile=klickSoundFile;

        weaponsPanel=new WeaponsPanel(mainPanel, gameManager, soundEngine, klickSoundFile);
        gamePanel=new GamePanel(mainFrame, mainPanel, this, weaponsPanel, gameManager);
        scrollPane=new JScrollPane(gamePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

        this.setLayout(new BorderLayout());

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);

        this.scrollPane.getActionMap().put("unitScrollUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.scrollPane.getActionMap().put("unitScrollDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.scrollPane.getActionMap().put("unitScrollRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.scrollPane.getActionMap().put("unitScrollLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
