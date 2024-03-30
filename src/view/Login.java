package view;

import model.JDBCConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login extends JPanel implements ActionListener {
    public static JFrame frame;
    public static JPanel panel = new JPanel();
    private JLabel login_header;
    private JLabel username;
    private JLabel password;
    private JLabel do_not_have_an_account;
    private JTextField _username;
    private JPasswordField _password;
    private JButton login_button;
    private JButton switch_to_signup;
    private BufferedImage icon_game;
    public static CardLayout cardLayout = new CardLayout();

    public Login() {
        frame = new JFrame("CHESS");
        frame.setLayout(new GridLayout(0, 2));
        try {
            icon_game = ImageIO.read(new File("resources/gui/icon_game.png"));
            BufferedImage originalImage = ImageIO.read(new File("resources/gui/bg.jpg"));
            Image scaledImage = originalImage.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel bg = new JLabel(imageIcon);
            frame.add(bg, BorderLayout.WEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        frame.setIconImage(icon_game);
        panel.setLayout(cardLayout);
        panel.add(this, "login");
        panel.add(new Signup(), "signup");
        frame.add(panel);
        this.setPreferredSize(new Dimension(400, 500));
        this.setLayout(null);
        login_header = new JLabel("LOGIN");
        login_header.setBounds(150, 30, 100, 50);
        login_header.setFont(login_header.getFont().deriveFont(30.0f));
        username = new JLabel("Username");
        username.setBounds(50, 100, 100, 30);

        password = new JLabel("Password");
        password.setBounds(50, 190, 100, 30);

        _username = new JTextField();
        _username.setBounds(50, 130, 300, 40);
        _password = new JPasswordField();
        _password.setBounds(50, 220, 300, 40);

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
        this.add(login_header);
        this.add(username);
        this.add(password);
        this.add(_username);
        this.add(_password);
        this.add(login_button);
        this.add(switch_to_signup);
        this.add(do_not_have_an_account);
        frame.add(panel, BorderLayout.EAST);

        login_button.addActionListener(this);
        switch_to_signup.addActionListener(this);

        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login_button) {
            String username = _username.getText().trim();
            char[] password_char = _password.getPassword();
            String password = new String(password_char).trim();
            if (username.equals("")) {
                JOptionPane.showMessageDialog(null, "username is blank", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "password is blank", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (username.length() < 8) {
                JOptionPane.showMessageDialog(null, "username < 8", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(null, "password < 8", "Message", JOptionPane.WARNING_MESSAGE);
            } else {
                if (JDBCConnection.checkValidAccount(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new Menu();
                }
            }
        }
        if (e.getSource() == switch_to_signup) {
            cardLayout.show(panel, "signup");
        }
    }
}
