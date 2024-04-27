package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import controller.ListenerReview;
import model.BoardReview;
import model.ReadImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Review extends JPanel {
    private JLabel title_bar_label;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private ButtonImage rotate;
    private ButtonImage first_normal_button;
    private ButtonImage last_normal_button;
    private ButtonImage next_normal_button;
    private ButtonImage previous_normal_button;
    
    private BufferedImage game_gui;
    private BufferedImage rotate_normal;
    private BufferedImage rotate_selected;
    public BufferedImage board_index_black;
    public BufferedImage board_index;
    private BufferedImage first_normal;
    private BufferedImage first_selected;
    private BufferedImage last_normal;
    private BufferedImage last_selected;
    private BufferedImage next_normal;
    private BufferedImage next_selected;
    private BufferedImage previous_normal;
    private BufferedImage previous_selected;
    private JTextArea textArea;
    private JScrollPane scrollPaneTextArea;
    protected JLabel white_name;
    protected JLabel black_name;
    public JLabel timeLabelWhite;
    public JLabel timeLabelBlack;
    private ListenerReview listenerReview;
    private BoardReview boardReview;
    public Review(File nameFile) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1536, 864));
        initPanel();
        boardReview = new BoardReview(this,nameFile);
        boardReview.setBounds(278, 114, 640, 640);
        this.add(boardReview);
        listenerReview = new ListenerReview(this,boardReview);
        rotate.addMouseListener(listenerReview);
        back_normal_button.addMouseListener(listenerReview);
        home_normal_button.addMouseListener(listenerReview);
        first_normal_button.addMouseListener(listenerReview);
        last_normal_button.addMouseListener(listenerReview);
        next_normal_button.addMouseListener(listenerReview);
        previous_normal_button.addMouseListener(listenerReview);

        
    }
    public void initPanel() {

        black_name = new JLabel("Black");
        black_name.setBounds(1000, 150, 490, 120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        white_name = new JLabel("White");
        white_name.setBounds(1000, 600, 490, 120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f));
        timeLabelWhite = new JLabel("--:--");
        timeLabelWhite.setBounds(1050, 575, 200, 40);
        timeLabelBlack = new JLabel("--:--");
        timeLabelBlack.setBounds(1050, 250, 200, 40);
        try {
            timeLabelWhite.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 40));
            timeLabelBlack.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 40));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(black_name);
        this.add(white_name);
        this.add(timeLabelBlack);
        this.add(timeLabelWhite);

        textArea = new JTextArea();
        textArea.setBackground(new Color(55, 55, 55));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        try {
            textArea.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 16));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        scrollPaneTextArea = new JScrollPane(textArea);
        scrollPaneTextArea.setBounds(962, 390, 298, 148);
        Border border = BorderFactory.createLineBorder(new Color(55, 55, 55));
        scrollPaneTextArea.setBorder(border);
        scrollPaneTextArea.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(scrollPaneTextArea);

        rotate = new ButtonImage(rotate_normal, rotate_selected, 50, 40, "");
        rotate.setBounds(1200, 336, 50, 40);
        this.add(rotate);
        first_normal_button = new ButtonImage(first_normal, first_selected, 40, 40, "");
        first_normal_button.setBounds(980, 336, 40, 40);
        this.add(first_normal_button);
        next_normal_button = new ButtonImage(next_normal, next_selected, 40, 40, "");
        next_normal_button.setBounds(1060, 336, 40, 40);
        this.add(next_normal_button);
        previous_normal_button = new ButtonImage(previous_normal, previous_selected, 40, 40, "");
        previous_normal_button.setBounds(1020, 336, 40, 40);
        this.add(previous_normal_button);
        last_normal_button = new ButtonImage(last_normal, last_selected, 40, 40, "");
        last_normal_button.setBounds(1100, 336, 40, 40);
        this.add(last_normal_button);
        //
        title_bar_label = new JLabel("Review");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        this.add(title_bar_label);
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        this.add(back_normal_button);
        this.add(home_normal_button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(game_gui, 250, 90,1014,690, this);
        g2d.drawImage(board_index, 250, 90,690,690, this);
    }
    public ButtonImage getRotate() {
        return rotate;
    }

    public void setRotate(ButtonImage rotate) {
        this.rotate = rotate;
    }
    public ButtonImage getLast_normal_button() {
        return last_normal_button;
    }

    public void setLast_normal_button(ButtonImage last_normal_button) {
        this.last_normal_button = last_normal_button;
    }
    public ButtonImage getNext_normal_button() {
        return next_normal_button;
    }

    public void setNext_normal_button(ButtonImage next_normal_button) {
        this.next_normal_button = next_normal_button;
    }
    public ButtonImage getPrevious_normal_button() {
        return previous_normal_button;
    }

    public void setPrevious_normal_button(ButtonImage previous_normal_button) {
        this.previous_normal_button = previous_normal_button;
    }
    public ButtonImage getFirst_normal_button() {
        return first_normal_button;
    }

    public void setFirst_normal_button(ButtonImage first_normal_button) {
        this.first_normal_button = first_normal_button;
    }
    public ButtonImage getBack_normal_button() {
        return back_normal_button;
    }

    public void setBack_normal_button(ButtonImage back_normal_button) {
        this.back_normal_button = back_normal_button;
    }
    public ButtonImage getHome_normal_button() {
        return home_normal_button;
    }

    public void setHome_normal_button(ButtonImage home_normal_button) {
        this.home_normal_button = home_normal_button;
    }
}
