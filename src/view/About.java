package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.JDBCConnection;
import model.ReadImage;
import model.Sound;

public class About extends JPanel {
    private JLabel title_bar_label;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private JTextArea about_game;
    public static Sound sound = new Sound();
    public static boolean music = false;
    public static void changeSoundBackGround(int index) {
        if (index == 0 && music == false) {
            music = true;
            sound.playBackGround();
        } else if(index == 1 && music == true){
            music = false;
            sound.stop();
        }
    }
    public About() {
        if (JDBCConnection.takeDataSetting().get(2).equals("1")) {
            music = true;
            sound.playBackGround();
        } else {
            music = false;
        }
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        title_bar_label = new JLabel("About");
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
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        about_game = new JTextArea();
        about_game.setText(
                "Author: The Tai - 221230980 & Tu Nguyen - 221231028.\n\nThis is a Project using Java. The project is a basic chess\ngame with GUI, that help player learn to play chess.\nThe game also have a chess engine that can play against\nthe player, and analyze the board, it can also be used to\nplay your friends. The game provides tutorials, chess puzzles.\n\nThe app uses Java swing for graphic display.\n\nThe development for this app is from Mar 5, 2024.");
        try {
            about_game.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/Inter-Regular.ttf")).deriveFont(Font.PLAIN, 16));
        } catch (FontFormatException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        about_game.setForeground(Color.WHITE);
        about_game.setBackground(new Color(55, 55, 55));
        about_game.setBounds(520, 220, 480, 380);
        about_game.setEditable(false);
        this.add(about_game);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(ReadImage.game_options_panel, 500, 180, 509, 420, this);
    }
}
