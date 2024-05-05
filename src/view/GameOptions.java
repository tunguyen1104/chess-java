package view;

import javax.swing.*;

import controller.ListenerGameOptions;
import model.ReadImage;

import java.awt.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOptions extends JPanel {
    private JLabel title_bar_label;
    
    private Image chess_standard;
    private JButton go;
    private JLabel game_mode;
    private JLabel time;
    private JLabel lever;
    private JLabel side;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private Image option_box_game;
    private Image option_box_time;
    private Image option_box_side;
    private Image option_box_lever;
    private ButtonImage forward_left_game;
    private ButtonImage forward_right_game;
    private JLabel option_box_game_label;
    private int index_game = 0;
    private ButtonImage forward_left_time;
    private ButtonImage forward_right_time;
    private JLabel option_box_time_label;
    private int index_time = 0;
    private ButtonImage forward_left_side;
    private ButtonImage forward_right_side;
    private JLabel option_box_side_label;
    private int index_side = 0;
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
    DialogPromotion a;
    private ListenerGameOptions input = new ListenerGameOptions(this);
    public GameOptions(DialogPromotion a) {
        try {
            option_box_game = ReadImage.option_box;
            option_box_time = option_box_game;
            option_box_side = option_box_game;
            option_box_lever = option_box_game;
            chess_standard = ImageIO.read(new File("resources/gui/chess_standard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.a = a;
        initPanel();
    }
    public void handle_forward_left_game() {
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
    public void handle_forward_right_game() {
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
    public void handle_forward_left_time() {
        --index_time;
        if (index_time < 0)
            index_time = time_string.length - 1;
        option_box_time_label.setText(time_string[index_time]);
    }
    public void handle_forward_right_time() {
        ++index_time;
        if (index_time > time_string.length - 1)
            index_time = 0;
        option_box_time_label.setText(time_string[index_time]);
    }
    public void handle_go() {
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
        if (option_box_game_label.getText().equals("PvC")) {
            Menu.panelCardLayout.add(new GamePVC(minute,a), "gamePvC");
            Menu.cardLayout.show(Menu.panelCardLayout, "gamePvC");
        } else {
            Menu.panelCardLayout.add(new GamePVP(minute), "gamePvP");
            Menu.cardLayout.show(Menu.panelCardLayout, "gamePvP");
        }
    }
    public void initPanel() {
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        title_bar_label = new JLabel("Game Options");
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        title_bar_label.setBounds((Menu.screenWidth - 120) / 2, 18, title_bar_label.getPreferredSize().width, title_bar_label.getPreferredSize().height);
        this.add(title_bar_label);
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 - 65, 10, 44, 44);
        home_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 + 450 + 20, 10, 44, 44);
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
        forward_left_game = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_game = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_game.setBounds(580, 210, 32, 32);
        forward_right_game.setBounds(760, 210, 32, 32);
        this.add(forward_left_game);
        this.add(forward_right_game);
        this.add(option_box_game_label);

        // time
        option_box_time_label = new JLabel();
        option_box_time_label.setText(time_string[index_time]);
        option_box_time_label.setForeground(Color.WHITE);
        option_box_time_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_time_label.setBounds(660, 290, 100, 32);
        forward_left_time = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_time = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_time.setBounds(580, 290, 32, 32);
        forward_right_time.setBounds(760, 290, 32, 32);
        this.add(forward_left_time);
        this.add(forward_right_time);
        this.add(option_box_time_label);
        // side
        option_box_side_label = new JLabel();
        option_box_side_label.setText(side_string[index_side]);
        option_box_side_label.setForeground(Color.WHITE);
        option_box_side_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_side_label.setBounds(660, 370, 100, 32);
        forward_left_side = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_side = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_side.setBounds(580, 370, 32, 32);
        forward_right_side.setBounds(760, 370, 32, 32);
        this.add(forward_left_side);
        this.add(forward_right_side);
        this.add(option_box_side_label);
        // lever
        option_box_lever_label = new JLabel();
        option_box_lever_label.setText(lever_string[index_lever]);
        option_box_lever_label.setForeground(Color.WHITE);
        option_box_lever_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_lever_label.setBounds(662, 450, 100, 32);
        forward_left_lever = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_lever = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_lever.setBounds(580, 450, 32, 32);
        forward_right_lever.setBounds(760, 450, 32, 32);
        this.add(forward_left_lever);
        this.add(forward_right_lever);
        this.add(option_box_lever_label);

        back_normal_button.addMouseListener(input);
        home_normal_button.addMouseListener(input);
        forward_left_game.addMouseListener(input);
        forward_right_game.addMouseListener(input);
        forward_left_time.addMouseListener(input);
        forward_right_time.addMouseListener(input);
        forward_left_side.addMouseListener(input);
        forward_right_side.addMouseListener(input);
        forward_left_lever.addMouseListener(input);
        forward_right_lever.addMouseListener(input);
        go.addActionListener(input);
    }
    public void handle_forward_left_side() {
        --index_side;
        if (index_side < 0)
            index_side = side_string.length - 1;
        option_box_side_label.setText(side_string[index_side]);
    }
    public void handle_forward_right_side() {
        ++index_side;
        if (index_side > side_string.length - 1)
            index_side = 0;
        option_box_side_label.setText(side_string[index_side]);
    }

    public void handle_forward_left_lever() {
        --index_lever;
        if (index_lever < 0)
            index_lever = lever_string.length - 1;
        option_box_lever_label.setText(lever_string[index_lever]);
    }
    public void handle_forward_right_lever() {
        ++index_lever;
        if (index_lever > lever_string.length - 1)
            index_lever = 0;
        option_box_lever_label.setText(lever_string[index_lever]);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, ( Menu.screenWidth - 450 ) / 2, 10, 450, 44, this);
        g2d.drawImage(ReadImage.game_options_panel, 360, 160, 520, 420, this);
        g2d.drawImage(chess_standard, 890, 160, 245, 323, this);
        g2d.drawImage(option_box_game, 616, 210, 140, 32, this);
        g2d.drawImage(option_box_time, 616, 290, 140, 32, this);
        g2d.drawImage(option_box_side, 616, 370, 140, 32, this);
        g2d.drawImage(option_box_lever, 616, 450, 140, 32, this);
    }

    public ButtonImage getBack_normal_button() {
        return back_normal_button;
    }

    public ButtonImage getHome_normal_button() {
        return home_normal_button;
    }

    public ButtonImage getForward_left_game() {
        return forward_left_game;
    }

    public ButtonImage getForward_right_game() {
        return forward_right_game;
    }

    public ButtonImage getForward_left_time() {
        return forward_left_time;
    }

    public ButtonImage getForward_right_time() {
        return forward_right_time;
    }

    public ButtonImage getForward_left_side() {
        return forward_left_side;
    }

    public ButtonImage getForward_right_side() {
        return forward_right_side;
    }

    public ButtonImage getForward_left_lever() {
        return forward_left_lever;
    }

    public ButtonImage getForward_right_lever() {
        return forward_right_lever;
    }
}
