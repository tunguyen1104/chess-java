package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.ReadImage;

import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel implements ActionListener {
    public static JFrame frame;
    private JLabel name_title;
    public Image icon_title;
    private Image icon_game;
    private BeautifyButton newgame_menu;
    private BeautifyButton history_menu;
    private BeautifyButton puzzles_menu;
    private BeautifyButton settings_menu;
    private BeautifyButton about_menu;
    private BeautifyButton exit_menu;
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel panelCardLayout = new JPanel();
    private ReadImage readImage = new ReadImage();

    public Menu() {
        frame = new JFrame("CHESS");
        panelCardLayout.setLayout(cardLayout);
        try {
            icon_title = ImageIO.read(new File("resources/gui/icon_title.png"));
            icon_game = ImageIO.read(new File("resources/gui/icon_game.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(icon_game);
        initPanel();
        panelCardLayout.add(this, "menu");
        panelCardLayout.add(new Setting(), "setting");
        panelCardLayout.add(new ListPuzzle(), "listPuzzle");
        panelCardLayout.add(new GameOptions(), "gameOptions");
        panelCardLayout.add(new History(), "history");
        panelCardLayout.add(new About(), "about");
        frame.add(panelCardLayout);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-6, 0);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(1536, 864));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        name_title = new JLabel("CHESS GAME");
        name_title.setBounds(600, 240, 400, 46);
        name_title.setForeground(Color.WHITE);
        name_title.setFocusable(false);
        name_title.setFont(new Font("", Font.BOLD, 50));
        newgame_menu = new BeautifyButton("New Game");
        newgame_menu.setBounds(574, 378, 180, 46);
        newgame_menu.setForeground(Color.WHITE);
        newgame_menu.setFocusable(false);
        newgame_menu.setFont(newgame_menu.getFont().deriveFont(20.0f));
        history_menu = new BeautifyButton("History");
        history_menu.setBounds(794, 378, 180, 46);
        history_menu.setForeground(Color.WHITE);
        history_menu.setFocusable(false);
        history_menu.setFont(history_menu.getFont().deriveFont(20.0f));
        puzzles_menu = new BeautifyButton("Puzzles");
        puzzles_menu.setBounds(574, 458, 180, 46);
        puzzles_menu.setForeground(Color.WHITE);
        puzzles_menu.setFocusable(false);
        puzzles_menu.setFont(puzzles_menu.getFont().deriveFont(20.0f));
        about_menu = new BeautifyButton("About");
        about_menu.setBounds(794, 458, 180, 46);
        about_menu.setForeground(Color.WHITE);
        about_menu.setFocusable(false);
        about_menu.setFont(about_menu.getFont().deriveFont(20.0f));
        settings_menu = new BeautifyButton("Settings");
        settings_menu.setBounds(574, 538, 180, 46);
        settings_menu.setForeground(Color.WHITE);
        settings_menu.setFocusable(false);
        settings_menu.setFont(settings_menu.getFont().deriveFont(20.0f));
        exit_menu = new BeautifyButton("Exit");
        exit_menu.setBounds(794, 538, 180, 46);
        exit_menu.setForeground(Color.WHITE);
        exit_menu.setFocusable(false);
        exit_menu.setFont(exit_menu.getFont().deriveFont(20.0f));
        newgame_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCardLayout, "gameOptions");
            }
        });
        history_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCardLayout, "history");
            }
        });
        puzzles_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCardLayout, "listPuzzle");
            }
        });
        settings_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCardLayout, "setting");
            }
        });
        about_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCardLayout, "about");
            }
        });
        exit_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(icon_title, 670, 28, 180, 180, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
