package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ButtonImage extends JLabel{
    private BufferedImage normal;
    private BufferedImage selected;
    private BufferedImage image;
    private int width;
    private int height;
    public ButtonImage(final BufferedImage normal, final BufferedImage selected,int width, int height) {
        this.width = width;
        this.height = height;
        this.normal = normal;
        this.selected = selected;
        image = normal;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                image = selected;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent me) {
                image = normal;
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,0,0,width,height,this);
    }
}
