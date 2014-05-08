package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.gameSystem.GameManager;

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

    //MainFrame mainFrame;
    MainPanel mainPanel;
    MainGamePanel mainGamePanel;
    WeaponsPanel weaponsPanel;
    JScrollBar hbar;
    JScrollBar vbar;
    double mouseLocationX, mouseLocationY;
    boolean autoscrolling;
    GameManager gameManager;

    public GamePanel(MainPanel mainPanel, MainGamePanel mainGamePanel, WeaponsPanel weaponsPanel, GameManager gameManager) {
        //this.mainFrame=mainFrame;
        this.mainPanel=mainPanel;
        this.mainGamePanel=mainGamePanel;
        this.weaponsPanel=weaponsPanel;
        this.gameManager=gameManager;

        autoscrolling=false;
        this.setPreferredSize(new Dimension((int)Math.round(gameManager.terrain.shape.getMaxOnX()), (int)Math.round(gameManager.terrain.shape.getMaxOnY())));//scrollPane needs to know the size of the panel it scrolls
        this.addMouseListener(new GameMouseListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameManager.terrain.draw(this.getGraphics());
        //draw team1
        gameManager.worm1_1.draw(this.getGraphics(),100,438);
        gameManager.worm1_2.draw(this.getGraphics(),120,432);
        gameManager.worm1_3.draw(this.getGraphics(),132,428);
        gameManager.worm1_4.draw(this.getGraphics(),150,422);
        //draw team2
        gameManager.worm2_1.draw(this.getGraphics(),763,306);
        gameManager.worm2_2.draw(this.getGraphics(),750,300);
        gameManager.worm2_3.draw(this.getGraphics(),735,293);
        gameManager.worm2_4.draw(this.getGraphics(),722,287);

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

        this.repaint();
    }

    public void scroll(JScrollBar hbar, JScrollBar vbar, int targetX, int targetY) {
        //this method makes the scrollbars scroll to a certain position, which is defined by targetX and targetY

        autoscrolling=true;
        while(hbar.getValue() != targetX || vbar.getValue() != targetY) {
            if(hbar.getValue() < targetX) {
                hbar.setValue(hbar.getValue() + 1);//scroll right
                this.update(this.getGraphics());
            }
            else if(hbar.getValue() > targetX) {
                hbar.setValue(hbar.getValue() - 1);//scroll left
                this.update(this.getGraphics());
            }

            if(vbar.getValue() < targetY) {
                vbar.setValue(vbar.getValue() + 1);//scroll up
                this.update(this.getGraphics());
            }
            else if(vbar.getValue() > targetY) {
                vbar.setValue(vbar.getValue() - 1);//scroll down
                this.update(this.getGraphics());
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
