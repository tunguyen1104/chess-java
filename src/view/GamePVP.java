package view;

import model.Board;
import model.Sound;
import model.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class GamePVP extends JPanel {
    public JFrame frame;
    private BufferedImage game_gui;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage back_selected;
    private BufferedImage home_normal;
    private BufferedImage home_selected;
    private BufferedImage icon_game;
    private BufferedImage rotate_normal;
    private BufferedImage rotate_selected;
    private BufferedImage board_index_black;
    private BufferedImage board_index;
    private JLabel white_name;
    private JLabel black_name;
    private JLabel title_bar_label;
    //time
    public JLabel timeLabelWhite = new JLabel();
    public JLabel timeLabelBlack = new JLabel();
    private int seconds_white;
    private int minutes_white;
    private int seconds_black;
    private int minutes_black;
    public String seconds_string1;
    public String minutes_string1;
    public String seconds_string2;
    public String minutes_string2;
    ButtonImage back_normal_button;
    ButtonImage home_normal_button;
    public JTextArea textArea;
    public JScrollPane scrollPaneTextArea;
    private Board board = new Board(this);
    public GamePVP(int minute) {
        seconds_white = 0;
        minutes_white = minute;
        seconds_black = 0;
        minutes_black = minute;
        seconds_string1 = String.format("%02d", seconds_white);
        minutes_string1 = String.format("%02d", minutes_white);
        seconds_string2 = String.format("%02d", seconds_black);
        minutes_string2 = String.format("%02d", minutes_black);
        // Load image
        try {
            board_index = ImageIO.read(new File("src/res/gui/board_index_white.png"));
            board_index_black = ImageIO.read(new File("src/res/gui/board_index_black.png"));
            back_selected = ImageIO.read(new File("src/res/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("src/res/buttons/home_selected.png"));
            rotate_normal = ImageIO.read(new File("src/res/buttons/rotate_normal.png"));
            rotate_selected = ImageIO.read(new File("src/res/buttons/rotate_selected.png"));
            icon_game = ImageIO.read(new File("src/res/gui/icon_game.png"));
            game_gui = ImageIO.read(new File("src/res/gui/game_gui.png"));
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set preferred size of the panel to match background image size
        this.setBackground(new Color(41,41,41));
        this.setPreferredSize(new Dimension(1600, 1000));
        this.setLayout(null);
        initPanel();
        board.setBounds(280,90,8 * 85,8 * 85);
        this.add(board);
        frame = new JFrame("CHESS PVP");
        frame.setIconImage(icon_game);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-6,0);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
    public void initPanel() {
        //setting name
        white_name = new JLabel("Player2 (Black)");
        white_name.setBounds(1050,140,490,120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f)); // Tạo font mới với kích thước mới và thiết lập cho nhãn

        black_name = new JLabel("Player1 (White)");
        black_name.setBounds(1050,610,490,120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        //==========Time_label_white==========
        timeLabelWhite.setText(minutes_string1 + ":" + seconds_string1);
        timeLabelWhite.setBounds(1100, 160, 500, 200);
        timeLabelWhite.setFont(timeLabelWhite.getFont().deriveFont(40.0f));
        // ==========Time_label_black==========
        timeLabelBlack.setText(minutes_string2 + ":" + seconds_string2);
        timeLabelBlack.setBounds(1100, 505, 500, 200);
        timeLabelBlack.setFont(timeLabelBlack.getFont().deriveFont(40.0f));
        //===========Title Bar============
        title_bar_label = new JLabel("Standard - PvP");
        title_bar_label.setBounds(680,0,400,60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(18.0f)); // Tạo font mới với kích thước mới và thiết lập cho nhãn
        //================================
        back_normal_button = new ButtonImage(back_normal,back_selected,42,42,"");
        home_normal_button = new ButtonImage(home_normal,home_selected,42,42,"");
        back_normal_button.setBounds(465,10,42,42);
        home_normal_button.setBounds(1000,10,42,42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new GameOptions();
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Menu();
            }
        });
        textArea = new JTextArea();
        textArea.setBackground(new Color(55,55,55));
        String s = "   1.   Kxh7\t\td7\n" +
                "   2.   Kxh7\t\td7\n" +
                "   3.   Kxh7\t\td7\n" +
                "   4.   Kxh7\t\td7\n" +
                "   5.   Kxh7\t\td7\n" +
                "   6.   Kxh7\t\td7\n" +
                "   7.   Kxh7\t\td7\n" +
                "   8.   Kxh7\t\td7\n" +
                "   9.   Kxh7\t\td7\n" +
                "   10.   Kxh7\t\td7\n" +
                "   11.   Kxh7\t\td7\n";
        textArea.setText(s);
        textArea.setForeground(Color.WHITE);
        try {
            textArea.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/res/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 14));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        scrollPaneTextArea = new JScrollPane(textArea);
        scrollPaneTextArea.setBounds(1010,384,308,158);
        scrollPaneTextArea.setBackground(new Color(55,55,55));
        Border border = BorderFactory.createLineBorder(new Color(55,55,55));
        scrollPaneTextArea.setBorder(border);
        scrollPaneTextArea.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(scrollPaneTextArea);
        this.add(timeLabelWhite);
        this.add(timeLabelBlack);
        this.add(white_name);
        this.add(black_name);
        this.add(back_normal_button);
        this.add(home_normal_button);
        this.add(title_bar_label);


        ButtonImage rotate = new ButtonImage(rotate_normal,rotate_selected,40,30,"");
        rotate.setBounds(1252,338,40,30);
        this.add(rotate);
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(game_gui, 250, 70,this);
        g2d.drawImage(title_bar,530,10,450,42,this);
        g2d.drawImage(board_index,250,70,this);
    }
    public Timer timer_white = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (seconds_white > 0) {
                seconds_white = seconds_white - 1;
            } else if (seconds_white == 0 && minutes_white > 0) {
                minutes_white = minutes_white - 1;
                seconds_white = 59;
            }
            seconds_string1 = String.format("%02d", seconds_white);
            minutes_string1 = String.format("%02d", minutes_white);
            timeLabelWhite.setText(minutes_string1 + ":" + seconds_string1);
        }
    });
    public Timer timer_black = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (seconds_black > 0) {
                seconds_black = seconds_black - 1;
            } else if (seconds_black == 0 && minutes_black > 0) {
                minutes_black = minutes_black - 1;
                seconds_black = 59;
            }
            seconds_string2 = String.format("%02d", seconds_black);
            minutes_string2 = String.format("%02d", minutes_black);
            timeLabelBlack.setText(minutes_string2 + ":" + seconds_string2);
        }

    });
    public void start_white() {
        timer_white.start();
    }

    public void start_black() {
        timer_black.start();
    }

    public void stop_white() {
        timer_white.stop();
    }

    public void stop_black() {
        timer_black.stop();
    }
    public static void main(String[] args) {
        new GamePVP(3);
    }
}
