package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ListenerMenu;
import model.JDBCConnection;
import model.ReadImage;
import model.Sound;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{
    public static JFrame frame;
    private JLabel name_title;
    public Image icon_title;
    private Image icon_game;
    private Image menu_normal;
    private Image menu_selected;
    private ButtonImage newGame;
    private ButtonImage history;
    private ButtonImage puzzle;
    private ButtonImage setting;
    private ButtonImage about;
    private ButtonImage exit;
    
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel panelCardLayout = new JPanel();
    private ReadImage readImage = new ReadImage();
    private ListenerMenu input = new ListenerMenu(this);
    public Menu() {
        frame = new JFrame("CHESS");
        panelCardLayout.setLayout(cardLayout);
        try {
            menu_normal = ImageIO.read(new File("resources/buttons/menu_normal_225px.png"));
            menu_selected = ImageIO.read(new File("resources/buttons/menu_selected_225px.png"));
            icon_title = ImageIO.read(new File("resources/gui/icon_title.png"));
            icon_game = ImageIO.read(new File("resources/gui/icon_game.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(icon_game);
        initPanel();
        panelCardLayout.add(this, "menu");
        panelCardLayout.add(new Setting(), "setting");
        panelCardLayout.add(new ListPuzzle(), "puzzle");
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
        newGame = new ButtonImage(menu_normal, menu_selected, 180, 59, "New Game",5);
        newGame.setBounds(574, 368, 180, 59);
        history = new ButtonImage(menu_normal, menu_selected, 180, 59, "History",5);
        history.setBounds(794, 368, 180, 59);
        puzzle = new ButtonImage(menu_normal, menu_selected, 180, 59, "Puzzle",5);
        puzzle.setBounds(574, 448, 180, 59);
        about = new ButtonImage(menu_normal, menu_selected, 180, 59, "About",5);
        about.setBounds(794, 448, 180, 59);
        setting = new ButtonImage(menu_normal, menu_selected, 180, 59, "Setting",5);
        setting.setBounds(574, 528, 180, 59);
        exit = new ButtonImage(menu_normal, menu_selected, 180, 59, "Exit",5);
        exit.setBounds(794, 528, 180, 59);
        newGame.setHorizontalAlignment(SwingConstants.CENTER); // Đặt căn giữa ngang
        history.setHorizontalAlignment(SwingConstants.CENTER);
        puzzle.setHorizontalAlignment(SwingConstants.CENTER);
        setting.setHorizontalAlignment(SwingConstants.CENTER);
        about.setHorizontalAlignment(SwingConstants.CENTER);
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(newGame);
        this.add(history);
        this.add(puzzle);
        this.add(setting);
        this.add(exit);
        this.add(about);
        this.add(name_title);
        newGame.addMouseListener(input);
        history.addMouseListener(input);
        puzzle.addMouseListener(input);
        about.addMouseListener(input);
        setting.addMouseListener(input);
        exit.addMouseListener(input);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(icon_title, 670, 28, 180, 180, this);
        
    }
    public ButtonImage getAbout() {
        return about;
    }
    public ButtonImage getExit() {
        return exit;
    }
    public ButtonImage getHistory() {
        return history;
    }
    public ButtonImage getPuzzle() {
        return puzzle;
    }
    public ButtonImage getSetting() {
        return setting;
    }
    public ButtonImage getNewGame() {
        return newGame;
    }
}
