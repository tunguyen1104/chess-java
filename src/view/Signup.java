package view;

import model.JDBCConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends JPanel implements ActionListener {
    private JLabel signup_header;
    private JLabel username;
    private JLabel password;
    private JLabel email;
    private JLabel you_have_account;
    private JTextField _username;
    private JPasswordField _password;
    private JTextField _email;
    private JButton signup_button;
    private JButton switch_to_login;
    // regex email
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Signup() {
        this.setPreferredSize(new Dimension(400, 500));
        this.setLayout(null);
        signup_header = new JLabel("SIGNUP");
        signup_header.setBounds(150, 40, 200, 50);
        signup_header.setFont(new Font("",Font.BOLD,30));
        username = new JLabel("Username");
        username.setBounds(50, 110, 100, 30);

        email = new JLabel("Email");
        email.setBounds(50, 190, 100, 30);

        password = new JLabel("Password");
        password.setBounds(50, 270, 100, 30);

        _username = new JTextField();
        _username.setBounds(50, 140, 300, 40);
        _email = new JTextField();
        _email.setBounds(50, 220, 300, 40);

        _password = new JPasswordField();
        _password.setBounds(50, 300, 300, 40);

        signup_button = new JButton("Signup");
        signup_button.setBounds(50, 360, 80, 30);
        signup_button.setForeground(Color.WHITE);
        signup_button.setBackground(new Color(140, 181, 90));
        signup_button.setFocusPainted(false);
        you_have_account = new JLabel("I have an account");
        you_have_account.setBounds(50, 420, 200, 25);
        switch_to_login = new JButton("Login");
        switch_to_login.setBounds(180, 410, 80, 30);
        switch_to_login.setFocusPainted(false);
        this.add(signup_header);
        this.add(username);
        this.add(password);
        this.add(_username);
        this.add(email);
        this.add(_email);
        this.add(_password);
        this.add(signup_button);
        this.add(switch_to_login);
        this.add(you_have_account);

        signup_button.addActionListener(this);
        switch_to_login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signup_button) {
            String username = _username.getText().trim();
            char[] password_char = _password.getPassword();
            String password = new String(password_char).trim();
            String email = _email.getText().trim();
            if (username.equals("")) {
                JOptionPane.showMessageDialog(null, "username is blank", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (email.equals("")) {
                JOptionPane.showMessageDialog(null, "email is blank", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "password is blank", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (username.length() < 8) {
                JOptionPane.showMessageDialog(null, "username < 8", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(null, "password < 8", "Message", JOptionPane.WARNING_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email!", "Message", JOptionPane.WARNING_MESSAGE);
            } else {
                if (JDBCConnection.createAccount(username, password, email)) {
                    JOptionPane.showMessageDialog(null, "Registered successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    Login.frame.dispose();
                    new Menu();
                }
            }
        } else if (e.getSource() == switch_to_login) {
            Login.cardLayout.show(Login.panel, "login");
        }
    }
}
