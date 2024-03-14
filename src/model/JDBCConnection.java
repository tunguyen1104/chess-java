package model;

import view.Menu;

import javax.swing.*;
import java.sql.*;

public class JDBCConnection {
    private static final String url = "jdbc:mysql://localhost:3306/chess?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "admin";

    public static Connection getJDBCConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return (Connection) DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static boolean checkValidAccount(String username, String password) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM account");
                ResultSet rs = preparedStatement.executeQuery();
                String _username;
                String _password;
                String _email;
                while (rs.next()) {
                    _username = rs.getString(1);
                    _password = rs.getString(2);
                    _email = rs.getString(3);
                    if (username.equals(_username) && password.equals(_password)) {
                        rs.close();
                        preparedStatement.close();
                        preparedStatement = conn.prepareStatement(
                                "INSERT INTO CURRENTUSER(USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
                        preparedStatement.setString(1, _username);
                        preparedStatement.setString(2, _password);
                        preparedStatement.setString(3, _email);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        conn.close();
                        return true;
                    }
                }
                JOptionPane.showMessageDialog(null, "Username or password is incorrect", "Message", JOptionPane.WARNING_MESSAGE);
                rs.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Connection database failed!", "Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection database failed!");
        }
        return false;
    }

    public static boolean createAccount(String username, String password, String email) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                PreparedStatement checkStatement = conn.prepareStatement(
                        "SELECT * FROM ACCOUNT WHERE USERNAME = ? OR EMAIL = ?");
                checkStatement.setString(1, username);
                checkStatement.setString(2, email);
                ResultSet rs = checkStatement.executeQuery();
                if (rs.next()) {
                    rs.close();
                    checkStatement.close();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Account already exists!", "Message", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                PreparedStatement insertStatement = conn.prepareStatement(
                        "INSERT INTO ACCOUNT(USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.executeUpdate();
                insertStatement = conn.prepareStatement(
                        "INSERT INTO CURRENTUSER(USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.executeUpdate();
                rs.close();
                checkStatement.close();
                insertStatement.close();
                conn.close();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Connection database failed!", "Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection database failed!");
        }
        return false;
    }

    public static void deleteDataCurrentUser() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("TRUNCATE TABLE CURRENTUSER");
                preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Connection to database failed!", "Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection to database failed!");
        }
    }

    public static boolean checkCurrentUser() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
                try {
                    PreparedStatement statement = conn.prepareStatement("SELECT username, password, email FROM CURRENTUSER");
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        rs.close();
                        statement.close();
                        return true;
                    }
                    return false;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Services not turned!");
        }
        return false;
    }
}
