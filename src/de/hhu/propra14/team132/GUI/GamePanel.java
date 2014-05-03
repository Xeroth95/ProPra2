package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by fabian on 02.05.14.
 */
public class GamePanel extends JPanel {
    //this is where the action will take place
    //but for now there is not much to show

    MainPanel mainPanel;
    WeaponsPanel weaponsPanel;

    public GamePanel(MainPanel mainPanel, WeaponsPanel weaponsPanel) {
        this.mainPanel=mainPanel;
        this.weaponsPanel=weaponsPanel;

        this.addMouseListener(new GameMouseListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is the GamePanel. It will contain all the game graphics later on.", 0, this.getHeight()/2);
    }

    class GameMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if(e.getButton()==e.BUTTON3) {
                GamePanel.this.weaponsPanel.setVisible(!weaponsPanel.isVisible());
            }
        }


        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }
}
