package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel implements ActionListener {
    private JFrame frame;
    private JLabel name_title;
    BufferedImage icon_title;
    private BufferedImage menu_normal;
    private BeautifyButton newgame_menu;
    private BeautifyButton history_menu;
    private BeautifyButton puzzles_menu;
    private BeautifyButton settings_menu;
    private BeautifyButton about_menu;
    private BeautifyButton exit_menu;
    public Menu() {
        frame = new JFrame("CHESS");
        try {
            icon_title = ImageIO.read(new File("src/res/gui/icon_title.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-6,0);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
    public void initPanel() {
        this.setPreferredSize(new Dimension(1600,860));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        name_title = new JLabel("CHESS GAME");
        name_title.setBounds(600,240,400,50);
        name_title.setForeground(Color.WHITE);
        name_title.setFocusable(false);
        name_title.setFont(name_title.getFont().deriveFont(50.0f));
        newgame_menu = new BeautifyButton("New Game");
        newgame_menu.setBounds(574,378,170,52);
        newgame_menu.setForeground(Color.WHITE);
        newgame_menu.setFocusable(false);
        newgame_menu.setFont(newgame_menu.getFont().deriveFont(20.0f));
        history_menu = new BeautifyButton("History");
        history_menu.setBounds(794,378,170,52);
        history_menu.setForeground(Color.WHITE);
        history_menu.setFocusable(false);
        history_menu.setFont(history_menu.getFont().deriveFont(20.0f));
        puzzles_menu = new BeautifyButton("Puzzles");
        puzzles_menu.setBounds(574,458,170,52);
        puzzles_menu.setForeground(Color.WHITE);
        puzzles_menu.setFocusable(false);
        puzzles_menu.setFont(puzzles_menu.getFont().deriveFont(20.0f));
        about_menu = new BeautifyButton("About");
        about_menu.setBounds(794,458,170,52);
        about_menu.setForeground(Color.WHITE);
        about_menu.setFocusable(false);
        about_menu.setFont(about_menu.getFont().deriveFont(20.0f));
        settings_menu = new BeautifyButton("Settings");
        settings_menu.setBounds(574,538,170,52);
        settings_menu.setForeground(Color.WHITE);
        settings_menu.setFocusable(false);
        settings_menu.setFont(settings_menu.getFont().deriveFont(20.0f));
        exit_menu = new BeautifyButton("Exit");
        exit_menu.setBounds(794,538,170,52);
        exit_menu.setForeground(Color.WHITE);
        exit_menu.setFocusable(false);
        exit_menu.setFont(exit_menu.getFont().deriveFont(20.0f));
        newgame_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new GameOptions();
            }
        });
        history_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        puzzles_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        settings_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new Setting();
            }
        });
        about_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        exit_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
        newgame_menu.setHorizontalAlignment(SwingConstants.CENTER); // Đặt căn giữa ngang
        history_menu.setHorizontalAlignment(SwingConstants.CENTER);
        puzzles_menu.setHorizontalAlignment(SwingConstants.CENTER);
        settings_menu.setHorizontalAlignment(SwingConstants.CENTER);
        about_menu.setHorizontalAlignment(SwingConstants.CENTER);
        exit_menu.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(newgame_menu);
        this.add(history_menu);
        this.add(puzzles_menu);
        this.add(settings_menu);
        this.add(exit_menu);
        this.add(about_menu);
        this.add(name_title);
    }
    public static void main(String[] args) {
        new Menu();
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(icon_title,670,28,190,180,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
