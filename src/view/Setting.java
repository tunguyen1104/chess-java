package view;

import model.JDBCConnection;
import model.ReadImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private BufferedImage piece_image;
    private JLabel title_bar_label;
    private BufferedImage noavatar;
    private BufferedImage board_image;
    private BufferedImage option_setting_normal;
    private BufferedImage option_setting_selected;
    private BufferedImage option_settingv1;
    private BufferedImage option_settingv2;
    private BufferedImage logout_image;
    private JLabel option_setting_labelv1;
    private JLabel option_setting_labelv2;
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
    private BufferedImage forward_normal_piece;
    private BufferedImage forward_normal_piece_v2;
    private BufferedImage forward_selected_piece;
    private BufferedImage forward_selected_piece_v2;
    private int index_piece = 0;
    private ButtonImage forward_left_piece;
    private ButtonImage forward_right_piece;
    private BufferedImage forward_normal_board;
    private BufferedImage forward_normal_board_v2;
    private BufferedImage forward_selected_board;
    private BufferedImage forward_selected_board_v2;
    private int index_board = 0;
    private ButtonImage forward_left_board;
    private ButtonImage forward_right_board;
    private BufferedImage forward_normal_sound;
    private BufferedImage forward_normal_sound_v2;
    private BufferedImage forward_selected_sound;
    private BufferedImage forward_selected_sound_v2;
    private BufferedImage option_box_sound;
    private int index_sound = 0;
    private JLabel option_box_sound_label;
    private ButtonImage forward_left_sound;
    private ButtonImage forward_right_sound;
    private JButton save;
    private JButton change_password;
    private BufferedImage panel_options;
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
            "resources/board/blue.png", "resources/board/metal.png", "resources/board/wood.png"
    };
    String turn[] = { "On", "Off" };
    ArrayList<String> inforAccount = null;

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
            panel_options = ImageIO.read(new File("resources/gui/game_options_panel.png"));
            option_box_sound = ImageIO.read(new File("resources/gui/option_box.png"));
            forward_normal_piece = ImageIO.read(new File("resources/buttons/forward_normal.png"));
            forward_normal_piece_v2 = ImageIO.read(new File("resources/buttons/forward_normalv2.png"));
            forward_selected_piece = ImageIO.read(new File("resources/buttons/forward_selected.png"));
            forward_selected_piece_v2 = ImageIO.read(new File("resources/buttons/forward_selectedv2.png"));
            forward_normal_board = forward_normal_piece;
            forward_normal_board_v2 = forward_normal_piece_v2;
            forward_selected_board = forward_selected_piece;
            forward_selected_board_v2 = forward_selected_piece_v2;
            forward_normal_sound = forward_normal_piece;
            forward_normal_sound_v2 = forward_normal_piece_v2;
            forward_selected_sound = forward_selected_piece;
            forward_selected_sound_v2 = forward_selected_piece_v2;
            noavatar = ImageIO.read(new File("resources/gui/noavatar.png"));
            option_setting_normal = ImageIO.read(new File("resources/buttons/history_normal.png"));
            option_setting_selected = ImageIO.read(new File("resources/buttons/history_selected.png"));
            option_settingv2 = option_setting_normal;
            option_settingv1 = option_setting_selected;
            logout_image = option_setting_normal;
            String urlBoard = dataJDBC.get(1);
            if (dataJDBC != null)
                board_image = ImageIO.read(new File(urlBoard));
            else
                board_image = ImageIO.read(new File("resources/board/metal.png"));
            int tilesize = board_image.getWidth() / 8;
            board_image_cut = board_image.getSubimage(0, 0, tilesize * 6, tilesize * 3).getScaledInstance(tilesize * 6,
                    tilesize * 3, BufferedImage.SCALE_SMOOTH);
            piece_image = ImageIO.read(new File(dataJDBC.get(0)));
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        this.setPreferredSize(new Dimension(1536, 864));
        // ===========Title Bar============
        title_bar_label = new JLabel("Settings");
        title_bar_label.setBounds(720, 0, 400, 60);
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
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        // ------------------------
        initGame();
        initAccount();
        initLogout();
    }

    public void initGame() {
        game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(option_box_sound, 276, 280, 160, 32, game);
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
        forward_left_piece = new ButtonImage(forward_normal_piece, forward_selected_piece, 32, 32, "");
        forward_right_piece = new ButtonImage(forward_normal_piece_v2, forward_selected_piece_v2, 32, 32, "");
        forward_left_piece.setBounds(240, 40, 32, 32);
        forward_right_piece.setBounds(440, 40, 32, 32);
        for (int i = 0; i < piece_url.length; ++i) {
            if (piece_url[i].equals(dataJDBC.get(0))) {
                index_piece = i;
                break;
            }
        }
        forward_left_piece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_piece;
                if (index_piece < 0)
                    index_piece = piece_url.length - 1;
                try {
                    piece_image = ImageIO.read(new File(piece_url[index_piece]));
                    piece_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                }
            }
        });

        forward_right_piece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_piece;
                if (index_piece > piece_url.length - 1)
                    index_piece = 0;
                try {
                    piece_image = ImageIO.read(new File(piece_url[index_piece]));
                    piece_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                }
            }
        });
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
        forward_left_board = new ButtonImage(forward_normal_board, forward_selected_board, 32, 32, "");
        forward_right_board = new ButtonImage(forward_normal_board_v2, forward_selected_board_v2, 32, 32, "");
        forward_left_board.setBounds(240, 160, 32, 32);
        forward_right_board.setBounds(440, 160, 32, 32);
        forward_left_board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
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
            }
        });
        forward_right_board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
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
            }
        });
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
        forward_left_sound = new ButtonImage(forward_normal_sound, forward_selected_sound, 32, 32, "");
        forward_right_sound = new ButtonImage(forward_normal_sound_v2, forward_selected_sound_v2, 32, 32, "");
        forward_left_sound.setBounds(240, 280, 32, 32);
        forward_right_sound.setBounds(440, 280, 32, 32);
        forward_left_sound.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                --index_sound;
                if (index_sound < 0)
                    index_sound = 1;
                option_box_sound_label.setText(turn[index_sound]);
            }
        });
        forward_right_sound.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ++index_sound;
                if (index_sound > 1)
                    index_sound = 0;
                option_box_sound_label.setText(turn[index_sound]);
            }
        });
        game.add(forward_left_sound);
        game.add(forward_right_sound);
        game.add(option_box_sound_label);
        //
        save = new JButton("Save");
        save.setFont(new Font("", Font.PLAIN, 18));
        save.setBackground(new Color(38, 117, 191));
        save.setForeground(Color.WHITE);
        save.setFocusPainted(false);
        save.setBounds(300, 350, 120, 35);
        game.add(save);
        // option_setting
        option_setting_labelv1 = new JLabel("Game");
        option_setting_labelv1.setBounds(280, 200, 200, 40);
        option_setting_labelv1.setFont(new Font("", Font.PLAIN, 20));
        option_setting_labelv1.setForeground(Color.WHITE);
        option_setting_labelv1.setHorizontalAlignment(SwingConstants.CENTER);
        option_setting_labelv1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                option_setting_labelv1.setForeground(new Color(140, 181, 90));
                check_color = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                option_setting_labelv1.setForeground(Color.WHITE);
                check_color = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                option_setting_labelv1.setForeground(new Color(47, 53, 62));
                logout_image = option_setting_normal;
                option_settingv2 = option_setting_normal;
                option_settingv1 = option_setting_selected;
                game.setVisible(true);
                account.setVisible(false);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (check_color) {
                    option_setting_labelv1.setForeground(new Color(140, 181, 90));
                } else {
                    option_setting_labelv1.setForeground(Color.WHITE);
                }
            }
        });
        // setup button save
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JDBCConnection.updateDataSetting(piece_url[index_piece], board_url[index_board],
                        (index_sound == 0) ? true : false);
                JOptionPane.showMessageDialog(null, "Your settings have been saved", "Notification",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void initLogout() {
        logout_label = new JLabel("Log out");
        logout_label.setFont(new Font("", Font.PLAIN, 20));
        logout_label.setForeground(Color.WHITE);
        logout_label.setHorizontalAlignment(SwingConstants.CENTER);
        logout_label.setBounds(280, 300, 200, 40);
        logout_label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                logout_label.setForeground(new Color(140, 181, 90));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                logout_label.setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent me) {
                // call database delete current user
                JDBCConnection.logOut();
                JDBCConnection.deleteDataCurrentUser();
                Menu.frame.dispose();
                new Login();
            }
        });
        this.add(logout_label);
    }

    public void initAccount() {
        // account
        option_setting_labelv2 = new JLabel("Account");
        option_setting_labelv2.setBounds(280, 250, 200, 40);
        option_setting_labelv2.setFont(new Font("", Font.PLAIN, 20));
        option_setting_labelv2.setForeground(Color.WHITE);
        option_setting_labelv2.setHorizontalAlignment(SwingConstants.CENTER);
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
        newPasswordBox.setBounds(260, 260, 200, 26);
        account.add(newPasswordBox);
        retypeNewPasswordBox = new JPasswordField();
        retypeNewPasswordBox.setBounds(260, 320, 200, 26);
        account.add(retypeNewPasswordBox);
        change_password = new JButton("Change Password");
        change_password.setFont(new Font("", Font.PLAIN, 16));
        change_password.setBackground(new Color(80, 161, 191));
        change_password.setForeground(Color.WHITE);
        change_password.setFocusPainted(false);
        change_password.setBounds(50, 360, 180, 40);
        change_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
        account.add(change_password);
        option_setting_labelv2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                option_setting_labelv2.setForeground(new Color(140, 181, 90));
                check_color = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                option_setting_labelv2.setForeground(Color.WHITE);
                check_color = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                option_setting_labelv2.setForeground(new Color(47, 53, 62));
                logout_image = option_setting_normal;
                option_settingv1 = option_setting_normal;
                option_settingv2 = option_setting_selected;
                game.setVisible(false);
                account.setVisible(true);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (check_color) {
                    option_setting_labelv2.setForeground(new Color(140, 181, 90));
                } else {
                    option_setting_labelv2.setForeground(Color.WHITE);
                }
            }
        });
        this.add(option_setting_labelv1);
        this.add(option_setting_labelv2);
        this.add(account);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(option_settingv1, 280, 200, 200, 40, this);
        g2d.drawImage(option_settingv2, 280, 250, 200, 40, this);
        g2d.drawImage(logout_image, 280, 300, 200, 40, this);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(panel_options, 500, 174, 540, 420, this);
    }
}
