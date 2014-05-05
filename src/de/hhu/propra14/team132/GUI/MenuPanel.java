package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabian on 02.05.14.
 */
public class MenuPanel extends JPanel{
    MainPanel mainPanel;
    CardLayout mainPanelLayout;

    JButton button_new_game;
    JButton button_load_game;
    JButton button_settings;
    JButton button_about;
    JButton button_exit;

    String path_to_saved_game;

    JFileChooser chooser;

    public MenuPanel(MainPanel mainPanel) {
        this.mainPanel=mainPanel;

        button_new_game=new JButton("New Game");
        button_new_game.addActionListener(new NewGameListener());
        button_load_game=new JButton("Load Game");
        button_load_game.addActionListener(new LoadGameListener());
        button_settings=new JButton("Settings");
        button_settings.addActionListener(new SettingsListener());
        button_about=new JButton("About");
        button_about.addActionListener(new AboutListener());
        button_exit=new JButton("Exit");
        button_exit.addActionListener(new ExitListener());

        chooser=new JFileChooser();

        this.setLayout(new GridLayout(5,1,10,10));

        this.add(button_new_game);
        this.add(button_load_game);
        this.add(button_settings);
        this.add(button_about);
        this.add(button_exit);
    }

    class NewGameListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.mainPanel.showPanel("2");
        }

    }

    class LoadGameListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            chooser.showOpenDialog(null);
            MenuPanel.this.path_to_saved_game=chooser.getSelectedFile().getPath();
        }

    }

    class SettingsListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.mainPanel.showPanel("3");
        }

    }

    class AboutListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            MenuPanel.this.mainPanel.showPanel("4");
        }

    }

    class ExitListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }
}
