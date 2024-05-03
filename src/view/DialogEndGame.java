package view;

import javax.swing.*;

import model.JDBCConnection;
import model.ReadImage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
public class DialogEndGame extends JPanel{
    public JLabel title;
    public JLabel titlev2;
    public DialogEndGame(String name, String reason) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(640, 640));
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
        ButtonImage newGame = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "New Game",0);
        newGame.setBounds(160,270,152,50);
        
        ButtonImage review = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Review",0);
        review.setBounds(320,270,152,50);
        
        ButtonImage home = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Home",0);
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
                handleReview();
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
    public void handleReview() {
        Menu.panelCardLayout.add(new History(),"history");
        Menu.panelCardLayout.add(new Review(JDBCConnection.takeHistoryTheEnd()),"review");
        Menu.cardLayout.show(Menu.panelCardLayout,"review");
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ReadImage.panel_500_320,120,150,400,256,this);
    }
}
