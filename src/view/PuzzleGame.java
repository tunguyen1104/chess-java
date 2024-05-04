package view;

import model.Board;
import model.ReadImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PuzzleGame extends JPanel {
    private String FEN;
    private int lever;
    private JLabel title_bar_label;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    
    private Board board;
    private ButtonImage hint;
    private ButtonImage undo;
    private ButtonImage try_again;
    private ButtonImage next_lever;
    private JPanel hint_panel;
    private JPanel undo_panel;
    private JPanel done_panel;
    private JLabel color_to_move;
    private JLabel correct;
    private JLabel failed;
    
    public PuzzleGame(String FEN, int lever) {
        this.FEN = FEN;
        this.lever = lever;
        board = new Board(this, FEN);
        initPanel();
        hint_panel.setVisible(true);
        done_panel.setVisible(false);
        undo_panel.setVisible(false);
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        board.setBounds(308, 100, board.tileSize * 8, board.tileSize * 8);
        this.add(board);
        title_bar_label = new JLabel("Puzzle - Lever " + lever);
        title_bar_label.setBounds(680, 4, 400, 50);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        this.add(title_bar_label);
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.panelCardLayout.add(new ListPuzzle(), "puzzle");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzle");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.panelCardLayout.add(new ListPuzzle(), "puzzle");
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        // setting menu hint
        hint_panel = new JPanel();
        hint_panel.setBounds(986, 266, 268, 250);
        hint_panel.setBackground(new Color(55, 55, 55));
        hint_panel.setLayout(null);
        hint = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Hint",0);
        hint.setBounds(60, 140, 152, 50);
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
        undo_panel.setBounds(986, 272, 268, 250);
        undo_panel.setBackground(new Color(55, 55, 55));
        undo_panel.setLayout(null);
        undo = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Undo",0);
        undo.setBounds(58, 120, 152, 50);
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
        done_panel.setBounds(986, 266, 268, 250);
        done_panel.setBackground(new Color(55, 55, 55));
        done_panel.setLayout(null);
        next_lever = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Next Lever",0);
        next_lever.setBounds(60, 180, 152, 50);
        try_again = new ButtonImage(ReadImage.menu_normal, ReadImage.menu_selected, 152, 50, "Try Again",0);
        try_again.setBounds(60, 120, 152, 50);
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
            public void mousePressed(MouseEvent e) {
                board.setHintBoolean(true);
                board.repaint();
            }
        });
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.panelCardLayout.add(new PuzzleGame(FEN, lever), "puzzleGame");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
            }
        });
        try_again.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.panelCardLayout.add(new PuzzleGame(FEN, lever), "puzzleGame");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
            }
        });
        next_lever.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (lever < 100) {
                    nextLever(++lever);
                } else if(lever == 100) {
                    Menu.panelCardLayout.add(new ListPuzzle(), "puzzle");
                    Menu.cardLayout.show(Menu.panelCardLayout, "puzzle");
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
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
        g2d.drawImage(ReadImage.board_index, 280, 80,690,690, this);
        g2d.drawImage(ReadImage.panel_320_292, 980, 260, 280, 260, this);
    }
    public JPanel getHint_panel() {
        return hint_panel;
    }
    public JPanel getUndo_panel() {
        return undo_panel;
    }
    public JPanel getDone_panel() {
        return done_panel;
    }
    public String getFEN() {
        return FEN;
    }
    public int getLever() {
        return lever;
    }
}
