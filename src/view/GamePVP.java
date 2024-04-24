package view;

import model.Board;
import model.ReadImage;
import model.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePVP extends JPanel {
    private BufferedImage game_gui;
    private BufferedImage rotate_normal;
    private BufferedImage rotate_selected;
    private BufferedImage board_index_black;
    private BufferedImage board_index;
    protected JLabel white_name;
    protected JLabel black_name;
    private JLabel title_bar_label;

    public JLabel getWhite_name() {
        return white_name;
    }

    public JLabel getBlack_name() {
        return black_name;
    }

    public JLabel getTimeLabelWhite() {
        return timeLabelWhite;
    }

    public JLabel getTimeLabelBlack() {
        return timeLabelBlack;
    }

    Sound sound = new Sound();
    // time
    public TimeLabel timeLabelWhite;
    public TimeLabel timeLabelBlack;
    ButtonImage back_normal_button;
    ButtonImage home_normal_button;
    public JTextArea textArea;
    public JScrollPane scrollPaneTextArea;
    private Board board = new Board(this);

    public GamePVP(int minute) {
        // Load image
        try {
            board_index = ImageIO.read(new File("resources/gui/board_index_white.png"));
            board_index_black = ImageIO.read(new File("resources/gui/board_index_black.png"));
            rotate_normal = ImageIO.read(new File("resources/buttons/rotate_normal.png"));
            rotate_selected = ImageIO.read(new File("resources/buttons/rotate_selected.png"));
            game_gui = ImageIO.read(new File("resources/gui/game_gui.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set preferred size of the panel to match background image size
        this.setBackground(new Color(41, 41, 41));
        this.setPreferredSize(new Dimension(1536, 864));
        this.setLayout(null);
        timeLabelWhite = new TimeLabel(minute, 1050, 490);
        timeLabelBlack = new TimeLabel(minute, 1050, 160);
        initPanel();
        board.setBounds(280, 100, 8 * board.tileSize, 8 * board.tileSize);
        this.add(board);
        timeLabelWhite.start();
        timer.start();
    }

    public void initPanel() {
        // setting name
        black_name = new JLabel("Player2 (Black)");
        black_name.setBounds(1000, 140, 490, 120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        white_name = new JLabel("Player1 (White)");
        white_name.setBounds(1000, 590, 490, 120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f));
        // ===========Title Bar============
        title_bar_label = new JLabel("Standard - PvP");
        title_bar_label.setBounds(700, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,18));
        // ================================
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        textArea = new JTextArea();
        textArea.setBackground(new Color(55, 55, 55));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        try {
            textArea.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 15));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        scrollPaneTextArea = new JScrollPane(textArea);
        scrollPaneTextArea.setBounds(962, 380, 298, 148);
        Border border = BorderFactory.createLineBorder(new Color(55, 55, 55));
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

        ButtonImage rotate = new ButtonImage(rotate_normal, rotate_selected, 50, 40, "");
        rotate.setBounds(1200, 326, 50, 40);
        rotate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                board_index = (board.rotating) ? null : board_index_black;
                board.rotateBoard();
                repaint();
            }
        });
        this.add(rotate);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(game_gui, 250, 80,1014,690, this);
        g2d.drawImage(board_index, 250, 80,690,690, this);
    }

    public Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("00:00".equals(timeLabelBlack.getText())) {
                System.out.println(textArea.getText());
                sound.playMusic(1);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                noti_end_game("White", "Time out");
            }
            if ("00:00".equals(timeLabelWhite.getText())) {
                System.out.println(textArea.getText());
                sound.playMusic(1);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                noti_end_game("Black", "Time out");
            }
        }
    });

    public void noti_end_game(String name_win, String reason) {
        Object[] options = { "New Game", "Home", "Review" };
        int select = JOptionPane.showOptionDialog(null, name_win + " Win (" + reason + " )", "Notification",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (select) {
            case 0:
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
                break;
            case 1:
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
                break;
            case 2:

                break;
        }
    }
}
