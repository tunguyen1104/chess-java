package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.ReadImage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class History extends JPanel {
    private JLabel title_bar_label;
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
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        this.setPreferredSize(new Dimension(1536, 864));
        title_bar_label = new JLabel("History");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        this.add(title_bar_label);
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
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
        test = new ButtonImage(history_normal, history_selected, 509, 72, "Mon Apr 22 15:06:30 2024    PvP   3 min   Black win");
        test.setBounds(500, 80, 509, 72);
        this.add(test);
        test.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.panelCardLayout.add(new Review(),"review");
                Menu.cardLayout.show(Menu.panelCardLayout,"review");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
    }
}
