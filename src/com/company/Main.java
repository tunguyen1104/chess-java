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
