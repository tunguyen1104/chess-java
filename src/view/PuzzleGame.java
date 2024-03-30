package view;

import model.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PuzzleGame extends JPanel {
    public String FEN;
    public int lever;
    private BufferedImage title_bar;
    private JLabel title_bar_label;
    private BufferedImage home_normal;
    private BufferedImage home_selected;
    private BufferedImage back_normal;
    private BufferedImage back_selected;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private BufferedImage board_index;
    private BufferedImage panel_320_292;
    private Board board;
    private BufferedImage normal;
    private BufferedImage selected;
    private ButtonImage hint;
    private ButtonImage undo;
    private ButtonImage try_again;
    private ButtonImage next_lever;
    public JPanel hint_panel;
    public JPanel undo_panel;
    public JPanel done_panel;
    private JLabel color_to_move;
    private JLabel correct;
    private JLabel failed;
    public BufferedImage circle_check;
    public BufferedImage circle_xmark;

    public PuzzleGame(String FEN, int lever) {
        this.FEN = FEN;
        this.lever = lever;
        board = new Board(this, FEN);
        try {
            normal = ImageIO.read(new File("resources/buttons/menu_normal.png"));
            selected = ImageIO.read(new File("resources/buttons/menu_selected.png"));
            panel_320_292 = ImageIO.read(new File("resources/gui/panel_320_292.png"));
            board_index = ImageIO.read(new File("resources/gui/board_index_white.png"));
            title_bar = ImageIO.read(new File("resources/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("resources/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("resources/buttons/home_normal.png"));
            back_selected = ImageIO.read(new File("resources/buttons/back_selected.png"));
            home_selected = ImageIO.read(new File("resources/buttons/home_selected.png"));
            circle_xmark = ImageIO.read(new File("resources/gui/circle_xmark.png"));
            circle_check = ImageIO.read(new File("resources/gui/circle_check.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
        hint_panel.setVisible(true);
        done_panel.setVisible(false);
        undo_panel.setVisible(false);
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(1600, 1000));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        board.setBounds(308, 94, board.tileSize * 8, board.tileSize * 8);
        this.add(board);
        title_bar_label = new JLabel("Puzzle - Lever " + lever);
        title_bar_label.setBounds(680, 4, 400, 50);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(back_normal, back_selected, 42, 42, "");
        home_normal_button = new ButtonImage(home_normal, home_selected, 42, 42, "");
        back_normal_button.setBounds(465, 10, 42, 42);
        home_normal_button.setBounds(1000, 10, 42, 42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.panelCardLayout.add(new ListPuzzle(), "listPuzzle");
                Menu.cardLayout.show(Menu.panelCardLayout, "listPuzzle");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        // setting menu hint
        hint_panel = new JPanel();
        hint_panel.setBounds(1026, 266, 268, 250);
        hint_panel.setBackground(new Color(55, 55, 55));
        hint_panel.setLayout(null);
        hint = new ButtonImage(normal, selected, 150, 50, "Hint");
        hint.setBounds(60, 140, 150, 50);
        hint_panel.add(hint);
        color_to_move = new JLabel();
        String key = board.color_to_move ? "Black to Move" : "White to Move";
        color_to_move.setText(key);
        try {
            color_to_move.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 30));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        color_to_move.setBounds(18, 60, 240, 50);
        color_to_move.setForeground(Color.WHITE);
        hint_panel.add(color_to_move);
        this.add(hint_panel);
        // setting menu try again
        undo_panel = new JPanel();
        undo_panel.setBounds(1026, 266, 268, 250);
        undo_panel.setBackground(new Color(55, 55, 55));
        undo_panel.setLayout(null);
        undo = new ButtonImage(normal, selected, 150, 50, "Undo");
        undo.setBounds(58, 120, 150, 50);
        undo_panel.add(undo);
        failed = new JLabel();
        failed.setText("Try Again");
        try {
            failed.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 30));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        failed.setBounds(46, 60, 240, 60);
        failed.setForeground(Color.WHITE);
        undo_panel.add(failed);
        this.add(undo_panel);
        // setting menu done
        done_panel = new JPanel();
        done_panel.setBounds(1026, 266, 268, 250);
        done_panel.setBackground(new Color(55, 55, 55));
        done_panel.setLayout(null);
        next_lever = new ButtonImage(normal, selected, 150, 50, "Next Lever");
        next_lever.setBounds(60, 180, 150, 50);
        try_again = new ButtonImage(normal, selected, 150, 50, "Try Again");
        try_again.setBounds(60, 120, 150, 50);
        done_panel.add(next_lever);
        done_panel.add(try_again);
        correct = new JLabel();
        correct.setText("Next Lever");
        try {
            correct.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 30));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        correct.setBounds(40, 40, 240, 60);
        correct.setForeground(Color.WHITE);
        done_panel.add(correct);
        this.add(done_panel);
        // listener
        hint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.setHintBoolean(true);
                board.repaint();
            }
        });
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.panelCardLayout.add(new PuzzleGame(FEN, lever), "puzzleGame");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
            }
        });
        try_again.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.panelCardLayout.add(new PuzzleGame(FEN, lever), "puzzleGame");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
            }
        });
        next_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (lever < 100) {
                    nextLever(++lever);
                }
            }
        });
    }

    public void nextLever(int lever) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("resources/puzzles.csv"));
            int count = 1;
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (count == lever) {
                    Menu.panelCardLayout.add(new PuzzleGame(line, lever), "puzzleGame");
                    Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
                    return;
                }
                ++count;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(title_bar, 530, 10, 450, 42, this);
        g2d.drawImage(board_index, 280, 70, this);
        g2d.drawImage(panel_320_292, 1020, 260, 280, 260, this);
    }
}
