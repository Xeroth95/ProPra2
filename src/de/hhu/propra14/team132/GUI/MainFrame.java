package de.hhu.propra14.team132.GUI;

import javax.swing.*;

/**
 * Created by fabian on 02.05.14.
 */
public class MainFrame extends JFrame{
    MainPanel mainPanel;

    public MainFrame()
    {
        mainPanel=new MainPanel();

        this.setTitle("ProPra14 - Team 132");
        this.setSize(640, 480);
        this.setResizable(true);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
