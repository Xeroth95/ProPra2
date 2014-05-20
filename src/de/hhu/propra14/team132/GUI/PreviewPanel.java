package de.hhu.propra14.team132.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by fabian on 20.05.14.
 */
public class PreviewPanel extends JPanel {
    BeforeGamePanel beforeGamePanel;

    Image imagePlayer1;
    Image imagePlayer2;
    JLabel labelPlayer1;
    JLabel labelPlayer2;

    public PreviewPanel(BeforeGamePanel beforeGamePanel) {
        this.beforeGamePanel=beforeGamePanel;

        this.setLayout(new GridLayout(1,2));

        labelPlayer1=new JLabel();
        labelPlayer2=new JLabel();
        refreshPreview();
        this.add(labelPlayer1);
        this.add(labelPlayer2);
    }

    public void refreshPreview() {
        try {
            imagePlayer1=ImageIO.read(new File("res/img/textures/" + (String) beforeGamePanel.player1ColorBox.getSelectedItem() + "Worm.png"));
            imagePlayer1=imagePlayer1.getScaledInstance(200,200, Image.SCALE_SMOOTH);
            imagePlayer2=ImageIO.read(new File("res/img/textures/" + (String) beforeGamePanel.player2ColorBox.getSelectedItem() + "Worm.png"));
            imagePlayer2=imagePlayer2.getScaledInstance(200,200, Image.SCALE_SMOOTH);
            labelPlayer1.setIcon(new ImageIcon(imagePlayer1));
            labelPlayer2.setIcon(new ImageIcon(imagePlayer2));
        }
        catch (Exception e) {
            System.err.println("Error loading preview");
            e.printStackTrace();
        }
    }
}
