package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;
            try {
                preparedStatement = conn.prepareStatement("SELECT * FROM account");
                rs = preparedStatement.executeQuery();
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
                        preparedStatement = conn.prepareStatement(
                                "INSERT INTO CURRENTUSER(USERNAME) VALUES (?)");
                        preparedStatement.setString(1, _username);
                        preparedStatement.executeUpdate();
                        return true;
                    }
                }
                JOptionPane.showMessageDialog(null, "Username or password is incorrect", "Message",
                        JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                    if (rs != null)
                        rs.close();
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } else {
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
                        "INSERT INTO CURRENTUSER(USERNAME) VALUES (?)");
                insertStatement.setString(1, username);
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
            JOptionPane.showMessageDialog(null, "Connection database failed!", "Message", JOptionPane.WARNING_MESSAGE);
            System.out.println("Connection database failed!");
        }
        return false;
    }

    public static void deleteDataCurrentUser() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement("TRUNCATE TABLE CURRENTUSER");
                preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }
    public static boolean checkCurrentUser() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM CURRENTUSER");
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    rs.close();
                    statement.close();
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else
            System.out.println("Services not turned!");
        return false;
    }

    public static ArrayList<String> takeInforAccount() {
        ArrayList<String> arr = null;
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement("SELECT ACCOUNT.USERNAME, PASSWORD, EMAIL FROM ACCOUNT INNER JOIN CURRENTUSER ON ACCOUNT.USERNAME = CURRENTUSER.USERNAME");
                rs = statement.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    arr = new ArrayList<String>();
                    arr.add(username);
                    arr.add(password);
                    arr.add(email);
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
        return arr;
    }

    public static ArrayList<String> takeDataSetting() {
        ArrayList<String> arr = null;
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement("SELECT PIECE, BOARD_NAME, SOUND FROM ACCOUNT INNER JOIN CURRENTUSER ON ACCOUNT.USERNAME = CURRENTUSER.USERNAME");
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
        return arr;
    }

    public static ArrayList<String> takeDataPuzzle() {
        ArrayList<String> arr = null;
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement("SELECT PUZZLE_FAILED, PUZZLE_SOLVED FROM ACCOUNT INNER JOIN CURRENTUSER ON ACCOUNT.USERNAME = CURRENTUSER.USERNAME");
                rs = statement.executeQuery();
                if (rs.next()) {
                    String puzzle_failed = rs.getString("puzzle_failed");
                    String puzzle_solved = rs.getString("puzzle_solved");
                    arr = new ArrayList<String>();
                    if (puzzle_failed != null) {
                        arr.add(puzzle_failed);
                    } else {
                        arr.add("");
                    }
                    if (puzzle_solved != null) {
                        arr.add(puzzle_solved);
                    } else {
                        arr.add("");
                    }
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
        return arr;
    }
    public static ArrayList<String> takeDataHistory() {
        ArrayList<String> arr = new ArrayList<String>();
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement("SELECT HISTORY FROM HISTORY INNER JOIN CURRENTUSER ON HISTORY.USERNAME = CURRENTUSER.USERNAME ORDER BY id DESC");
                rs = statement.executeQuery();
                while (rs.next()) {
                    arr.add(rs.getString("history"));
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
        return arr;
    }
    public static String takeHistoryTheEnd() {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement("SELECT HISTORY FROM HISTORY INNER JOIN CURRENTUSER ON HISTORY.USERNAME = CURRENTUSER.USERNAME ORDER BY id DESC");
                rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getString("history");
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
        return "";
    }
    public static void insertHistory(String data) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement("INSERT INTO HISTORY (username, history) VALUES ((SELECT username FROM CURRENTUSER), ?)");
                statement.setString(1, data);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }
    public static void updatePiece(String piece_url) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement("UPDATE ACCOUNT SET PIECE = ? WHERE USERNAME = (SELECT USERNAME FROM CURRENTUSER)");
                statement.setString(1, piece_url);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }
    public static void updateBoard(String board_url) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement("UPDATE ACCOUNT SET BOARD_NAME = ? WHERE USERNAME = (SELECT USERNAME FROM CURRENTUSER)");
                statement.setString(1, board_url);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }
    public static void updateSound(Boolean sound) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement("UPDATE ACCOUNT SET SOUND = ? WHERE USERNAME = (SELECT USERNAME FROM CURRENTUSER)");
                statement.setBoolean(1, sound);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }

    public static void updateDataPuzzle(String puzzle_failed, String puzzle_solved) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement("UPDATE ACCOUNT SET PUZZLE_FAILED = ?, PUZZLE_SOLVED = ? WHERE USERNAME = (SELECT USERNAME FROM CURRENTUSER)");
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
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            System.out.println("Connection to database failed!");
        }
    }

     public static void updatePassword(String new_password) {
         Connection conn = JDBCConnection.getJDBCConnection();
         if (conn != null) {
             PreparedStatement statement = null;
             try {
                 statement = conn.prepareStatement("UPDATE ACCOUNT SET PASSWORD = ? WHERE USERNAME = (SELECT USERNAME FROM CURRENTUSER)");
                 statement.setString(1, new_password);
                 statement.executeUpdate();
             } catch (SQLException ex) {
                 throw new RuntimeException(ex);
             } finally {
                 try {
                     if (statement != null) {
                         statement.close();
                     }
                     conn.close();
                 } catch (SQLException ex) {
                     System.err.println(ex);
                 }
             }
         }
         else System.out.println("Connection to database failed!");
     }
}
