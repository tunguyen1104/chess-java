package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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
    private BufferedImage back_selected;
    private BufferedImage home_selected;
    private JButton go;
    private JLabel game_mode;
    private JLabel time;
    private JLabel lever;
    private JLabel side;
    private BufferedImage icon_game;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private BufferedImage forward_normal_game;
    private BufferedImage forward_normal_game_v2;
    private BufferedImage forward_selected_game;
    private BufferedImage forward_selected_game_v2;
    private BufferedImage option_box_game;
    private ButtonImage forward_left_game;
    private ButtonImage forward_right_game;
    private JLabel option_box_game_label;
    private int index_game = 0;
    private BufferedImage forward_normal_time;
    private BufferedImage forward_normal_time_v2;
    private BufferedImage forward_selected_time;
    private BufferedImage forward_selected_time_v2;
    private ButtonImage forward_left_time;
    private ButtonImage forward_right_time;
    private JLabel option_box_time_label;
    private int index_time = 0;
    private BufferedImage forward_normal_side;
    private BufferedImage forward_normal_side_v2;
    private BufferedImage forward_selected_side;
    private BufferedImage forward_selected_side_v2;
    private ButtonImage forward_left_side;
    private ButtonImage forward_right_side;
    private JLabel option_box_side_label;
    private int index_side = 0;
    private BufferedImage forward_normal_lever;
    private BufferedImage forward_normal_lever_v2;
    private BufferedImage forward_selected_lever;
    private BufferedImage forward_selected_lever_v2;
    private ButtonImage forward_left_lever;
    private ButtonImage forward_right_lever;
    private JLabel option_box_lever_label;
    private int index_lever = 0;
    String game_mode_string[] = {
            "PvC","PvP"
    };
    String time_string[] = {
            "1 min","3 min","10 min", "30 min"
    };
    String side_string[] = {
            "White","Black","Random"
    };
    String lever_string[] = {
            "Easy","Medium","Hard"
    };
    public GameOptions (){
        try {
            option_box_game = ImageIO.read(new File("src/res/gui/option_box.png"));
            forward_normal_game = ImageIO.read(new File("src/res/buttons/forward_normal.png"));
            forward_normal_game_v2 = ImageIO.read(new File("src/res/buttons/forward_normalv2.png"));
            forward_selected_game = ImageIO.read(new File("src/res/buttons/forward_selected.png"));
            forward_selected_game_v2 = ImageIO.read(new File("src/res/buttons/forward_selectedv2.png"));

            forward_normal_time = forward_normal_game;
            forward_normal_time_v2 = forward_normal_game_v2;
            forward_selected_time = forward_selected_game;
            forward_selected_time_v2 = forward_selected_game_v2;

            forward_normal_side = forward_normal_game;
            forward_normal_side_v2 = forward_normal_game_v2;
            forward_selected_side = forward_selected_game;
            forward_selected_side_v2 = forward_selected_game_v2;

            forward_normal_lever = forward_normal_game;
            forward_normal_lever_v2 = forward_normal_game_v2;
            forward_selected_lever = forward_selected_game;
            forward_selected_lever_v2 = forward_selected_game_v2;
            icon_game = ImageIO.read(new File("src/res/gui/icon_game.png"));
            chess_standard = ImageIO.read(new File("src/res/gui/chess_standard.png"));
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("src/res/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("src/res/buttons/home_selected.png"));
            game_options_panel = ImageIO.read(new File("src/res/gui/game_options_panel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
        frame = new JFrame("CHESS");
        frame.setIconImage(icon_game);
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
        back_normal_button = new ButtonImage(back_normal,back_selected,42,42);
        home_normal_button = new ButtonImage(home_normal,home_selected,42,42);
        back_normal_button.setBounds(465,10,42,42);
        home_normal_button.setBounds(1000,10,42,42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new view.Menu();
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
                if(option_box_game_label.getText().equals("PvC")) {
                    //new Game PvC (time,side,lever)
                }
                else {
                    int minute = 1;
                    switch (index_time) {
                        case 0:
                            minute = 1;
                            break;
                        case 1:
                            minute = 3;
                            break;
                        case 2:
                            minute = 10;
                            break;
                        case 3:
                            minute = 30;
                            break;
                    }
                    frame.dispose();
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
        option_box_game_label = new JLabel();
        option_box_game_label.setText(game_mode_string[index_game]);
        option_box_game_label.setForeground(Color.WHITE);
        option_box_game_label.setFont(new Font("",Font.PLAIN,18));
        option_box_game_label.setBounds(650,210,100,32);
        forward_left_game = new ButtonImage(forward_normal_game,forward_selected_game,32,32);
        forward_right_game = new ButtonImage(forward_normal_game_v2,forward_selected_game_v2,32,32);
        forward_left_game.setBounds(580,210,32,32);
        forward_right_game.setBounds(760,210,32,32);
        forward_left_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_game;
                if(index_game < 0) index_game = game_mode_string.length - 1;
                option_box_game_label.setText(game_mode_string[index_game]);
            }
        });
        forward_right_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_game;
                if(index_game > game_mode_string.length - 1) index_game = 0;
                option_box_game_label.setText(game_mode_string[index_game]);
            }
        });
        this.add(forward_left_game);
        this.add(forward_right_game);
        this.add(option_box_game_label);

        //time
        option_box_time_label = new JLabel();
        option_box_time_label.setText(time_string[index_time]);
        option_box_time_label.setForeground(Color.WHITE);
        option_box_time_label.setFont(new Font("",Font.PLAIN,18));
        option_box_time_label.setBounds(648,290,100,32);
        forward_left_time = new ButtonImage(forward_normal_time,forward_selected_time,32,32);
        forward_right_time = new ButtonImage(forward_normal_time_v2,forward_selected_time_v2,32,32);
        forward_left_time.setBounds(580,290,32,32);
        forward_right_time.setBounds(760,290,32,32);
        forward_left_time.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_time;
                if(index_time < 0) index_time = time_string.length - 1;
                option_box_time_label.setText(time_string[index_time]);
            }
        });
        forward_right_time.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_time;
                if(index_time > time_string.length - 1) index_time = 0;
                option_box_time_label.setText(time_string[index_time]);
            }
        });
        this.add(forward_left_time);
        this.add(forward_right_time);
        this.add(option_box_time_label);

        //side
        option_box_side_label = new JLabel();
        option_box_side_label.setText(side_string[index_side]);
        option_box_side_label.setForeground(Color.WHITE);
        option_box_side_label.setFont(new Font("",Font.PLAIN,18));
        option_box_side_label.setBounds(648,370,100,32);
        forward_left_side = new ButtonImage(forward_normal_side,forward_selected_side,32,32);
        forward_right_side = new ButtonImage(forward_normal_side_v2,forward_selected_side_v2,32,32);
        forward_left_side.setBounds(580,370,32,32);
        forward_right_side.setBounds(760,370,32,32);
        forward_left_side.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_side;
                if(index_side < 0) index_side = side_string.length - 1;
                option_box_side_label.setText(side_string[index_side]);
            }
        });
        forward_right_side.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_side;
                if(index_side > side_string.length - 1) index_side = 0;
                option_box_side_label.setText(side_string[index_side]);
            }
        });
        this.add(forward_left_side);
        this.add(forward_right_side);
        this.add(option_box_side_label);
        //lever
        option_box_lever_label = new JLabel();
        option_box_lever_label.setText(lever_string[index_lever]);
        option_box_lever_label.setForeground(Color.WHITE);
        option_box_lever_label.setFont(new Font("",Font.PLAIN,18));
        option_box_lever_label.setBounds(650,450,100,32);
        forward_left_lever = new ButtonImage(forward_normal_lever,forward_selected_lever,32,32);
        forward_right_lever = new ButtonImage(forward_normal_lever_v2,forward_selected_lever_v2,32,32);
        forward_left_lever.setBounds(580,450,32,32);
        forward_right_lever.setBounds(760,450,32,32);
        forward_left_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_lever;
                if(index_lever < 0) index_lever = lever_string.length - 1;
                option_box_lever_label.setText(lever_string[index_lever]);
            }
        });
        forward_right_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_lever;
                if(index_lever > lever_string.length - 1) index_lever = 0;
                option_box_lever_label.setText(lever_string[index_lever]);
            }
        });
        this.add(forward_left_lever);
        this.add(forward_right_lever);
        this.add(option_box_lever_label);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(title_bar,530,10,450,42,this);
        g2d.drawImage(game_options_panel,360,160,520,420,this);
        g2d.drawImage(chess_standard,890,160,240,320,this);
        g2d.drawImage(option_box_game,616,210,140,32,this);
        g2d.drawImage(option_box_game,616,290,140,32,this);
        g2d.drawImage(option_box_game,616,370,140,32,this);
        g2d.drawImage(option_box_game,616,450,140,32,this);
    }

    public static void main(String[] args) {
        new GameOptions();
    }
}
