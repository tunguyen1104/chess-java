package model;

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
        if(conn != null) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM account");
                ResultSet rs = preparedStatement.executeQuery();
                String _username;
                String _password;
                String _email;
                while(rs.next()) {
                    _username = rs.getString(1);
                    _password = rs.getString(2);
                    _email = rs.getString(3);
                    if (username.equals(_username) && password.equals(_password)) {
                        preparedStatement.close();
                        conn.close();
                        return true;
                    }
                }
                JOptionPane.showMessageDialog(null, "Username or password is incorrect","Message", JOptionPane.WARNING_MESSAGE);
                preparedStatement.close();
                conn.close();
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Connection database failed!","Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection database failed!");
        }
        return false;
    }
    public static boolean createAccount(String username, String password, String email) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if(conn != null) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM account");
                ResultSet rs = preparedStatement.executeQuery();
                String _username;
                String _password;
                String _email;
                while(rs.next()) {
                    _username = rs.getString(1);
                    _password = rs.getString(2);
                    _email = rs.getString(3);
                    if (username.equals(_username) || email.equals(_email)) {
                        preparedStatement.close();
                        conn.close();
                        JOptionPane.showMessageDialog(null, "Account already exist!","Message", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                preparedStatement.executeUpdate("INSERT INTO ACCOUNT(USERNAME,PASSWORD,EMAIL) VALUES ('" + username + "','" + password + "','" + email + "')");
                preparedStatement.close();
                conn.close();
                return true;
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Connection database failed!","Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection database failed!");
        }
        return false;
    }
}
