package com.company;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import model.JDBCConnection;
import view.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        boolean ok = false;
        try {
            ok = JDBCConnection.checkCurrentUser();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (ok) {
            new Menu();
        } else
            new Login();
    }
}
