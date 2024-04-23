package view;

import model.JDBCConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login extends JFrame implements ActionListener {
    public JPanel panel_login;
    public static JPanel panel = new JPanel();
    private JLabel login_header;
    private JLabel username;
    private JLabel password;
    private JLabel do_not_have_an_account;
    public static JTextField _username;
    public static JPasswordField _password;
    private JButton login_button;
    private JButton switch_to_signup;
    private BufferedImage icon_game;
    private BufferedImage originalImage;
    public static BufferedImage eye;
    public static BufferedImage eye_hide;
    private Boolean hide = true;
    private JLabel eye_label;
    public static CardLayout cardLayout = new CardLayout();
    private ImageIcon icon_loading;
    private JLabel loading;
    private final Color color_back_ground_root = this.getBackground();

    public Login() {
        try {
            eye = ImageIO.read(new File("resources/buttons/eye.png"));
            eye_hide = ImageIO.read(new File("resources/buttons/eye_hide.png"));
            icon_game = ImageIO.read(new File("resources/gui/icon_game.png"));
            originalImage = ImageIO.read(new File("resources/gui/bg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setTitle("CHESS");
        this.setIconImage(icon_game);
        this.setLayout(null);
        panel.setBounds(400, 0, 400, 500);
        panel_login = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paint(g2d);
                g2d.drawImage(hide ? eye_hide : eye, 310, 232, 16, 16, panel_login);
            }
        };
        panel_login.setLayout(null);
        panel_login.setPreferredSize(new Dimension(400, 500));
        login_header = new JLabel("LOGIN");
        login_header.setBounds(150, 30, 100, 50);
        login_header.setFont(new Font("", Font.BOLD, 30));
        username = new JLabel("Username");
        username.setBounds(50, 100, 100, 30);
        password = new JLabel("Password");
        password.setBounds(50, 190, 100, 30);

        _username = new JTextField();
        _username.setBounds(50, 130, 300, 40);
        _password = new JPasswordField();
        _password.setBounds(50, 220, 300, 40);
        _password.setEchoChar('*');
        login_button = new JButton("Login");
        login_button.setBounds(50, 280, 80, 30);
        login_button.setBackground(new Color(140, 181, 90));
        login_button.setForeground(Color.WHITE);
        login_button.setFocusPainted(false);
        do_not_have_an_account = new JLabel("I don't have an account");
        do_not_have_an_account.setBounds(50, 340, 200, 25);
        switch_to_signup = new JButton("Signup");
        switch_to_signup.setBounds(200, 330, 80, 30);
        switch_to_signup.setFocusPainted(false);
        eye_label = new JLabel();
        eye_label.setBounds(310, 232, 16, 16);
        eye_label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hide = !hide;
                if (hide) {
                    _password.setEchoChar('*');
                } else {
                    _password.setEchoChar((char) 0);
                }
                panel_login.repaint();
            }
        });
        panel_login.add(eye_label);
        panel_login.add(login_header);
        panel_login.add(username);
        panel_login.add(password);
        panel_login.add(_username);
        panel_login.add(_password);
        panel_login.add(login_button);
        panel_login.add(switch_to_signup);
        panel_login.add(do_not_have_an_account);
        panel.setLayout(cardLayout);
        panel.add(panel_login, "login");
        panel.add(new Signup(), "signup");
        panel.setBounds(400, 0, 400, 500);
        login_button.addActionListener(this);
        switch_to_signup.addActionListener(this);
        this.add(panel);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        });
    }

    public void load() {
        icon_loading = new ImageIcon("resources/gui/loading.gif");
        loading = new JLabel(icon_loading);
        loading.setBounds(180, 180, 28, 28);
        loading.setOpaque(false);
        panel_login.add(loading);
        panel_login.setBackground(new Color(255, 255, 255));
        panel_login.repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.drawImage(originalImage, 0, 0, 400, 500, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(login_button)) {
            load();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    panel_login.remove(loading);
                    panel_login.setBackground(color_back_ground_root);
                    panel_login.repaint();

                    String username = _username.getText().trim();
                    char[] password_char = _password.getPassword();
                    String password = new String(password_char).trim();
                    if (username.equals("")) {
                        JOptionPane.showMessageDialog(null, "username is blank", "Message",
                                JOptionPane.WARNING_MESSAGE);
                        _username.requestFocus();
                    } else if (password.equals("")) {
                        JOptionPane.showMessageDialog(null, "password is blank", "Message",
                                JOptionPane.WARNING_MESSAGE);
                        _password.requestFocus();
                    } else if (username.length() < 8) {
                        JOptionPane.showMessageDialog(null, "username < 8", "Message", JOptionPane.WARNING_MESSAGE);
                        _username.requestFocus();
                    } else if (password.length() < 8) {
                        JOptionPane.showMessageDialog(null, "password < 8", "Message", JOptionPane.WARNING_MESSAGE);
                        _password.requestFocus();
                    } else {
                        if (JDBCConnection.checkValidAccount(username, password)) {
                            JOptionPane.showMessageDialog(null, "Login successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new Menu();
                        }
                    }
                }
            };
            thread.start();
        }
        if (e.getSource().equals(switch_to_signup)) {
            cardLayout.show(panel, "signup");
        }
    }
}
