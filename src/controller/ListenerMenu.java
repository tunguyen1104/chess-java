package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.Menu;

public class ListenerMenu extends MouseAdapter{
    private Menu menu;
    public ListenerMenu(Menu menu) {
        this.menu = menu;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(menu.getNewGame())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
        } else if(e.getSource().equals(menu.getHistory())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "history");
        } else if(e.getSource().equals(menu.getPuzzle())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "puzzle");
        } else if(e.getSource().equals(menu.getSetting())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "setting");
        } else if(e.getSource().equals(menu.getPuzzle())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "listPuzzle");
        } else if(e.getSource().equals(menu.getAbout())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "about");
        } else if(e.getSource().equals(menu.getExit())) {
            int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else
                    Menu.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
