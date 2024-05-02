package view;

import controller.ListenerSetting;
import model.JDBCConnection;
import model.ReadImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Setting extends JPanel {
    private JPanel game;
    private JPanel account;
    private JPanel piece_panel;
    private JLabel piece_set;
    private JLabel board_label;
    private JLabel sound_label;
    private Image piece_image;
    private JLabel title_bar_label;
    private Image noavatar;
    private BufferedImage board_image;
    private Image option_setting_normal;
    private Image option_setting_selected;
    private Image option_settingv1;
    private Image option_settingv2;
    private Image logout_image;
    private JLabel option_game;
    private JLabel option_account;
    private JLabel logout_label;
    private Image board_image_cut;
    private boolean check_color;// nếu dang hover vào label -> true
    private JPanel board_panel;
    private JLabel name;
    private JLabel lever;
    private JLabel email;
    private JLabel changePassword;
    private JLabel newPassword;
    private JLabel retypeNewPassword;
    private JPasswordField newPasswordBox;
    private JPasswordField retypeNewPasswordBox;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private int index_piece = 0;
    private ButtonImage forward_left_piece;
    private ButtonImage forward_right_piece;
    private int index_board = 0;
    private ButtonImage forward_left_board;
    private ButtonImage forward_right_board;
    private int index_sound = 0;
    private JLabel option_box_sound_label;
    private ButtonImage forward_left_sound;
    private ButtonImage forward_right_sound;
    private JButton change_password;
    ArrayList<String> dataJDBC;
    String[] piece_url = {
            "resources/pieces/default.png",
            "resources/pieces/alpha.png",
            "resources/pieces/anarcandy.png",
            "resources/pieces/cardinal.png",
            "resources/pieces/chessnut.png",
            "resources/pieces/kiwen-suwi.png",
            "resources/pieces/maestro.png",
            "resources/pieces/tatiana.png"
    };
    String board_url[] = {
            "resources/board/green.png", "resources/board/brown.png", "resources/board/tournament.png",
            "resources/board/blackwhite.png",
            "resources/board/blue.png", "resources/board/metal.png", "resources/board/wood.png", "resources/board/orange.png"
    };
    String turn[] = { "On", "Off" };
    ArrayList<String> inforAccount = null;
    private ListenerSetting input = new ListenerSetting(this);
    public Setting() {
        initPanel();
    }

    public void initPanel() {
        dataJDBC = new ArrayList<String>();
        dataJDBC = JDBCConnection.takeDataSetting();
        inforAccount = new ArrayList<>();
        inforAccount = JDBCConnection.takeInforAccount();
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        // Load image
        try {
            noavatar = ImageIO.read(new File("resources/gui/noavatar.png"));
            option_setting_normal = ReadImage.history_normal;
            option_setting_selected = ReadImage.history_selected;
            option_settingv2 = option_setting_normal;
            option_settingv1 = option_setting_selected;
            logout_image = option_setting_normal;
            board_image = (BufferedImage) ReadImage.board_image;
            board_image_cut = board_image.getSubimage(0, 0, 100 * 6, 100 * 3).getScaledInstance(100 * 6,
                    100 * 3, BufferedImage.SCALE_SMOOTH);
            piece_image = ReadImage.piece;
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        this.setPreferredSize(new Dimension(1536, 864));
        // ===========Title Bar============
        title_bar_label = new JLabel("Settings");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        this.add(title_bar_label);
        // ----------------------
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        this.add(back_normal_button);
        this.add(home_normal_button);
        back_normal_button.addMouseListener(input);
        home_normal_button.addMouseListener(input);
        // ------------------------
        initGame();
        initAccount();
        initLogout();
    }
    public void handle_forward_left_piece() {
        --index_piece;
        if (index_piece < 0)
            index_piece = piece_url.length - 1;
        try {
            piece_image = ImageIO.read(new File(piece_url[index_piece]));
            piece_panel.repaint();
        } catch (IOException ex) {
            System.out.println("Error url image!");
        }
        ReadImage.changePieceImage(piece_url[index_piece]);
        JDBCConnection.updateBoard(piece_url[index_piece]);
    }
    public void handle_forward_right_piece() {
        ++index_piece;
        if (index_piece > piece_url.length - 1)
            index_piece = 0;
        try {
            piece_image = ImageIO.read(new File(piece_url[index_piece]));
            piece_panel.repaint();
        } catch (IOException ex) {
            System.out.println("Error url image!");
        }
        ReadImage.changePieceImage(piece_url[index_piece]);
        JDBCConnection.updateBoard(piece_url[index_piece]);
    }
    public void handle_forward_left_board() {
        --index_board;
        if (index_board < 0)
            index_board = board_url.length - 1;
        try {
            board_image = ImageIO.read(new File(board_url[index_board]));
            int tilesize = board_image.getWidth() / 8;
            board_image_cut = board_image.getSubimage(0, 0, tilesize * 6, tilesize * 3)
                    .getScaledInstance(tilesize * 6, tilesize * 3, BufferedImage.SCALE_SMOOTH);
            board_panel.repaint();
        } catch (IOException ex) {
            System.out.println("Error url image!");
        }
        ReadImage.changeBoardImage(board_url[index_board]);
        JDBCConnection.updateBoard(board_url[index_board]);
    }
    public void handle_forward_right_board() {
        ++index_board;
        if (index_board > board_url.length - 1)
            index_board = 0;
        try {
            board_image = ImageIO.read(new File(board_url[index_board]));
            int tilesize = board_image.getWidth() / 8;
            board_image_cut = board_image.getSubimage(0, 0, tilesize * 6, tilesize * 3)
                    .getScaledInstance(tilesize * 6, tilesize * 3, BufferedImage.SCALE_SMOOTH);
            board_panel.repaint();
        } catch (IOException ex) {
            System.out.println("Error url image!");
        }
        ReadImage.changeBoardImage(board_url[index_board]);
        JDBCConnection.updateBoard(board_url[index_board]);
    }
    public void handle_forward_left_sound() {
        --index_sound;
        if (index_sound < 0)
            index_sound = 1;
        option_box_sound_label.setText(turn[index_sound]);
        About.changeSoundBackGround(index_sound);
        ReadImage.changeSound(index_sound);
        JDBCConnection.updateSound((index_sound == 0) ? true : false);
    }
    public void handle_forward_right_sound() {
        ++index_sound;
        if (index_sound > 1)
            index_sound = 0;
        option_box_sound_label.setText(turn[index_sound]);
        About.changeSoundBackGround(index_sound);
        ReadImage.changeSound(index_sound);
        JDBCConnection.updateSound((index_sound == 0) ? true : false);
    }
    public void initGame() {
        game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(ReadImage.option_box, 276, 280, 160, 32, game);
            }
        };
        game.setBounds(508, 180, 526, 408);
        game.setBackground(new Color(55, 55, 55));
        game.setLayout(null);
        this.add(game);
        // label piece set
        piece_set = new JLabel("Piece Set");
        piece_set.setBounds(50, 30, 90, 40);
        piece_set.setForeground(Color.WHITE);
        piece_set.setFont(new Font("", Font.PLAIN, 20));
        game.add(piece_set);
        // ------------------------
        board_label = new JLabel("Board");
        board_label.setBounds(50, 150, 90, 40);
        board_label.setForeground(Color.WHITE);
        board_label.setFont(new Font("", Font.PLAIN, 20));
        game.add(board_label);
        // ------------------------
        sound_label = new JLabel("Sound");
        sound_label.setBounds(50, 270, 90, 40);
        sound_label.setForeground(Color.WHITE);
        sound_label.setFont(new Font("", Font.PLAIN, 20));
        game.add(sound_label);
        // setting type pieces
        forward_left_piece = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_piece = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_piece.setBounds(240, 40, 32, 32);
        forward_right_piece.setBounds(440, 40, 32, 32);
        for (int i = 0; i < piece_url.length; ++i) {
            if (piece_url[i].equals(dataJDBC.get(0))) {
                index_piece = i;
                break;
            }
        }
        piece_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(piece_image, 6, 5, 140, 50, piece_panel);
            }
        };
        piece_panel.setBackground(new Color(161, 150, 128));
        piece_panel.setBounds(282, 30, 150, 60);
        game.add(piece_panel);
        game.add(forward_left_piece);
        game.add(forward_right_piece);
        // board
        for (int i = 0; i < board_url.length; ++i) {
            if (board_url[i].equals(dataJDBC.get(1))) {
                index_board = i;
                break;
            }
        }
        forward_left_board = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_right_board = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_board.setBounds(240, 160, 32, 32);
        forward_right_board.setBounds(440, 160, 32, 32);
        board_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(board_image_cut, 0, 0, 150, 70, board_panel);
            }
        };
        board_panel.setBounds(280, 152, 150, 70);
        game.add(board_panel);
        game.add(forward_left_board);
        game.add(forward_right_board);
        //
        option_box_sound_label = new JLabel();
        index_sound = dataJDBC.get(2).equals("1") ? 0 : 1;
        option_box_sound_label.setText(turn[index_sound]);
        option_box_sound_label.setForeground(Color.WHITE);
        option_box_sound_label.setFont(new Font("", Font.PLAIN, 18));
        option_box_sound_label.setBounds(340, 280, 200, 32);
        forward_left_sound = new ButtonImage(ReadImage.forward_normal, ReadImage.back_selected, 32, 32, "");
        forward_right_sound = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_left_sound.setBounds(240, 280, 32, 32);
        forward_right_sound.setBounds(440, 280, 32, 32);

        game.add(forward_left_sound);
        game.add(forward_right_sound);
        game.add(option_box_sound_label);
        // option_setting
        option_game = new JLabel("Game");
        option_game.setBounds(280, 200, 200, 40);
        option_game.setFont(new Font("", Font.PLAIN, 20));
        option_game.setForeground(Color.WHITE);
        option_game.setHorizontalAlignment(SwingConstants.CENTER);
        
        forward_left_piece.addMouseListener(input);
        forward_right_piece.addMouseListener(input);
        forward_left_board.addMouseListener(input);
        forward_right_board.addMouseListener(input);
        forward_left_sound.addMouseListener(input);
        forward_right_sound.addMouseListener(input);
        option_game.addMouseListener(input);
    }
    public void handle_option_game_mouse_pressed() {
        option_game.setForeground(new Color(47, 53, 62));
        logout_image = option_setting_normal;
        option_settingv2 = option_setting_normal;
        option_settingv1 = option_setting_selected;
        game.setVisible(true);
        account.setVisible(false);
        repaint();
    }
    public void handle_option_game_mouse_entered() {
        option_game.setForeground(new Color(140, 181, 90));
        check_color = true;
    }
    public void handle_option_game_mouse_exited() {
        option_game.setForeground(Color.WHITE);
        check_color = false;
    }
    public void handle_option_game_mouse_released() {
        if (check_color) {
            option_game.setForeground(new Color(140, 181, 90));
        } else {
            option_game.setForeground(Color.WHITE);
        }
    }

    public void initLogout() {
        logout_label = new JLabel("Log out");
        logout_label.setFont(new Font("", Font.PLAIN, 20));
        logout_label.setForeground(Color.WHITE);
        logout_label.setHorizontalAlignment(SwingConstants.CENTER);
        logout_label.setBounds(280, 300, 200, 40);
        this.add(logout_label);
        logout_label.addMouseListener(input);
    }
    public void handle_logout_label_entered() {
        logout_label.setForeground(new Color(140, 181, 90));
    }
    public void handle_logout_label_exited() {
        logout_label.setForeground(Color.WHITE);
    }
    public void handle_logout_label_pressed() {
        ReadImage.sound.close();
        // call database delete current user
        JDBCConnection.logOut();
        JDBCConnection.deleteDataCurrentUser();
        Menu.frame.dispose();
        new Login();
    }

    public void initAccount() {
        // account
        option_account = new JLabel("Account");
        option_account.setBounds(280, 250, 200, 40);
        option_account.setFont(new Font("", Font.PLAIN, 20));
        option_account.setForeground(Color.WHITE);
        option_account.setHorizontalAlignment(SwingConstants.CENTER);
        account = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(noavatar, 50, 20, 80, 80, account);
            }
        };
        account.setBounds(508, 180, 526, 408);
        account.setBackground(new Color(55, 55, 55));
        account.setLayout(null);
        account.setVisible(false);
        name = new JLabel(inforAccount.get(0));
        name.setBounds(140, 20, 200, 30);
        name.setForeground(Color.WHITE);
        name.setFont(name.getFont().deriveFont(20.0f));
        account.add(name);
        lever = new JLabel("Lever 1");
        lever.setBounds(140, 50, 90, 30);
        lever.setForeground(Color.WHITE);
        lever.setFont(lever.getFont().deriveFont(14f));
        account.add(lever);
        email = new JLabel("Email : " + inforAccount.get(2));
        email.setBounds(50, 140, 300, 30);
        email.setForeground(Color.WHITE);
        email.setFont(email.getFont().deriveFont(18f));
        account.add(email);
        changePassword = new JLabel("Change Password");
        changePassword.setBounds(50, 200, 200, 30);
        changePassword.setForeground(Color.WHITE);
        changePassword.setFont(changePassword.getFont().deriveFont(20.0f));
        account.add(changePassword);
        newPassword = new JLabel("New Password");
        newPassword.setBounds(50, 260, 200, 20);
        newPassword.setForeground(Color.WHITE);
        newPassword.setFont(newPassword.getFont().deriveFont(15f));
        account.add(newPassword);
        retypeNewPassword = new JLabel("Retype New Password");
        retypeNewPassword.setBounds(50, 320, 200, 20);
        retypeNewPassword.setForeground(Color.WHITE);
        retypeNewPassword.setFont(retypeNewPassword.getFont().deriveFont(15f));
        account.add(retypeNewPassword);
        newPasswordBox = new JPasswordField();
        newPasswordBox.setBounds(260, 254, 200, 30);
        account.add(newPasswordBox);
        retypeNewPasswordBox = new JPasswordField();
        retypeNewPasswordBox.setBounds(260, 314, 200, 30);
        account.add(retypeNewPasswordBox);
        change_password = new JButton("Change Password");
        change_password.setFont(new Font("", Font.PLAIN, 16));
        change_password.setBackground(new Color(80, 161, 191));
        change_password.setForeground(Color.WHITE);
        change_password.setFocusPainted(false);
        change_password.setBounds(50, 360, 180, 40);
        account.add(change_password);
        this.add(option_game);
        this.add(option_account);
        this.add(account);
        change_password.addActionListener(input);
        option_account.addMouseListener(input);
    }
    public void option_account_entered() {
        option_account.setForeground(new Color(140, 181, 90));
        check_color = true;
    }
    public void option_account_pressed() {
        option_account.setForeground(new Color(47, 53, 62));
        logout_image = option_setting_normal;
        option_settingv1 = option_setting_normal;
        option_settingv2 = option_setting_selected;
        game.setVisible(false);
        account.setVisible(true);
        repaint();
    }
    public void option_account_exited() {
        option_account.setForeground(Color.WHITE);
        check_color = false;
    }
    public void option_account_released() {
        if (check_color) {
            option_account.setForeground(new Color(140, 181, 90));
        } else {
            option_account.setForeground(Color.WHITE);
        }
    }
    public void handle_change_password() {
        inforAccount = JDBCConnection.takeInforAccount();
        char[] new_password_char = newPasswordBox.getPassword();
        String new_password = new String(new_password_char).trim();
        char[] new_retype_password_char = retypeNewPasswordBox.getPassword();
        String new_retype_password = new String(new_retype_password_char).trim();
        if (new_password.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter new Password", "Message",
                    JOptionPane.WARNING_MESSAGE);
        } else if (new_password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Please enter new Password length < 8", "Message",
                    JOptionPane.WARNING_MESSAGE);
        } else if (new_password.equals(new_retype_password)) {
            if (!new_password.equals(inforAccount.get(1))) {
                JDBCConnection.updatePasswordCurrentUser(new_password);
                JDBCConnection.logOut();
                JOptionPane.showMessageDialog(null, "Change password success", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                newPasswordBox.setText("");
                retypeNewPasswordBox.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "The new password is not valid", "Message",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The retype new password is not valid", "Message",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(option_settingv1, 280, 200, 200, 40, this);
        g2d.drawImage(option_settingv2, 280, 250, 200, 40, this);
        g2d.drawImage(logout_image, 280, 300, 200, 40, this);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(ReadImage.game_options_panel, 500, 174, 540, 420, this);
    }
    public ButtonImage getBack_normal_button() {
        return back_normal_button;
    }

    public ButtonImage getHome_normal_button() {
        return home_normal_button;
    }

    public ButtonImage getForward_left_piece() {
        return forward_left_piece;
    }

    public ButtonImage getForward_right_piece() {
        return forward_right_piece;
    }

    public ButtonImage getForward_left_board() {
        return forward_left_board;
    }

    public ButtonImage getForward_right_board() {
        return forward_right_board;
    }

    public ButtonImage getForward_left_sound() {
        return forward_left_sound;
    }

    public ButtonImage getForward_right_sound() {
        return forward_right_sound;
    }
    public JButton getChange_password() {
        return change_password;
    }

    public JLabel getoption_game() {
        return option_game;
    }

    public JLabel getoption_account() {
        return option_account;
    }

    public JLabel getLogout_label() {
        return logout_label;
    }
}
