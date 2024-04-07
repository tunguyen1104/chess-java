package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReadImage {
    public static BufferedImage loading;
    public static BufferedImage title_bar;
    public static BufferedImage back_normal;
    public static BufferedImage home_normal;
    public static BufferedImage back_selected;
    public static BufferedImage home_selected;

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
