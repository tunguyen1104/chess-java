package com.company;

import model.JDBCConnection;
import view.Login;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        boolean ok = false;
        try {
            ok = JDBCConnection.checkCurrentUser();
        }catch (Exception e){
            System.out.println(e);
        }
        if(ok) new Menu();
        else new Login();
    }
}
