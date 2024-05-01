package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.GameOptions;
import view.Menu;

public class ListenerGameOptions extends MouseAdapter implements ActionListener {
    private GameOptions gameOptions;
    public ListenerGameOptions(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(gameOptions.getBack_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "menu");
        } else if(e.getSource().equals(gameOptions.getHome_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "menu");
        } else if(e.getSource().equals(gameOptions.getForward_left_game())) {
            gameOptions.handle_forward_left_game();
        } else if(e.getSource().equals(gameOptions.getForward_right_game())) {
            gameOptions.handle_forward_right_game();
        } else if(e.getSource().equals(gameOptions.getForward_left_time())) {
            gameOptions.handle_forward_left_time();
        } else if(e.getSource().equals(gameOptions.getForward_right_time())) {
            gameOptions.handle_forward_right_time();
        }else if(e.getSource().equals(gameOptions.getForward_left_side())) {
            gameOptions.handle_forward_left_side();
        } else if(e.getSource().equals(gameOptions.getForward_right_side())) {
            gameOptions.handle_forward_right_side();
        }else if(e.getSource().equals(gameOptions.getForward_left_lever())) {
            gameOptions.handle_forward_left_lever();
        } else if(e.getSource().equals(gameOptions.getForward_right_lever())) {
            gameOptions.handle_forward_right_lever();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameOptions.handle_go();
    }
}
