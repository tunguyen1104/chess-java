package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ListenerMenu;
import model.ReadImage;

import java.awt.*;
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
    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel panelCardLayout = new JPanel();
    private ListenerMenu input = new ListenerMenu(this);
    public Menu() {
        frame = new JFrame("CHESS");
        DialogPromotion ts=new DialogPromotion(frame,"",true);
        panelCardLayout.setLayout(cardLayout);
        try {
            menu_normal = ImageIO.read(new File("resources/buttons/menu_normal_225.png"));
            menu_selected = ImageIO.read(new File("resources/buttons/menu_selected_225.png"));
            icon_title = ImageIO.read(new File("resources/gui/icon_title.png"));
            icon_game = ImageIO.read(new File("resources/gui/icon_game.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(icon_game);
        new ReadImage();
        initPanel();
        panelCardLayout.add(this, "menu");
        panelCardLayout.add(new Setting(), "setting");
        panelCardLayout.add(new ListPuzzle(), "puzzle");
        panelCardLayout.add(new GameOptions(ts), "gameOptions");
        panelCardLayout.add(new History(), "history");
        panelCardLayout.add(new About(), "about");
        frame.add(panelCardLayout);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-7, 0);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public void initPanel() {
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        name_title = new JLabel("CHESS GAME");
        name_title.setForeground(Color.WHITE);
        name_title.setFocusable(false);
        name_title.setFont(new Font("", Font.BOLD, 50));
        name_title.setBounds((screenWidth - name_title.getPreferredSize().width) / 2 - 3, screenHeight / 4 + 46, 340, 46);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(41, 41, 41));
        buttonPanel.setLayout(new GridLayout(3, 2, 20, 20));
        buttonPanel.setBounds((screenWidth - 380) / 2, screenHeight / 2 - 70, 380, 217);
        newGame = new ButtonImage(menu_normal, menu_selected, 180, 59, "New Game", 5);
        history = new ButtonImage(menu_normal, menu_selected, 180, 59, "History", 5);
        puzzle = new ButtonImage(menu_normal, menu_selected, 180, 59, "Puzzle", 5);
        about = new ButtonImage(menu_normal, menu_selected, 180, 59, "About", 5);
        setting = new ButtonImage(menu_normal, menu_selected, 180, 59, "Setting", 5);
        exit = new ButtonImage(menu_normal, menu_selected, 180, 59, "Exit", 5);

        buttonPanel.add(newGame);
        buttonPanel.add(history);
        buttonPanel.add(puzzle);
        buttonPanel.add(about);
        buttonPanel.add(setting);
        buttonPanel.add(exit);

        this.add(buttonPanel);
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
        g2d.drawImage(icon_title, (screenWidth - 180) / 2, screenHeight / 4 - 180, 180, 180, this);
        
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
