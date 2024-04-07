package view;

import javax.swing.*;

import model.ReadImage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOptions extends JPanel {
    private JLabel title_bar_label;
    private BufferedImage game_options_panel;
    private BufferedImage chess_standard;
    private JButton go;
    private JLabel game_mode;
    private JLabel time;
    private JLabel lever;
    private JLabel side;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private BufferedImage forward_normal_game;
    private BufferedImage forward_normal_game_v2;
    private BufferedImage forward_selected_game;
    private BufferedImage forward_selected_game_v2;
    private BufferedImage option_box_game;
    private BufferedImage option_box_time;
    private BufferedImage option_box_side;
    private BufferedImage option_box_lever;
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
            "PvC", "PvP"
    };
    String time_string[] = {
            "1 min", "3 min", "10 min", "30 min"
    };
    String side_string[] = {
            "White", "Black", "Random"
    };
    String lever_string[] = {
            "Easy", "Medium", "Hard"
    };

    public GameOptions() {
        try {
            option_box_game = ImageIO.read(new File("resources/gui/option_box.png"));
            forward_normal_game = ImageIO.read(new File("resources/buttons/forward_normal.png"));
            forward_normal_game_v2 = ImageIO.read(new File("resources/buttons/forward_normalv2.png"));
            forward_selected_game = ImageIO.read(new File("resources/buttons/forward_selected.png"));
            forward_selected_game_v2 = ImageIO.read(new File("resources/buttons/forward_selectedv2.png"));
            option_box_time = option_box_game;
            option_box_side = option_box_game;
            option_box_lever = option_box_game;
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
            chess_standard = ImageIO.read(new File("resources/gui/chess_standard.png"));
            game_options_panel = ImageIO.read(new File("resources/gui/game_options_panel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(1536, 864));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        title_bar_label = new JLabel("Game Options");
        title_bar_label.setBounds(700, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        // ----------------------
        // setting back_normal, home_normal
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
                super.mouseClicked(e);
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        // setting button
        go = new JButton("Go");
        go.setFont(new Font("", Font.BOLD, 30));
        go.setBounds(890, 490, 246, 88);
        go.setBackground(new Color(140, 181, 90));
        go.setForeground(Color.WHITE);
        go.setFocusPainted(false);
        this.add(go);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option_box_game_label.getText().equals("PvC")) {
                    // new Game PvC (time,side,lever)
                } else {
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
                    Menu.panelCardLayout.add(new GamePVP(minute), "gamePvP");
                    Menu.cardLayout.show(Menu.panelCardLayout, "gamePvP");
                }
            }
        });
        // setting JLabel
        game_mode = new JLabel("Game Mode");
        game_mode.setBounds(420, 200, 120, 50);
        game_mode.setForeground(Color.WHITE);
        game_mode.setFont(new Font("", Font.BOLD, 20));
        this.add(game_mode);

        time = new JLabel("Time");
        time.setBounds(480, 280, 100, 50);
        time.setForeground(Color.WHITE);
        time.setFont(new Font("", Font.BOLD, 20));
        this.add(time);

        side = new JLabel("Side");
        side.setBounds(480, 360, 100, 50);
        side.setForeground(Color.WHITE);
        side.setFont(new Font("", Font.BOLD, 20));
        this.add(side);

        lever = new JLabel("Lever");
        lever.setBounds(470, 440, 100, 50);
        lever.setForeground(Color.WHITE);
        lever.setFont(new Font("", Font.BOLD, 20));
        this.add(lever);
        //
        option_box_game_label = new JLabel();
        option_box_game_label.setText(game_mode_string[index_game]);
        option_box_game_label.setForeground(Color.WHITE);
        option_box_game_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_game_label.setBounds(665, 210, 100, 32);
        forward_left_game = new ButtonImage(forward_normal_game, forward_selected_game, 32, 32, "");
        forward_right_game = new ButtonImage(forward_normal_game_v2, forward_selected_game_v2, 32, 32, "");
        forward_left_game.setBounds(580, 210, 32, 32);
        forward_right_game.setBounds(760, 210, 32, 32);
        forward_left_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_game;
                if (index_game < 0)
                    index_game = game_mode_string.length - 1;
                if (index_game == 1) {
                    side.setVisible(false);
                    option_box_side_label.setVisible(false);
                    forward_left_side.setVisible(false);
                    forward_right_side.setVisible(false);
                    option_box_side = null;
                    lever.setVisible(false);
                    option_box_lever_label.setVisible(false);
                    forward_left_lever.setVisible(false);
                    forward_right_lever.setVisible(false);
                    option_box_lever = null;
                } else {
                    side.setVisible(true);
                    option_box_side_label.setVisible(true);
                    forward_left_side.setVisible(true);
                    forward_right_side.setVisible(true);
                    option_box_side = option_box_game;
                    lever.setVisible(true);
                    option_box_lever_label.setVisible(true);
                    forward_left_lever.setVisible(true);
                    forward_right_lever.setVisible(true);
                    option_box_lever = option_box_game;
                }
                option_box_game_label.setText(game_mode_string[index_game]);
            }
        });
        forward_right_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_game;
                if (index_game > game_mode_string.length - 1)
                    index_game = 0;
                if (index_game == 1) {
                    side.setVisible(false);
                    option_box_side_label.setVisible(false);
                    forward_left_side.setVisible(false);
                    forward_right_side.setVisible(false);
                    option_box_side = null;
                    lever.setVisible(false);
                    option_box_lever_label.setVisible(false);
                    forward_left_lever.setVisible(false);
                    forward_right_lever.setVisible(false);
                    option_box_lever = null;
                } else {
                    side.setVisible(true);
                    option_box_side_label.setVisible(true);
                    forward_left_side.setVisible(true);
                    forward_right_side.setVisible(true);
                    option_box_side = option_box_game;
                    lever.setVisible(true);
                    option_box_lever_label.setVisible(true);
                    forward_left_lever.setVisible(true);
                    forward_right_lever.setVisible(true);
                    option_box_lever = option_box_game;
                }
                option_box_game_label.setText(game_mode_string[index_game]);
            }
        });
        this.add(forward_left_game);
        this.add(forward_right_game);
        this.add(option_box_game_label);

        // time
        option_box_time_label = new JLabel();
        option_box_time_label.setText(time_string[index_time]);
        option_box_time_label.setForeground(Color.WHITE);
        option_box_time_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_time_label.setBounds(660, 290, 100, 32);
        forward_left_time = new ButtonImage(forward_normal_time, forward_selected_time, 32, 32, "");
        forward_right_time = new ButtonImage(forward_normal_time_v2, forward_selected_time_v2, 32, 32, "");
        forward_left_time.setBounds(580, 290, 32, 32);
        forward_right_time.setBounds(760, 290, 32, 32);
        forward_left_time.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_time;
                if (index_time < 0)
                    index_time = time_string.length - 1;
                option_box_time_label.setText(time_string[index_time]);
            }
        });
        forward_right_time.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_time;
                if (index_time > time_string.length - 1)
                    index_time = 0;
                option_box_time_label.setText(time_string[index_time]);
            }
        });
        this.add(forward_left_time);
        this.add(forward_right_time);
        this.add(option_box_time_label);

        // side
        option_box_side_label = new JLabel();
        option_box_side_label.setText(side_string[index_side]);
        option_box_side_label.setForeground(Color.WHITE);
        option_box_side_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_side_label.setBounds(660, 370, 100, 32);
        forward_left_side = new ButtonImage(forward_normal_side, forward_selected_side, 32, 32, "");
        forward_right_side = new ButtonImage(forward_normal_side_v2, forward_selected_side_v2, 32, 32, "");
        forward_left_side.setBounds(580, 370, 32, 32);
        forward_right_side.setBounds(760, 370, 32, 32);
        forward_left_side.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_side;
                if (index_side < 0)
                    index_side = side_string.length - 1;
                option_box_side_label.setText(side_string[index_side]);
            }
        });
        forward_right_side.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_side;
                if (index_side > side_string.length - 1)
                    index_side = 0;
                option_box_side_label.setText(side_string[index_side]);
            }
        });
        this.add(forward_left_side);
        this.add(forward_right_side);
        this.add(option_box_side_label);
        // lever
        option_box_lever_label = new JLabel();
        option_box_lever_label.setText(lever_string[index_lever]);
        option_box_lever_label.setForeground(Color.WHITE);
        option_box_lever_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_lever_label.setBounds(662, 450, 100, 32);
        forward_left_lever = new ButtonImage(forward_normal_lever, forward_selected_lever, 32, 32, "");
        forward_right_lever = new ButtonImage(forward_normal_lever_v2, forward_selected_lever_v2, 32, 32, "");
        forward_left_lever.setBounds(580, 450, 32, 32);
        forward_right_lever.setBounds(760, 450, 32, 32);
        forward_left_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_lever;
                if (index_lever < 0)
                    index_lever = lever_string.length - 1;
                option_box_lever_label.setText(lever_string[index_lever]);
            }
        });
        forward_right_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_lever;
                if (index_lever > lever_string.length - 1)
                    index_lever = 0;
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
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(game_options_panel, 360, 160, 520, 420, this);
        g2d.drawImage(chess_standard, 890, 160, 245, 323, this);
        g2d.drawImage(option_box_game, 616, 210, 140, 32, this);
        g2d.drawImage(option_box_time, 616, 290, 140, 32, this);
        g2d.drawImage(option_box_side, 616, 370, 140, 32, this);
        g2d.drawImage(option_box_lever, 616, 450, 140, 32, this);
    }
}
