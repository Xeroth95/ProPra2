package de.hhu.propra14.team132.GUI;

import javax.swing.*;

/**
 * Created by fabian on 03.05.14.
 */
public class WeaponsPanel extends JPanel {
    MainPanel mainPanel;
    JButton weapon1;
    JButton weapon2;
    JButton weapon3;
    JButton weapon4;
    JButton weapon5;

    public WeaponsPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        weapon1=new JButton("Bazooka");
        weapon2=new JButton("Grenade");
        weapon3=new JButton("Mine");
        weapon4=new JButton("Punch");
        weapon5=new JButton("Super sheep");

        this.add(weapon1);
        this.add(weapon2);
        this.add(weapon3);
        this.add(weapon4);
        this.add(weapon5);

        this.setVisible(false);
    }
}
