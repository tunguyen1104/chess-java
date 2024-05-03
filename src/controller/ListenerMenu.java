package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.Menu;

public class ListenerMenu extends MouseAdapter{
    private Menu menu;
    public ListenerMenu(Menu menu) {
        this.menu = menu;
    }
    @Override
    public void mousePressed(MouseEvent e) {
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
            System.exit(0);
        }
    }
}
