package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.File;
import java.io.IOException;

public class GameOptions extends JPanel{
    private JFrame frame;
    private JLabel title_bar_label;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage home_normal;
    private BufferedImage game_options_panel;
    private BufferedImage chess_standard;
    private JButton go;
    private JLabel game_mode;
    private JLabel time;
    private JLabel lever;
    private JLabel side;
    private JComboBox combo_game;
    private JComboBox combo_time;
    private JComboBox combo_side;
    private JComboBox combo_lever;
    private JScrollPane listScrollPane_game;
    private JScrollPane listScrollPane_time;
    private JScrollPane listScrollPane_side;
    private JScrollPane listScrollPane_lever;
    Border border = BorderFactory.createLineBorder(Color.BLACK); // You can adjust the color and thickness
    public GameOptions (){
        try {
            chess_standard = ImageIO.read(new File("src/res/gui/chess_standard.png"));
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
            game_options_panel = ImageIO.read(new File("src/res/gui/game_options_panel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
        frame = new JFrame("CHESS");
        frame.add(this);
        frame.pack();
        frame.setLocation(-6,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
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
        this.setPreferredSize(new Dimension(1600,1000));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        title_bar_label = new JLabel("Game Options");
        title_bar_label.setBounds(700,0,400,60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        //----------------------
        //setting back_normal, home_normal
        MyImageButton back_normal_button = new MyImageButton(back_normal,"back_normal");
        MyImageButton home_normal_button = new MyImageButton(home_normal,"home_normal");
        back_normal_button.setBounds(465,10,42,42);
        home_normal_button.setBounds(1000,10,42,42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Menu();
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Menu();
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        //setting button
        go = new JButton("Go");
        go.setFont(new Font("",Font.BOLD,30));
        go.setBounds(890,490,240,88);
        go.setBackground(new Color(140, 181, 90));
        go.setForeground(Color.WHITE);
        go.setFocusPainted(false);
        this.add(go);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(combo_game.getSelectedIndex() == 0) {
                    //new Game PvC (time,side,lever)
                }
                else {
                    int minute = 1;
                    int select = combo_time.getSelectedIndex();
                    switch (select) {
                        case 0:
                            minute = 1;
                            break;
                        case 1:
                            minute = 3;
                            break;
                        case 2:
                            minute = 10;
                            break;
                    }
                    new GamePVP(minute);
                }
            }
        });
        //setting JLabel
        game_mode = new JLabel("Game Mode");
        game_mode.setBounds(420,200,120,50);
        game_mode.setForeground(Color.WHITE);
        game_mode.setFont(new Font("",Font.BOLD,20));
        this.add(game_mode);

        time = new JLabel("Time");
        time.setBounds(480,280,100,50);
        time.setForeground(Color.WHITE);
        time.setFont(new Font("",Font.BOLD,20));
        this.add(time);

        side = new JLabel("Side");
        side.setBounds(480,360,100,50);
        side.setForeground(Color.WHITE);
        side.setFont(new Font("",Font.BOLD,20));
        this.add(side);

        lever = new JLabel("Lever");
        lever.setBounds(470,440,100,50);
        lever.setForeground(Color.WHITE);
        lever.setFont(new Font("",Font.BOLD,20));
        this.add(lever);
        //
        String game_mode[] = {
                "  PvC","  PvP"
        };
        combo_game = new JComboBox(game_mode);
        combo_game.setBackground(new Color(27, 101, 106, 255));
        combo_game.setFocusable(false);
        combo_game.setSelectedIndex(0);
        combo_game.setForeground(Color.WHITE);
        combo_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = combo_game.getSelectedIndex();
                if(selectedIndex == 1) {
                    lever.setVisible(false);
                    combo_lever.setVisible(false);
                    listScrollPane_lever.setVisible(false);
                }
                else {
                    lever.setVisible(true);
                    combo_lever.setVisible(true);
                    listScrollPane_lever.setVisible(true);
                }
            }
        });

        listScrollPane_game = new JScrollPane(combo_game);
        listScrollPane_game.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_game.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_game.setBounds(590, 210, 200, 30);
        listScrollPane_game.setBorder(border);
        this.add(listScrollPane_game);

        String time[] = {
                "  1 min","  3 min","  10 min"
        };
        combo_time = new JComboBox(time);
        combo_time.setBackground(new Color(27, 101, 106, 255));
        combo_time.setFocusable(false);
        combo_time.setSelectedIndex(0);
        combo_time.setForeground(Color.WHITE);

        listScrollPane_time = new JScrollPane(combo_time);
        listScrollPane_time.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_time.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_time.setBounds(590, 290, 200, 30);
        listScrollPane_time.setBorder(border);
        this.add(listScrollPane_time);

        String side[] = {
                "  White","  Black","  Random"
        };
        combo_side = new JComboBox(side);
        combo_side.setBackground(new Color(27, 101, 106, 255));
        combo_side.setFocusable(false);
        combo_side.setSelectedIndex(0);
        combo_side.setForeground(Color.WHITE);

        listScrollPane_side = new JScrollPane(combo_side);
        listScrollPane_side.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_side.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_side.setBounds(590, 370, 200, 30);
        listScrollPane_side.setBorder(border);
        this.add(listScrollPane_side);

        String lever[] = {
                "  Easy","  Medium","  Hard"
        };
        combo_lever = new JComboBox(lever);
        combo_lever.setBackground(new Color(27, 101, 106, 255));
        combo_lever.setFocusable(false);
        combo_lever.setSelectedIndex(0);
        combo_lever.setForeground(Color.WHITE);

        listScrollPane_lever = new JScrollPane(combo_lever);
        listScrollPane_lever.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_lever.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_lever.setBounds(590, 450, 200, 30);
        listScrollPane_lever.setBorder(border);
        this.add(listScrollPane_lever);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(title_bar,530,10,450,42,this);
        g2d.drawImage(game_options_panel,360,160,520,420,this);
        g2d.drawImage(chess_standard,890,160,240,320,this);
    }

    public static void main(String[] args) {
        new GameOptions();
    }
}
