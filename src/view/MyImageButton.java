package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImageButton extends JButton {
    private BufferedImage image;
    public MyImageButton(BufferedImage image, String name) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,42,42, this);
    }
}
