package de.hhu.propra14.team132.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fabian on 06.05.14.
 */
public abstract interface Drawable {
    //every object that wants to be drawn on the GamePanel has to implement this interface
    public abstract void draw(Graphics2D g2d, JPanel p);
}