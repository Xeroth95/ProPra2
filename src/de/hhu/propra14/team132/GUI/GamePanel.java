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
    MainGamePanel mainGamePanel;
    WeaponsPanel weaponsPanel;
    JScrollBar hbar;
    JScrollBar vbar;
    double mouseLocationX, mouseLocationY;

    public GamePanel(MainPanel mainPanel, MainGamePanel mainGamePanel, WeaponsPanel weaponsPanel) {
        this.mainPanel=mainPanel;
        this.mainGamePanel=mainGamePanel;
        this.weaponsPanel=weaponsPanel;

        this.setPreferredSize(new Dimension(1500, 1500));

        this.addMouseListener(new GameMouseListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw some rects and a string for testing purposes
        g.drawString("This is the GamePanel. It will contain all the game graphics later on. This is a very long string to test the scrolling functionality. Later on in the developement it will be replaced by cool graphics and stuff.", 0, 100);
        g.fillRect(100,150,200,200);
        g.fillRect(300,500,200,200);
        g.fillRect(600,700,200,200);
        g.fillRect(700,300,200,200);
        g.fillRect(1000,500,200,200);
        g.fillRect(1200,1000,200,200);
        g.fillRect(1100,150,200,200);
        g.fillRect(350,200,200,200);
        g.fillRect(100,1200,200,200);
        g.fillRect(500,1100,200,200);

        hbar = mainGamePanel.scroll.getHorizontalScrollBar();
        vbar = mainGamePanel.scroll.getVerticalScrollBar();

        //get x and y coordinates of the moues relatively to MainGamePanel
        mouseLocationX = MouseInfo.getPointerInfo().getLocation().getX()-mainGamePanel.scroll.getViewport().getLocationOnScreen().getX();
        mouseLocationY = MouseInfo.getPointerInfo().getLocation().getY()-mainGamePanel.scroll.getViewport().getLocationOnScreen().getY();

        if(!weaponsPanel.isVisible())//only scroll if WeaponsPanel is not visible
         {
            if (mouseLocationX >= mainGamePanel.scroll.getViewport().getWidth() - 50) {
                hbar.setValue(hbar.getValue() + 1);//scroll to the right
            }
            if (mouseLocationX <= 50) {
                hbar.setValue(hbar.getValue() - 1);//scroll to the left
            }

            if (mouseLocationY >= mainGamePanel.scroll.getViewport().getHeight() - 50) {
                vbar.setValue(vbar.getValue() + 1);//scroll down
            }
            if (mouseLocationY <= 50) {
                vbar.setValue(vbar.getValue() - 1);//scroll up
            }
        }

        this.repaint();
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
