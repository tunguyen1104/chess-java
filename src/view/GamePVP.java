package view;

import model.Board;
import model.ReadImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class GamePVP extends JPanel {
    protected JLabel white_name;
    protected JLabel black_name;
    private JLabel title_bar_label;
    private Image board_index;
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

    // time
    public TimeLabel timeLabelWhite;
    public TimeLabel timeLabelBlack;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    public JTextArea textArea;
    public int minute;
    public JScrollPane scrollPaneTextArea;
    private Board board = new Board(this);

    public GamePVP(int minute) {
        this.minute = minute;
        this.setBackground(new Color(41, 41, 41));
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        this.setLayout(null);
        timeLabelWhite = new TimeLabel(minute, Menu.screenWidth / 6 + 800, Menu.screenHeight / 10 + 410);
        timeLabelBlack = new TimeLabel(minute, Menu.screenWidth / 6 + 800, Menu.screenHeight / 10 + 80);
        initPanel();
        board.setBounds(Menu.screenWidth / 6 + 30, Menu.screenHeight / 10 + 24, 8 * board.tileSize, 8 * board.tileSize);
        this.add(board);
        board_index = ReadImage.board_index;
        timeLabelWhite.start();
        timer.start();
    }

    public void initPanel() {
        // setting name
        black_name = new JLabel("Player2 (Black)");
        black_name.setBounds(Menu.screenWidth / 6 + 750, Menu.screenHeight / 10 + 58, 490, 120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        white_name = new JLabel("Player1 (White)");
        white_name.setBounds(Menu.screenWidth / 6 + 750, Menu.screenHeight / 10 + 508, 490, 120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f));
        // ===========Title Bar============
        title_bar_label = new JLabel("Standard - PvP");
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        title_bar_label.setBounds(( Menu.screenWidth - 140 ) / 2, 18, title_bar_label.getPreferredSize().width + 2, title_bar_label.getPreferredSize().height);
        // ================================
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 - 65, 10, 44, 44);
        home_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 + 450 + 20, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(board.checkEndGame) return;
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(board.checkEndGame) return;
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        textArea = new JTextArea();
        textArea.setBackground(new Color(55,55,55));
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
        scrollPaneTextArea.setBounds(Menu.screenWidth / 6 + 720, Menu.screenHeight / 10 + 300, 268, 148);
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

        ButtonImage rotate = new ButtonImage(ReadImage.rotate_normal, ReadImage.rotate_selected, 48, 38, "");
        rotate.setBounds(Menu.screenWidth / 6 + 950, Menu.screenHeight / 10 + 246, 48, 38);
        rotate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(board.checkEndGame) return;
                board_index = (board.rotating) ? null : ReadImage.board_index_black;
                board.rotateBoard();
                repaint();
            }
        });
        this.add(rotate);
    }
    public void start_stop_time_label(boolean isTurn) {
        if (isTurn == true) {
            timeLabelBlack.start();
            timeLabelWhite.stop();
        } else {
            timeLabelWhite.start();
            timeLabelBlack.stop();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, ( Menu.screenWidth - 450 ) / 2, 10, 450, 44, this);
        g2d.drawImage(ReadImage.game_gui, Menu.screenWidth / 6, Menu.screenHeight / 10,1014,690, this);
        g2d.drawImage(board_index, Menu.screenWidth / 6, Menu.screenHeight / 10,690,690, this);
    }

    public Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("00:00".equals(timeLabelBlack.getText())) {
                String text = board.convertDate() +
                        "\nPvP\n" +
                        minute +
                        "\nTimeout\n" +
                        "White win\n";
                board.saveFile(new String(text + board.getPgn()));
                ReadImage.sound.playMusic(1);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                board.addDialogEndGame("White", "timeout");
            } else if ("00:00".equals(timeLabelWhite.getText())) {
                String text = board.convertDate() +
                        "\nPvP\n" +
                        minute +
                        "\nTimeout\n" +
                        "Black win\n";
                board.saveFile(new String(text + board.getPgn()));
                ReadImage.sound.playMusic(1);
                timeLabelWhite.stop();
                timeLabelBlack.stop();
                timer.stop();
                board.addDialogEndGame("Black", "timeout");
            }
        }
    });
}
