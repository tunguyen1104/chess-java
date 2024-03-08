package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Setting extends JPanel{
    private JFrame frame;
    private JPanel piece_panel;
    private JLabel piece_set;
    private JLabel board_label;
    private JLabel sound_label;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage home_normal;
    private BufferedImage piece_image;
    private BufferedImage setting_panel;
    private JLabel title_bar_label;
    private JComboBox combo_piece;
    private JComboBox combo_board;
    private JComboBox combo_sound;
    private JScrollPane listScrollPane_piece;
    private JScrollPane listScrollPane_board;
    private BufferedImage board_image;
    private JPanel board_panel;
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    String [] piece_url = {
            "src/res/pieces/default.png",
            "src/res/pieces/alpha.png",
            "src/res/pieces/anarcandy.png",
            "src/res/pieces/cardinal.png",
            "src/res/pieces/chessnut.png",
            "src/res/pieces/kiwen-suwi.png",
            "src/res/pieces/maestro.png",
            "src/res/pieces/tatiana.png"
    };
    String board_url[] = {
            "src/res/board/green.png","src/res/board/brown.png","src/res/board/tournament.png"
    };
    public Setting(){
        initPanel();
        frame = new JFrame();
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
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
    public void initPanel() {
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        // Load image
        try {
            board_image = ImageIO.read(new File(board_url[0]));
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
            setting_panel = ImageIO.read(new File("src/res/gui/game_options_panel.png"));
            piece_image = ImageIO.read(new File(piece_url[0]));
        } catch (IOException e) {
            System.out.println("Error url image!");
            throw new RuntimeException(e);
        }
        // Set preferred size of the panel to match background image size
        setPreferredSize(new Dimension(1600, 1000));
        //===========Title Bar============
        title_bar_label = new JLabel("Settings");
        title_bar_label.setBounds(720,0,400,60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        //----------------------
        //setting back_normal, home_normal
        MyImageButton back_normal_button = new MyImageButton(back_normal,"back_normal");
        MyImageButton home_normal_button = new MyImageButton(home_normal,"home_normal");
        back_normal_button.setBounds(465,10,42,42);
        home_normal_button.setBounds(1000,10,42,42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Menu();
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
        this.add(back_normal_button);
        this.add(home_normal_button);
        //------------------------
        //label piece set
        piece_set = new JLabel("Piece Set");
        piece_set.setBounds(560,240,90,40);
        piece_set.setForeground(Color.WHITE);
        piece_set.setFont(piece_set.getFont().deriveFont(20.0f));
        this.add(piece_set);
        //------------------------
        board_label = new JLabel("Board");
        board_label.setBounds(590,370,90,40);
        board_label.setForeground(Color.WHITE);
        board_label.setFont(board_label.getFont().deriveFont(20.0f));
        this.add(board_label);
        //------------------------
        sound_label = new JLabel("Sound");
        sound_label.setBounds(590,480,90,40);
        sound_label.setForeground(Color.WHITE);
        sound_label.setFont(sound_label.getFont().deriveFont(20.0f));
        this.add(sound_label);
        //------------------------
        //setting type pieces
        String piecesName[] = {
                "  Default","  Alpha","  Anarcandy","  Cardinal","  Chessnut","  Kiwen-suwi","  Maestro","  Tatiana"
        };
        combo_piece = new JComboBox(piecesName);
        combo_piece.setSelectedIndex(0);
        combo_piece.setBackground(new Color(27, 101, 106, 255));
        combo_piece.setForeground(Color.WHITE);
        combo_piece.setFocusable(false);
        listScrollPane_piece = new JScrollPane(combo_piece);
        listScrollPane_piece.setBounds(840,240,140,30);
        listScrollPane_piece.setBorder(border);
        combo_piece.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndex = combo_piece.getSelectedIndex();
                    piece_image = ImageIO.read(new File(piece_url[selectedIndex]));
                    piece_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                    throw new RuntimeException(ex);
                }
            }
        });
        piece_panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(piece_image,6,5,140,50,this);
            }
        };
        piece_panel.setBackground(new Color(161, 150, 128));
        piece_panel.setBounds(670,230,150,60);
        this.add(piece_panel);
        this.add(listScrollPane_piece);

        String board[] = {
                "  Green","  Brown","  Tournament"
        };
        combo_board = new JComboBox(board);
        combo_board.setBackground(new Color(27, 101, 106, 255));
        combo_board.setFocusable(false);
        combo_board.setSelectedIndex(0);
        combo_board.setForeground(Color.WHITE);

        listScrollPane_board = new JScrollPane(combo_board);
        listScrollPane_board.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_board.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane_board.setBounds(840, 375, 150, 30);
        listScrollPane_board.setBorder(border);
        this.add(listScrollPane_board);
        combo_board.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndex = combo_board.getSelectedIndex();
                    board_image = ImageIO.read(new File(board_url[selectedIndex]));
                    board_panel.repaint();
                } catch (IOException ex) {
                    System.out.println("Error url image!");
                    throw new RuntimeException(ex);
                }
            }
        });
        board_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(board_image,0,0,120,120,board_panel);
            }
        };
        board_panel.setBounds(680,320,120,120);
        this.add(board_panel);
        String turn[] = {"  On","  Off"};
        combo_sound = new JComboBox(turn);
        combo_sound.setBackground(new Color(27, 101, 106, 255));
        combo_sound.setBounds(800,480,120,30);
        combo_sound.setFocusable(false);
        combo_sound.setSelectedIndex(0);
        combo_sound.setForeground(Color.WHITE);
        this.add(combo_sound);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(title_bar,530,10,450,42,this);
        g.drawImage(setting_panel,500,180,520,400,this);
    }

    public static void main(String[] args) {
        new Setting();
    }
}
