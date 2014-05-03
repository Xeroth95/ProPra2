package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by fabian on 03.05.14.
 */
public class MainGamePanel extends JPanel{
    MainPanel mainPanel;
    GamePanel gamePanel;
    WeaponsPanel weaponsPanel;
    JScrollPane scroll;

    public MainGamePanel(MainPanel mainPanel) {
        //this panel contains all in-game related panels
        //for now that would be scroll(GamePanel) and WeaponsPanel
        this.mainPanel=mainPanel;

        weaponsPanel=new WeaponsPanel(mainPanel);
        gamePanel=new GamePanel(mainPanel, this, weaponsPanel);
        scroll=new JScrollPane(gamePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//we just need a horizontal scrollbar
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));//make that ugly scrollbar invisible
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));//and the other one too

        this.setLayout(new BorderLayout());

        this.add(scroll, BorderLayout.CENTER);
        this.add(weaponsPanel, BorderLayout.SOUTH);
    }
}
