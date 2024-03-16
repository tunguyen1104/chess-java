package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonImage extends JLabel{
    public BufferedImage normal1;
    public BufferedImage selected1;
    private BufferedImage image;
    private int width;
    private int height;
    private JLabel title;
    public void setSelected(BufferedImage selected1) {
        this.selected1 = selected1;
    }
    public void setNormal(BufferedImage normal1) {
        this.normal1 = normal1;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public ButtonImage(final BufferedImage normal, final BufferedImage selected, int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.normal1 = normal;
        this.selected1 = selected;
        image = normal1;
        title = new JLabel(name, JLabel.CENTER);
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.CENTER);
        title.setForeground(Color.WHITE);
        try {
            title.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/res/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 16));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                image = selected1;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent me) {
                image = normal1;
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
