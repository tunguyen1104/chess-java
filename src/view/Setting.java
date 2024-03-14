package view;

import com.company.Main;
import model.JDBCConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Setting extends JPanel{
    private JFrame frame;
    private JPanel game;
    private JPanel account;
    private JPanel piece_panel;
    private JLabel piece_set;
    private JLabel board_label;
    private JLabel sound_label;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage home_normal;
    private BufferedImage piece_image;
    private JLabel title_bar_label;
    private JComboBox combo_piece;
    private JComboBox combo_board;
    private JComboBox combo_sound;
    private BufferedImage noavatar;
    private BufferedImage board_image;
    private BufferedImage icon_game;
    private BufferedImage option_setting_normal;
    private BufferedImage option_setting_selected;
    private BufferedImage option_settingv1;
    private BufferedImage option_settingv2;
    private BufferedImage logout_image;
    private JLabel option_setting_labelv1;
    private JLabel option_setting_labelv2;
    private JLabel logout_label;
    private Image board_image_cut;
    private boolean check_color;//nếu dang hover vào label -> true
    private JPanel board_panel;
    private JLabel name;
    private JLabel lever;
    private JLabel email;
    private JLabel changePassword;
    private JLabel newPassword;
    private JLabel retypeNewPassword;
    private JPasswordField newPasswordBox;
    private JPasswordField retypeNewPasswordBox;
    private JButton changePasswordButton;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private BufferedImage back_selected;
    private BufferedImage home_selected;
    String [] piece_url = {
            "src/res/pieces/default.png",
            "src/res/pieces/alpha.png",
            "src/res/pieces/anarcandy.png",
            "src/res/pieces/cardinal.png",
            "src/res/pieces/chessnut.png",
            "src/res/pieces/kiwen-suwi.png",
            "src/res/pieces/maestro.png",
            "src/res/pieces/tatiana.png"
    };
    String board_url[] = {
            "src/res/board/green.png","src/res/board/brown.png","src/res/board/tournament.png"
    };
    public Setting(){
        initPanel();
        frame = new JFrame();
        frame.setIconImage(icon_game);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-6,0);
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
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        // Load image
        try {
            noavatar = ImageIO.read(new File("C:\\Users\\Tu_Nguyen\\Downloads\\noavatar.gif"));
            option_setting_normal = ImageIO.read(new File("src/res/buttons/history_normal.png"));
            option_setting_selected = ImageIO.read(new File("src/res/buttons/history_selected.png"));
            option_settingv2 = option_setting_normal;
            option_settingv1 = option_setting_selected;
            logout_image = option_setting_normal;
            icon_game = ImageIO.read(new File("src/res/gui/icon_game.png"));
            board_image = ImageIO.read(new File(board_url[0]));
            int tilesize = board_image.getWidth() / 8;
            board_image_cut = board_image.getSubimage(0, 0, tilesize*6 , tilesize*3).getScaledInstance(tilesize*6, tilesize*3, BufferedImage.SCALE_SMOOTH);
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("src/res/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("src/res/buttons/home_selected.png"));
            piece_image = ImageIO.read(new File(piece_url[0]));
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        // Set preferred size of the panel to match background image size
        setPreferredSize(new Dimension(1600, 1000));
        //===========Title Bar============
        title_bar_label = new JLabel("Settings");
        title_bar_label.setBounds(720,0,400,60);
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
        //------------------------
        game = new JPanel();
        game.setBounds(500,180,540,420);
        game.setBackground(new Color(55,55,55));
        game.setLayout(null);
        this.add(game);
        //label piece set
        piece_set = new JLabel("Piece Set");
        piece_set.setBounds(50,20,90,40);
        piece_set.setForeground(Color.WHITE);
        piece_set.setFont(piece_set.getFont().deriveFont(20.0f));
        game.add(piece_set);
        //------------------------
        board_label = new JLabel("Board");
        board_label.setBounds(50,160,90,40);
        board_label.setForeground(Color.WHITE);
        board_label.setFont(board_label.getFont().deriveFont(20.0f));
        game.add(board_label);
        //------------------------
        sound_label = new JLabel("Sound");
        sound_label.setBounds(50,280,90,40);
        sound_label.setForeground(Color.WHITE);
        sound_label.setFont(sound_label.getFont().deriveFont(20.0f));
        game.add(sound_label);
        //------------------------
        //setting type pieces
        String piecesName[] = {
                "  Default","  Alpha","  Anarcandy","  Cardinal","  Chessnut","  Kiwen-suwi","  Maestro","  Tatiana"
        };
        combo_piece = new JComboBox(piecesName);
        combo_piece.setSelectedIndex(0);
        combo_piece.setBackground(new Color(27, 101, 106, 255));
        combo_piece.setForeground(Color.WHITE);
        combo_piece.setFocusable(false);
        combo_piece.setBounds(330,30,140,30);
        combo_piece.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndex = combo_piece.getSelectedIndex();
                    piece_image = ImageIO.read(new File(piece_url[selectedIndex]));
                    piece_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                    throw new RuntimeException(ex);
                }
            }
        });
        piece_panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(piece_image,6,5,140,50,game);
            }
        };
        piece_panel.setBackground(new Color(161, 150, 128));
        piece_panel.setBounds(160,20,150,60);
        game.add(piece_panel);
        game.add(combo_piece);

        String board[] = {
                "  Green","  Brown","  Tournament"
        };
        combo_board = new JComboBox(board);
        combo_board.setBackground(new Color(27, 101, 106, 255));
        combo_board.setFocusable(false);
        combo_board.setSelectedIndex(0);
        combo_board.setForeground(Color.WHITE);

        combo_board.setBounds(330, 160, 140, 30);
        game.add(combo_board);

        combo_board.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndex = combo_board.getSelectedIndex();
                    board_image = ImageIO.read(new File(board_url[selectedIndex]));
                    int tilesize = board_image.getWidth() / 8;
                    board_image_cut = board_image.getSubimage(0, 0, tilesize*6 , tilesize*3).getScaledInstance(tilesize*6, tilesize*3, BufferedImage.SCALE_SMOOTH);
                    board_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                    throw new RuntimeException(ex);
                }
            }
        });
        board_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(board_image_cut,0,0,140,70,board_panel);
            }
        };
        board_panel.setBounds(160,152,140,70);
        game.add(board_panel);
        String turn[] = {"  On","  Off"};
        combo_sound = new JComboBox(turn);
        combo_sound.setBackground(new Color(27, 101, 106, 255));
        combo_sound.setBounds(330,280,140,30);
        combo_sound.setFocusable(false);
        combo_sound.setSelectedIndex(0);
        combo_sound.setForeground(Color.WHITE);
        game.add(combo_sound);
        // option_setting
        option_setting_labelv1 = new JLabel("Game");
        option_setting_labelv1.setBounds(280,200,200,40);
        option_setting_labelv1.setFont(new Font("",Font.PLAIN,20));
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

        // account
        option_setting_labelv2 = new JLabel("Account");
        option_setting_labelv2.setBounds(280,250,200,40);
        option_setting_labelv2.setFont(new Font("",Font.PLAIN,20));
        option_setting_labelv2.setForeground(Color.WHITE);
        option_setting_labelv2.setHorizontalAlignment(SwingConstants.CENTER);
        account = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(noavatar,50,20,80,80,account);
            }
        };
        account.setBounds(500,180,540,420);
        account.setBackground(new Color(55,55,55));
        account.setLayout(null);
        account.setVisible(false);
        name = new JLabel("user2024");
        name.setBounds(140,20,90,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("",Font.PLAIN,20));
        account.add(name);
        lever = new JLabel("Lever 1");
        lever.setBounds(140,50,90,30);
        lever.setForeground(Color.WHITE);
        lever.setFont(new Font("",Font.PLAIN,14));
        account.add(lever);
        email = new JLabel("Email : ");
        email.setBounds(50,140,90,30);
        email.setForeground(Color.WHITE);
        email.setFont(new Font("",Font.PLAIN,18));
        account.add(email);
        changePassword = new JLabel("Change Password");
        changePassword.setBounds(50,200,200,30);
        changePassword.setForeground(Color.WHITE);
        changePassword.setFont(new Font("",Font.BOLD,20));
        account.add(changePassword);
        newPassword = new JLabel("New Password");
        newPassword.setBounds(50,260,200,20);
        newPassword.setForeground(Color.WHITE);
        newPassword.setFont(new Font("",Font.PLAIN,15));
        account.add(newPassword);
        retypeNewPassword = new JLabel("Retype New Password");
        retypeNewPassword.setBounds(50,320,200,20);
        retypeNewPassword.setForeground(Color.WHITE);
        retypeNewPassword.setFont(new Font("",Font.PLAIN,15));
        account.add(retypeNewPassword);
        newPasswordBox = new JPasswordField();
        newPasswordBox.setBounds(260,260,200,26);
        account.add(newPasswordBox);
        retypeNewPasswordBox = new JPasswordField();
        retypeNewPasswordBox.setBounds(260,320,200,26);
        account.add(retypeNewPasswordBox);
        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("",Font.BOLD,12));
        changePasswordButton.setBackground(new Color(140, 181, 90));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setBounds(50,360,150,32);
        account.add(changePasswordButton);
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
        logout_label = new JLabel("Log out");
        logout_label.setFont(new Font("",Font.PLAIN,20));
        logout_label.setForeground(Color.WHITE);
        logout_label.setHorizontalAlignment(SwingConstants.CENTER);
        logout_label.setBounds(280,300,200,40);
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
                //call database delete current user
                JDBCConnection.deleteDataCurrentUser();
                frame.dispose();
                new Login();
            }
        });
        this.add(logout_label);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(option_settingv1,280,200,200,40,this);
        g2d.drawImage(option_settingv2,280,250,200,40,this);
        g2d.drawImage(logout_image,280,300,200,40,this);
        g2d.drawImage(title_bar,530,10,450,42,this);
    }

    public static void main(String[] args) {
        new Setting();
    }
}
