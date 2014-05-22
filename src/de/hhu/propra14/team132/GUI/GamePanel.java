package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameSystem.*;
import de.hhu.propra14.team132.physics.util.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by fabian on 02.05.14.
 */
public class GamePanel extends JPanel {
    //this is where the action will take place
    //but for now there is not much to show

    MainFrame mainFrame;
    MainPanel mainPanel;
    MainGamePanel mainGamePanel;
    WeaponsPanel weaponsPanel;
    GameManager gameManager;
    JScrollBar hbar;
    JScrollBar vbar;
    Graphics2D g2d;
    GameObject[] gameObjects;
    ArrayList<Integer> objectIDs;
    Font displayFont;
    Thread mousePressedThread;
    Vector2D direction;
    float percentage;
    double mouseLocationX, mouseLocationY;
    boolean autoscrolling;
    boolean alreadySent;


    public GamePanel(MainFrame mainFrame, MainPanel mainPanel, MainGamePanel mainGamePanel, WeaponsPanel weaponsPanel, GameManager gameManager) {
        this.mainFrame = mainFrame;
        this.mainPanel = mainPanel;
        this.mainGamePanel = mainGamePanel;
        this.weaponsPanel = weaponsPanel;
        this.gameManager = gameManager;

        autoscrolling = false;
        alreadySent=false;
        percentage = 0;
        gameObjects = gameManager.gameMap.getMapObjects();
        objectIDs = gameManager.gameMap.getObjectIds();
        displayFont = new Font("Arial", Font.BOLD, 20);
        this.setPreferredSize(new Dimension(8192, 8192));
        this.setFocusable(true);
        this.addKeyListener(new GameKeyListener());
        this.addMouseListener(new GameMouseListener());
    }

    public void refresh() {
        this.gameObjects = gameManager.gameMap.getMapObjects();
        this.objectIDs = gameManager.gameMap.getObjectIds();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.requestFocus();
        hbar = mainGamePanel.scrollPane.getHorizontalScrollBar();
        vbar = mainGamePanel.scrollPane.getVerticalScrollBar();
        g2d = (Graphics2D) g;
        g2d.scale(1, -1);
        g2d.translate(0, -8192);


        for (int i : objectIDs) {
            gameObjects[i].draw(g2d, this);
        }

        g2d.scale(1, -1);
        g2d.translate(0, -8192);
        g2d.setColor(Color.RED);
        g2d.setFont(displayFont);
        g2d.drawString("Player " + String.valueOf(gameManager.gameMap.getCurrentPlayer().getPlayerID()), 0 + hbar.getValue(), 18 + vbar.getValue());
        g2d.drawString("Time left: " + (gameManager.gameMap.getTimeLeftInTicks() / gameManager.ticksPerSecond + 1), 0 + hbar.getValue(), 36 + vbar.getValue());
        if (percentage > 0) {
            g2d.drawString("Power: " + String.valueOf((int) (percentage * 100)) + "%", 0 + hbar.getValue(), 54 + vbar.getValue());
        }


        //get x and y coordinates of the moues relatively to MainGamePanel
        //MouseInfo.getPointerInfo().getLocation() returns a Point with the cursor position relatively to the screen
        //mainGamePanel.scrollPane.getViewport().getLocationOnScreen() returns a point with the position of scrollPane relatively to the screen
        //by subtracting the position of scrollPane from the cursorposition, we get the position of the cursor relatively to scrollPane
        mouseLocationX = MouseInfo.getPointerInfo().getLocation().getX() - mainGamePanel.scrollPane.getViewport().getLocationOnScreen().getX();
        mouseLocationY = MouseInfo.getPointerInfo().getLocation().getY() - mainGamePanel.scrollPane.getViewport().getLocationOnScreen().getY();

        if (!weaponsPanel.isVisible() && autoscrolling == false) {
            //only scroll if WeaponsPanel is invisivle and the panel is not autoscrolling
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

        autoscrolling = true;
        while (hbar.getValue() != targetX || vbar.getValue() != targetY) {
            if (hbar.getValue() < targetX) {
                hbar.setValue(hbar.getValue() + 1);//scroll right
                this.update(g2d);
            } else if (hbar.getValue() > targetX) {
                hbar.setValue(hbar.getValue() - 1);//scroll left
                this.update(g2d);
            }

            if (vbar.getValue() < targetY) {
                vbar.setValue(vbar.getValue() + 1);//scroll up
                this.update(g2d);
            } else if (vbar.getValue() > targetY) {
                vbar.setValue(vbar.getValue() - 1);//scroll down
                this.update(g2d);
            }
        }
        autoscrolling = false;
    }

    class GameMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == e.BUTTON3) {
                GamePanel.this.weaponsPanel.setVisible(!weaponsPanel.isVisible());
            }
        }


        public void mousePressed(MouseEvent e) {
            double wormPosX=GamePanel.this.gameManager.gameMap.getCurrentPlayer().getCurrentWorm().getPosition().getX();
            double wormPosY=GamePanel.this.gameManager.gameMap.getCurrentPlayer().getCurrentWorm().getPosition().getY();
            double mousePosX=e.getX();
            double mousePosY=8192-e.getY();
            GamePanel.this.direction=new Vector2D(mousePosX-wormPosX, wormPosY-mousePosY);
            try {
                direction.makeUnitVector();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println(wormPosX+" "+wormPosY);
            System.out.println(mousePosX+" "+mousePosY);
            if (e.getButton() == e.BUTTON1) {
                GamePanel.this.mousePressedThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int startedAtTick = GamePanel.this.gameManager.getCurrentTick();
                        int ticksPerSecond = GamePanel.this.gameManager.ticksPerSecond;
                        while (!mousePressedThread.isInterrupted()) {
                            int currentTick = GamePanel.this.gameManager.getCurrentTick();
                            GamePanel.this.percentage = (float) (currentTick - startedAtTick) / (float) 720;
                            if (GamePanel.this.percentage > 1) {
                                GamePanel.this.percentage = 1;
                            }
                            try {
                                GamePanel.this.mousePressedThread.sleep((long) (1000) / (long) (ticksPerSecond));
                            } catch (InterruptedException e) {
                                GamePanel.this.mousePressedThread.interrupt();
                            }
                        }
                    }
                });
                GamePanel.this.mousePressedThread.start();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == e.BUTTON1) {
                GamePanel.this.mousePressedThread.interrupt();
                GamePanel.this.gameManager.sendMessage(new ShootMessage(GamePanel.this.percentage, GamePanel.this.direction));
            }
            percentage = 0;
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                GamePanel.this.mainPanel.showPanel("11");
                gameManager.sendMessage(new StopMessage());
            } else if (GamePanel.this.mainPanel.options.getControls() == GamePanel.this.mainPanel.options.ARROWS && alreadySent==false) {
                alreadySent=true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.JUMP));
                        break;
                    case KeyEvent.VK_LEFT:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_LEFT));
                        break;
                    case KeyEvent.VK_RIGHT:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_RIGHT));
                        break;
                }

            } else if (GamePanel.this.mainPanel.options.getControls() == GamePanel.this.mainPanel.options.WASD && alreadySent==false) {
                alreadySent=true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.JUMP));
                        break;
                    case KeyEvent.VK_A:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_LEFT));
                        break;
                    case KeyEvent.VK_D:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_RIGHT));
                        break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (GamePanel.this.mainPanel.options.getControls() == GamePanel.this.mainPanel.options.ARROWS) {
                alreadySent=false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_LEFT_STOP));
                        break;
                    case KeyEvent.VK_RIGHT:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_RIGHT_STOP));
                        break;
                }
            }

            if (GamePanel.this.mainPanel.options.getControls() == GamePanel.this.mainPanel.options.WASD) {
                alreadySent=false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_LEFT_STOP));
                        break;
                    case KeyEvent.VK_D:
                        GamePanel.this.gameManager.sendMessage(new KeyboardMessage(KeyboardMessage.Command.MOVE_RIGHT_STOP));
                        break;
                }
            }
        }
    }
}
