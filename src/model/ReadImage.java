package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReadImage {
    public static Image loading;
    public static Image title_bar;
    public static Image back_normal;
    public static Image home_normal;
    public static Image back_selected;
    public static Image home_selected;

    public ReadImage() {
        try {
            title_bar = ImageIO.read(new File("resources/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("resources/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("resources/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("resources/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("resources/buttons/home_selected.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
