package model;

import view.Menu;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

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
                String _piece;
                String _board_name;
                Boolean _sound;
                String _puzzle_failed;
                String _puzzle_solved;
                while (rs.next()) {
                    _username = rs.getString(1);
                    _password = rs.getString(2);
                    _email = rs.getString(3);
                    _piece = rs.getString(4);
                    _board_name = rs.getString(5);
                    _sound = rs.getBoolean(6);
                    _puzzle_failed = rs.getString(7);
                    _puzzle_solved = rs.getString(8);
                    if (username.equals(_username) && password.equals(_password)) {
                        rs.close();
                        preparedStatement.close();
                        preparedStatement = conn.prepareStatement(
                                "INSERT INTO CURRENTUSER(USERNAME, PASSWORD, EMAIL, PIECE, BOARD_NAME, SOUND, PUZZLE_FAILED, PUZZLE_SOLVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                        preparedStatement.setString(1, _username);
                        preparedStatement.setString(2, _password);
                        preparedStatement.setString(3, _email);
                        preparedStatement.setString(4, _piece);
                        preparedStatement.setString(5, _board_name);
                        preparedStatement.setBoolean(6, _sound);
                        preparedStatement.setString(7, _puzzle_failed);
                        preparedStatement.setString(8, _puzzle_solved);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        conn.close();
                        return true;
                    }
                }
                JOptionPane.showMessageDialog(null, "Username or password is incorrect", "Message",
                        JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Account already exists!", "Message",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                PreparedStatement insertStatement = conn.prepareStatement(
                        "INSERT INTO ACCOUNT(USERNAME, PASSWORD, EMAIL, PIECE, BOARD_NAME, SOUND, PUZZLE_FAILED, PUZZLE_SOLVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.setString(4, "resources/pieces/maestro.png");
                insertStatement.setString(5, "resources/board/metal.png");
                insertStatement.setBoolean(6, true);
                insertStatement.setString(7, "");
                insertStatement.setString(8, "");
                insertStatement.executeUpdate();
                insertStatement = conn.prepareStatement(
                        "INSERT INTO CURRENTUSER(USERNAME, PASSWORD, EMAIL, PIECE, BOARD_NAME, SOUND, PUZZLE_FAILED, PUZZLE_SOLVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.setString(4, "resources/pieces/maestro.png");
                insertStatement.setString(5, "resources/board/metal.png");
                insertStatement.setBoolean(6, true);
                insertStatement.setString(7, "");
                insertStatement.setString(8, "");
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
            JOptionPane.showMessageDialog(null, "Connection to database failed!", "Message",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection to database failed!");
        }
    }

    public static boolean checkCurrentUser() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT username FROM CURRENTUSER");
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    rs.getString("username");
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

    public static ArrayList<String> takeDataSetting() {
        ArrayList<String> arr = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = JDBCConnection.getJDBCConnection();
            statement = conn.prepareStatement("SELECT PIECE, BOARD_NAME, SOUND FROM CURRENTUSER");
            rs = statement.executeQuery();
            if (rs.next()) {
                String piece_url = rs.getString("piece");
                String board_url = rs.getString("board_name");
                Boolean sound = rs.getBoolean("sound");
                arr = new ArrayList<String>();
                arr.add(piece_url);
                arr.add(board_url);
                arr.add(sound ? "1" : "0");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        return arr;
    }

    public static ArrayList<String> takeDataPuzzle() {
        ArrayList<String> arr = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = JDBCConnection.getJDBCConnection();
            statement = conn.prepareStatement("SELECT PUZZLE_FAILED, PUZZLE_SOLVED FROM CURRENTUSER");
            rs = statement.executeQuery();
            if (rs.next()) {
                String puzzle_failed = rs.getString("puzzle_failed");
                String puzzle_solved = rs.getString("puzzle_solved");
                arr = new ArrayList<String>();
                arr.add(puzzle_failed);
                arr.add(puzzle_solved);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        return arr;
    }

    public static void updateDataSetting(String piece_url, String board_url, Boolean sound) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = JDBCConnection.getJDBCConnection();
            statement = conn.prepareStatement("UPDATE CURRENTUSER SET PIECE = ?, BOARD_NAME = ?, SOUND = ?");
            statement.setString(1, piece_url);
            statement.setString(2, board_url);
            statement.setBoolean(3, sound);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

    public static void updateDataPuzzle(String puzzle_failed, String puzzle_solved) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = JDBCConnection.getJDBCConnection();
            statement = conn.prepareStatement("UPDATE CURRENTUSER SET PUZZLE_FAILED = ?, PUZZLE_SOLVED = ?");
            statement.setString(1, puzzle_failed);
            statement.setString(2, puzzle_solved);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }
}
