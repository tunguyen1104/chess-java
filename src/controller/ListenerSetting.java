package controller;

import view.Menu;
import view.Setting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListenerSetting extends MouseAdapter implements ActionListener {
    private Setting setting;
    public ListenerSetting(Setting setting) {
        this.setting = setting;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(setting.getBack_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "menu");
        } else if(e.getSource().equals(setting.getHome_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "menu");
        } else if(e.getSource().equals(setting.getoption_game())) {
            setting.handle_option_game_mouse_pressed();
        } else if(e.getSource().equals(setting.getoption_account())) {
            setting.option_account_pressed();
        } else if(e.getSource().equals(setting.getLogout_label())) {
            setting.handle_logout_label_pressed();
        } else if(e.getSource().equals(setting.getForward_left_piece())) {
            setting.handle_forward_left_piece();
        } else if(e.getSource().equals(setting.getForward_right_piece())) {
            setting.handle_forward_right_piece();
        }else if(e.getSource().equals(setting.getForward_left_board())) {
            setting.handle_forward_left_board();
        } else if(e.getSource().equals(setting.getForward_right_board())) {
            setting.handle_forward_right_board();
        }else if(e.getSource().equals(setting.getForward_left_sound())) {
            setting.handle_forward_left_sound();
        } else if(e.getSource().equals(setting.getForward_right_sound())) {
            setting.handle_forward_right_sound();
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(setting.getoption_game())) {
            setting.handle_option_game_mouse_entered();
        } else if(e.getSource().equals(setting.getoption_account())) {
            setting.option_account_entered();
        } else if(e.getSource().equals(setting.getLogout_label())) {
            setting.handle_logout_label_entered();
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(setting.getoption_game())) {
            setting.handle_option_game_mouse_exited();
        } else if(e.getSource().equals(setting.getoption_account())) {
            setting.option_account_exited();
        } else if(e.getSource().equals(setting.getLogout_label())) {
            setting.handle_logout_label_exited();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource().equals(setting.getoption_game())) {
            setting.handle_option_game_mouse_released();
        } else if(e.getSource().equals(setting.getoption_account())) {
            setting.option_account_released();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(setting.getSave())) {
            setting.handle_save();
        } else if(e.getSource().equals(setting.getChange_password())) {
            setting.handle_change_password();
        }
    }
}
