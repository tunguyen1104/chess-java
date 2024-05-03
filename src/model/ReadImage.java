package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ReadImage {
    public static BufferedImage piece;
    public static Image loading;
    public static Image title_bar;
    public static Image back_normal;
    public static Image home_normal;
    public static Image back_selected;
    public static Image home_selected;
    public static Image game_gui;
    public static Image rotate_normal;
    public static Image rotate_selected;
    public static Image board_index_black;
    public static Image board_index;
    public static Image first_normal;
    public static Image first_selected;
    public static Image last_normal;
    public static Image last_selected;
    public static Image next_normal;
    public static Image next_selected;
    public static Image previous_normal;
    public static Image previous_selected;

    public static Image forward_normal;
    public static Image forward_normal_v2;
    public static Image forward_selected;
    public static Image forward_selected_v2;
    public static Image history_normal;
    public static Image history_selected;
    public static Image board_image;

    public static Image menu_normal;
    public static Image menu_selected;

    public static Image circle_check;
    public static Image circle_xmark;
    public static Image panel_320_292;

    public static Image puzzle_normal;
    public static Image puzzle_selected;
    public static Image puzzle_failed_normal;
    public static Image puzzle_failed_selected;
    public static Image puzzle_solved_normal;
    public static Image puzzle_solved_selected;
    public static Image option_box;
    public static Image game_options_panel;
    public static Image panel_500_320;
    public static Sound sound;
    private ArrayList<String> data = new ArrayList<>();
    public ReadImage() {
        data = JDBCConnection.takeDataSetting();
        try {
            piece = ImageIO.read(new File(data.get(0)));
            board_image = ImageIO.read(new File(data.get(1)));
            if (data.get(2).equals("1"))
                sound = new Sound();
            else
                sound = new Sound(1);
            title_bar = ImageIO.read(new File("resources/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("resources/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("resources/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("resources/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("resources/buttons/home_selected.png"));
            game_options_panel = ImageIO.read(new File("resources/gui/game_options_panel.png"));
            board_index = ImageIO.read(new File("resources/gui/board_index_white.png"));
            board_index_black = ImageIO.read(new File("resources/gui/board_index_black.png"));
            rotate_normal = ImageIO.read(new File("resources/buttons/rotate_normal.png"));
            rotate_selected = ImageIO.read(new File("resources/buttons/rotate_selected.png"));
            game_gui = ImageIO.read(new File("resources/gui/game_gui.png"));
            first_normal = ImageIO.read(new File("resources/buttons/first_normal.png"));
            first_selected = ImageIO.read(new File("resources/buttons/first_selected.png"));
            last_normal = ImageIO.read(new File("resources/buttons/last_normal.png"));
            last_selected = ImageIO.read(new File("resources/buttons/last_selected.png"));
            next_normal = ImageIO.read(new File("resources/buttons/next_normal.png"));
            next_selected = ImageIO.read(new File("resources/buttons/next_selected.png"));
            previous_normal = ImageIO.read(new File("resources/buttons/previous_normal.png"));
            previous_selected = ImageIO.read(new File("resources/buttons/previous_selected.png"));
            option_box = ImageIO.read(new File("resources/gui/option_box.png"));
            forward_normal = ImageIO.read(new File("resources/buttons/forward_normal.png"));
            forward_normal_v2 = ImageIO.read(new File("resources/buttons/forward_normalv2.png"));
            forward_selected = ImageIO.read(new File("resources/buttons/forward_selected.png"));
            forward_selected_v2 = ImageIO.read(new File("resources/buttons/forward_selectedv2.png"));
            history_normal = ImageIO.read(new File("resources/buttons/history_normal.png"));
            history_selected = ImageIO.read(new File("resources/buttons/history_selected.png"));
            menu_normal = ImageIO.read(new File("resources/buttons/menu_normal.png"));
            menu_selected = ImageIO.read(new File("resources/buttons/menu_selected.png"));
            panel_500_320 = ImageIO.read(new File("resources/gui/panel_500_320.png"));
            panel_320_292 = ImageIO.read(new File("resources/gui/panel_320_292.png"));
            circle_xmark = ImageIO.read(new File("resources/gui/circle_xmark.png"));
            circle_check = ImageIO.read(new File("resources/gui/circle_check.png"));

            puzzle_normal = ImageIO.read(new File("resources/buttons/puzzle_normal.png"));
            puzzle_selected = ImageIO.read(new File("resources/buttons/puzzle_selected.png"));
            puzzle_failed_normal = ImageIO.read(new File("resources/buttons/puzzle_failed_normal.png"));
            puzzle_failed_selected = ImageIO.read(new File("resources/buttons/puzzle_failed_selected.png"));
            puzzle_solved_normal = ImageIO.read(new File("resources/buttons/puzzle_solved_normal.png"));
            puzzle_solved_selected = ImageIO.read(new File("resources/buttons/puzzle_solved_selected.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changeBoardImage(String path) {
        try {
            board_image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changePieceImage(String path) {
        try {
            piece = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changeSound(int index) {
        if (index == 0)
            sound = new Sound();
        else
            sound = new Sound(1);
    }
}
