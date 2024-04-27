package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ButtonImage extends JLabel{
    public Image normal1;
    public Image selected1;
    private Image image;
    private int width;
    private int height;
    private JLabel title;
    public void setSelected(Image selected1) {
        this.selected1 = selected1;
    }
    public void setNormal(Image normal1) {
        this.normal1 = normal1;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public ButtonImage(final Image normal, final Image selected, int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.normal1 = normal;
        this.selected1 = selected;
        image = normal1;
        title = new JLabel(name, JLabel.CENTER);
        this.setLayout(new BorderLayout());
        this.add(title);
        title.setForeground(Color.WHITE);
        try {
            title.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 16));
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
    public ButtonImage(final Image normal, final Image selected, int width, int height, String name, String history) {
        this.width = width;
        this.height = height;
        this.normal1 = normal;
        this.selected1 = selected;
        image = normal1;
        title = new JLabel(name + "\n", JLabel.LEFT);
        title.setBounds(40, 16, 280, 20);
        JLabel titlev2 = new JLabel(history, JLabel.LEFT);
        titlev2.setForeground(Color.WHITE);
        titlev2.setBounds(40, 42, 500, 20);
        title.setForeground(Color.WHITE);
        this.add(title);
        this.add(titlev2);
        try {
            title.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 16));
            titlev2.setFont(Font.createFont(Font.TRUETYPE_FONT,
            new File("resources/fonts/Inter-Regular.ttf")).deriveFont(Font.PLAIN, 16));
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
