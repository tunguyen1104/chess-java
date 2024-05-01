package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
public class DialogEndGame extends JPanel{
    private Image image;
    private Image menu_normal;
    private Image menu_selected;
    public JLabel title;
    public JLabel titlev2;
    public DialogEndGame(String name, String reason) {
        this.setLayout(null);
        try {
            image = ImageIO.read(new File("resources/gui/panel_500_320.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(640, 640));
        this.setVisible(true);
        try {
            menu_normal = ImageIO.read(new File("resources/buttons/menu_normal.png"));
            menu_selected = ImageIO.read(new File("resources/buttons/menu_selected.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        title = new JLabel(name + " Win");
        title.setForeground(Color.WHITE);
        title.setBounds(220, 190, 240, 40);
        titlev2 = new JLabel("by " + reason);
        titlev2.setForeground(Color.WHITE);
        titlev2.setBounds(260, 230, 140, 30);
        try {
            title.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/Inter-Regular.ttf")).deriveFont(Font.BOLD, 40));
                    titlev2.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/Inter-Regular.ttf")).deriveFont(Font.PLAIN, 16));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ButtonImage newGame = new ButtonImage(menu_normal, menu_selected, 152, 50, "New Game",0);
        newGame.setBounds(160,270,152,50);
        
        ButtonImage review = new ButtonImage(menu_normal, menu_selected, 152, 50, "Review",0);
        review.setBounds(320,270,152,50);
        
        ButtonImage home = new ButtonImage(menu_normal, menu_selected, 152, 50, "Home",0);
        home.setBounds(240,320,152,50);
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
            }
        });
        review.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
            }
        });
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(title);
        this.add(titlev2);
        this.add(newGame);this.add(review);
        this.add(home);
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,120,150,400,256,this);
    }
    public JLabel getTitle() {
        return title;
    }
    public void setTitle(JLabel title) {
        this.title = title;
    }
    public JLabel getTitlev2() {
        return titlev2;
    }
    public void setTitlev2(JLabel titlev2) {
        this.titlev2 = titlev2;
    }
}
