package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class History extends JPanel {
    private JLabel title_bar_label;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage home_normal;
    private BufferedImage back_selected;
    private BufferedImage home_selected;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private BufferedImage history_normal;
    private BufferedImage history_selected;
    private ButtonImage test;

    public History() {
        initPanel();
    }

    public void initPanel() {
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        try {
            history_normal = ImageIO.read(new File("resources/buttons/history_normal.png"));
            history_selected = ImageIO.read(new File("resources/buttons/history_selected.png"));
            title_bar = ImageIO.read(new File("resources/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("resources/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("resources/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("resources/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("resources/buttons/home_selected.png"));
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        this.setPreferredSize(new Dimension(1600, 1000));
        title_bar_label = new JLabel("History");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        back_normal_button = new ButtonImage(back_normal, back_selected, 42, 42, "");
        home_normal_button = new ButtonImage(home_normal, home_selected, 42, 42, "");
        back_normal_button.setBounds(465, 10, 42, 42);
        home_normal_button.setBounds(1000, 10, 42, 42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        test = new ButtonImage(history_normal, history_selected, 580, 70, "Day");
        test.setBounds(462, 80, 580, 70);
        this.add(test);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(title_bar, 530, 10, 450, 42, this);
    }
}
