package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 15.05.14.
 */
public class InGameMenuPanel extends JPanel {
    MainPanel mainPanel;
    GameManager gameManager;

    JButton buttonGoBack;

    public InGameMenuPanel(MainPanel mainPanel, GameManager gameManager) {
        this.mainPanel=mainPanel;
        this.gameManager=gameManager;

        this.setLayout(new GridLayout(1,1));

        buttonGoBack=new JButton("Go back to the Game");
        buttonGoBack.addActionListener(new GoBackListener());

        this.add(buttonGoBack);
    }

    class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InGameMenuPanel.this.mainPanel.showPanel("2");
            gameManager.setStopped(false);
        }
    }
}
