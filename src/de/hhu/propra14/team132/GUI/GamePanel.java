package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by fabian on 02.05.14.
 */
public class GamePanel extends JPanel {
    //this is where the action will take place
    //but for now there is not much to show

    MainPanel mainPanel;

    public GamePanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is the GamePanel. It will contain all the game graphics later on.", 0, this.getHeight()/2);
    }
}
