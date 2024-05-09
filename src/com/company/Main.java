package com.company;

import javax.swing.*;

import com.formdev.flatlaf.FlatIntelliJLaf;
import model.JDBCConnection;
import view.*;
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        if (JDBCConnection.checkCurrentUser()) {
            new Menu();
        } else
            new Login();
    }
}
