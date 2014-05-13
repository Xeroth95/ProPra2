package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameObjects.Obstacle;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by fabian on 02.05.14.
 */
public class GamePanel extends JPanel {
    //this is where the action will take place
    //but for now there is not much to show

    MainPanel mainPanel;
    MainGamePanel mainGamePanel;
    WeaponsPanel weaponsPanel;
    GameManager gameManager;
    JScrollBar hbar;
    JScrollBar vbar;
    Graphics2D g2d;
    GameObject[] gameObjects;
    ArrayList<Integer> objectIDs;
    double mouseLocationX, mouseLocationY;
    boolean autoscrolling;


    public GamePanel(MainFrame mainFrame, MainPanel mainPanel, MainGamePanel mainGamePanel, WeaponsPanel weaponsPanel, GameManager gameManager) throws IOException {
        //this.mainFrame=mainFrame;
        this.mainPanel=mainPanel;
        this.mainGamePanel=mainGamePanel;
        this.weaponsPanel=weaponsPanel;
        this.gameManager=gameManager;

        autoscrolling=false;
        gameObjects=gameManager.gameMap.getMapObjects();
        objectIDs=gameManager.gameMap.getObjectIds();
        this.setPreferredSize(new Dimension(8192, 8192));
        this.addMouseListener(new GameMouseListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d=(Graphics2D) g;

        for(int i : objectIDs) {
            gameObjects[i].draw(g2d);
        }

        hbar = mainGamePanel.scrollPane.getHorizontalScrollBar();
        vbar = mainGamePanel.scrollPane.getVerticalScrollBar();

        //get x and y coordinates of the moues relatively to MainGamePanel
        //MouseInfo.getPointerInfo().getLocation() returns a Point with the cursor position relatively to the screen
        //mainGamePanel.scrollPane.getViewport().getLocationOnScreen() returns a point with the position of scrollPane relatively to the screen
        //by subtracting the position of scrollPane from the cursorposition, we get the position of the cursor relatively to scrollPane
        mouseLocationX = MouseInfo.getPointerInfo().getLocation().getX()-mainGamePanel.scrollPane.getViewport().getLocationOnScreen().getX();
        mouseLocationY = MouseInfo.getPointerInfo().getLocation().getY()-mainGamePanel.scrollPane.getViewport().getLocationOnScreen().getY();

        if(!weaponsPanel.isVisible() && autoscrolling==false)//only scroll if WeaponsPanel is invisivle and the panel is not autoscrolling
        {
            if (mouseLocationX >= mainGamePanel.scrollPane.getViewport().getWidth() - 50) {
                hbar.setValue(hbar.getValue() + 1);//scroll to the right
            }
            if (mouseLocationX <= 50) {
                hbar.setValue(hbar.getValue() - 1);//scroll to the left
            }

            if (mouseLocationY >= mainGamePanel.scrollPane.getViewport().getHeight() - 50) {
                vbar.setValue(vbar.getValue() + 1);//scroll down
            }
            if (mouseLocationY <= 50) {
                vbar.setValue(vbar.getValue() - 1);//scroll up
            }
        }
    }

    public void nextTick() {
        //this method is called by the GameManager, so that the panel is repainted every tick
        this.repaint();
    }

    public void scroll(JScrollBar hbar, JScrollBar vbar, int targetX, int targetY) {
        //this method makes the scrollbars scroll to a certain position, which is defined by targetX and targetY

        autoscrolling=true;
        while(hbar.getValue() != targetX || vbar.getValue() != targetY) {
            if(hbar.getValue() < targetX) {
                hbar.setValue(hbar.getValue() + 1);//scroll right
                this.update(g2d);
            }
            else if(hbar.getValue() > targetX) {
                hbar.setValue(hbar.getValue() - 1);//scroll left
                this.update(g2d);
            }

            if(vbar.getValue() < targetY) {
                vbar.setValue(vbar.getValue() + 1);//scroll up
                this.update(g2d);
            }
            else if(vbar.getValue() > targetY) {
                vbar.setValue(vbar.getValue() - 1);//scroll down
                this.update(g2d);
            }
        }
        autoscrolling=false;
    }

    class GameMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            //show weaponsPanel when the right mouse button has been clicked
            if(e.getButton()==e.BUTTON3) {
                GamePanel.this.weaponsPanel.setVisible(!weaponsPanel.isVisible());
            }

            if(e.getButton()==e.BUTTON1) {
                //scroll to upper left corner (for testing purposes)
                GamePanel.this.scroll(GamePanel.this.mainGamePanel.scrollPane.getHorizontalScrollBar(), GamePanel.this.mainGamePanel.scrollPane.getVerticalScrollBar(), 0, 0);
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
