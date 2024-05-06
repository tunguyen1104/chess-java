package view;

import javax.swing.*;
import javax.swing.border.Border;

import controller.ListenerReview;
import model.BoardReview;
import model.ReadImage;

import java.awt.*;
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
    private JTextArea textArea;
    private JScrollPane scrollPaneTextArea;
    private JLabel white_name;
    private JLabel black_name;
    private JLabel timeLabelWhite;
    private JLabel timeLabelBlack;
    private ListenerReview listenerReview;
    private BoardReview boardReview;
    private Image board_index;
    public Review(String pgn) {
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        initPanel();
        boardReview = new BoardReview(this,pgn);
        boardReview.setBounds(Menu.screenWidth / 6 + 30, Menu.screenHeight / 10 + 24, 640,640);
        this.add(boardReview);
        listenerReview = new ListenerReview(this,boardReview);
        rotate.addMouseListener(listenerReview);
        back_normal_button.addMouseListener(listenerReview);
        home_normal_button.addMouseListener(listenerReview);
        first_normal_button.addMouseListener(listenerReview);
        last_normal_button.addMouseListener(listenerReview);
        next_normal_button.addMouseListener(listenerReview);
        previous_normal_button.addMouseListener(listenerReview);
        board_index = ReadImage.board_index;
    }
    public void initPanel() {
        black_name = new JLabel("Black");
        black_name.setBounds(Menu.screenWidth / 6 + 750, Menu.screenHeight / 10 + 58, 200, 120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        white_name = new JLabel("White");
        white_name.setBounds(Menu.screenWidth / 6 + 750, Menu.screenHeight / 10 + 508, 200, 120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f));
        timeLabelWhite = new JLabel("--:--");
        timeLabelWhite.setBounds(Menu.screenWidth / 6 + 800, Menu.screenHeight / 10 + 486, 200, 40);
        timeLabelBlack = new JLabel("--:--");
        timeLabelBlack.setBounds(Menu.screenWidth / 6 + 800, Menu.screenHeight / 10 + 160, 200, 40);
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
        scrollPaneTextArea.setBounds(Menu.screenWidth / 6 + 720, Menu.screenHeight / 10 + 300, 270, 148);
        Border border = BorderFactory.createLineBorder(new Color(55, 55, 55));
        scrollPaneTextArea.setBorder(border);
        scrollPaneTextArea.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(scrollPaneTextArea);

        rotate = new ButtonImage(ReadImage.rotate_normal, ReadImage.rotate_selected, 48, 38, "");
        rotate.setBounds(Menu.screenWidth / 6 + 950, Menu.screenHeight / 10 + 246, 48, 38);
        this.add(rotate);
        first_normal_button = new ButtonImage(ReadImage.first_normal, ReadImage.first_selected, 40, 40, "");
        int x = Menu.screenWidth / 6 + 730;
        first_normal_button.setBounds(x, Menu.screenHeight / 3 + 42, 40, 40);
        this.add(first_normal_button);
        next_normal_button = new ButtonImage(ReadImage.next_normal, ReadImage.next_selected, 40, 40, "");
        next_normal_button.setBounds(x + 80, Menu.screenHeight / 3 + 42, 40, 40);
        this.add(next_normal_button);
        previous_normal_button = new ButtonImage(ReadImage.previous_normal, ReadImage.previous_selected, 40, 40, "");
        previous_normal_button.setBounds(x + 40, Menu.screenHeight / 3 + 42, 40, 40);
        this.add(previous_normal_button);
        last_normal_button = new ButtonImage(ReadImage.last_normal, ReadImage.last_selected, 40, 40, "");
        last_normal_button.setBounds(x + 120, Menu.screenHeight / 3 + 42, 40, 40);
        this.add(last_normal_button);
        title_bar_label = new JLabel("Review");
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        title_bar_label.setBounds(( Menu.screenWidth - 60 ) / 2, 18, title_bar_label.getPreferredSize().width + 4, title_bar_label.getPreferredSize().height);
        this.add(title_bar_label);
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 - 65, 10, 44, 44);
        home_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 + 450 + 20, 10, 44, 44);
        this.add(back_normal_button);
        this.add(home_normal_button);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, ( Menu.screenWidth - 450 ) / 2, 10, 450, 44, this);
        g2d.drawImage(ReadImage.game_gui, Menu.screenWidth / 6, Menu.screenHeight / 10,1014,690, this);
        g2d.drawImage(board_index, Menu.screenWidth / 6, Menu.screenHeight / 10,690,690, this);
    }
    public ButtonImage getRotate() {
        return rotate;
    }
    public ButtonImage getLast_normal_button() {
        return last_normal_button;
    }
    public ButtonImage getNext_normal_button() {
        return next_normal_button;
    }
    public ButtonImage getPrevious_normal_button() {
        return previous_normal_button;
    }
    public ButtonImage getFirst_normal_button() {
        return first_normal_button;
    }
    public ButtonImage getBack_normal_button() {
        return back_normal_button;
    }
    public ButtonImage getHome_normal_button() {
        return home_normal_button;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getWhite_name() {
        return white_name;
    }

    public JLabel getBlack_name() {
        return black_name;
    }

    public void setBoard_index(Image board_index) {
        this.board_index = board_index;
    }
}
