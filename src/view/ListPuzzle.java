package view;

import model.JDBCConnection;
import model.ReadImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListPuzzle extends JPanel {
    private JPanel panel_contains_puzzle_page_1;
    private JPanel panel_contains_puzzle_page_2;
    private JPanel panel_contains_puzzle_page_3;
    private JLabel title_bar_label;
    private Image puzzle_normal;
    private Image puzzle_selected;
    private Image puzzle_failed_normal;
    private Image puzzle_failed_selected;
    private Image puzzle_solved_normal;
    private Image puzzle_solved_selected;
    private Image forward_normal_game;
    private Image forward_normal_game_v2;
    private Image forward_selected_game;
    private Image forward_selected_game_v2;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private ButtonImage puzzle[] = new ButtonImage[106];
    private ButtonImage forward_left;
    private ButtonImage forward_right;
    private int cnt = 1;
    private JLabel page;
    String _page[] = { "1/3", "2/3", "3/3" };
    private int index_page = 0;
    private ArrayList<String> arr;

    public ListPuzzle() {
        arr = new ArrayList<String>();
        arr = JDBCConnection.takeDataPuzzle();
        try {
            forward_normal_game = ImageIO.read(new File("resources/buttons/forward_normal.png"));
            forward_normal_game_v2 = ImageIO.read(new File("resources/buttons/forward_normalv2.png"));
            forward_selected_game = ImageIO.read(new File("resources/buttons/forward_selected.png"));
            forward_selected_game_v2 = ImageIO.read(new File("resources/buttons/forward_selectedv2.png"));
            puzzle_normal = ImageIO.read(new File("resources/buttons/puzzle_normal.png"));
            puzzle_selected = ImageIO.read(new File("resources/buttons/puzzle_selected.png"));
            puzzle_failed_normal = ImageIO.read(new File("resources/buttons/puzzle_failed_normal.png"));
            puzzle_failed_selected = ImageIO.read(new File("resources/buttons/puzzle_failed_selected.png"));
            puzzle_solved_normal = ImageIO.read(new File("resources/buttons/puzzle_solved_normal.png"));
            puzzle_solved_selected = ImageIO.read(new File("resources/buttons/puzzle_solved_selected.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPanel();
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(1536, 864));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        title_bar_label = new JLabel("Puzzles");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(20.0f));
        this.add(title_bar_label);
        // ----------------------
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
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
        //
        forward_left = new ButtonImage(forward_normal_game, forward_selected_game, 32, 32, "");
        forward_left.setBounds(700, 740, 32, 32);
        forward_right = new ButtonImage(forward_normal_game_v2, forward_selected_game_v2, 32, 32, "");
        forward_right.setBounds(788, 740, 32, 32);
        forward_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (index_page > 0) {
                    --index_page;
                }
                if (index_page == 0) {
                    panel_contains_puzzle_page_2.setVisible(false);
                    panel_contains_puzzle_page_3.setVisible(false);
                    panel_contains_puzzle_page_1.setVisible(true);
                } else if (index_page == 1) {
                    panel_contains_puzzle_page_1.setVisible(false);
                    panel_contains_puzzle_page_3.setVisible(false);
                    panel_contains_puzzle_page_2.setVisible(true);
                }
                page.setText(_page[index_page]);
            }
        });
        forward_right.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (index_page < _page.length - 1) {
                    ++index_page;
                }
                if (index_page == 2) {
                    panel_contains_puzzle_page_1.setVisible(false);
                    panel_contains_puzzle_page_2.setVisible(false);
                    panel_contains_puzzle_page_3.setVisible(true);
                } else if (index_page == 1) {
                    panel_contains_puzzle_page_1.setVisible(false);
                    panel_contains_puzzle_page_3.setVisible(false);
                    panel_contains_puzzle_page_2.setVisible(true);
                }
                page.setText(_page[index_page]);
            }
        });
        this.add(forward_left);
        this.add(forward_right);
        page = new JLabel();
        page.setForeground(Color.WHITE);
        page.setFont(new Font("", Font.PLAIN, 20));
        page.setText(_page[index_page]);
        page.setBounds(746, 740, 60, 30);
        this.add(page);
        //
        initPage1();
        initPage2();
        initPage3();
        panel_contains_puzzle_page_2.setVisible(false);
        panel_contains_puzzle_page_3.setVisible(false);
        this.add(panel_contains_puzzle_page_1);
        this.add(panel_contains_puzzle_page_2);
        this.add(panel_contains_puzzle_page_3);
        readFEN();
        initPuzzleFailed();
        initPuzzleSolved();
    }

    public void readFEN() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources/puzzles.csv"));
            int lever = 1;
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                initController(line, lever);
                ++lever;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void initPage1() {
        panel_contains_puzzle_page_1 = new JPanel();
        panel_contains_puzzle_page_1.setBackground(new Color(41, 41, 41));
        panel_contains_puzzle_page_1.setLayout(null);
        panel_contains_puzzle_page_1.setBounds(260, 100, 1000, 620);
        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(puzzle_normal, puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = puzzle_normal;
                puzzle[cnt].selected1 = puzzle_selected;
                puzzle[cnt].setBounds(X, Y, 80, 80);
                panel_contains_puzzle_page_1.add(puzzle[cnt]);
                X += 80 + 70;
                ++cnt;
            }
            Y += 80 + 50;

        }
    }

    public void initPage2() {
        panel_contains_puzzle_page_2 = new JPanel();
        panel_contains_puzzle_page_2.setBackground(new Color(41, 41, 41));
        panel_contains_puzzle_page_2.setLayout(null);
        panel_contains_puzzle_page_2.setBounds(260, 100, 1000, 620);

        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(puzzle_normal, puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = puzzle_normal;
                puzzle[cnt].selected1 = puzzle_selected;
                puzzle[cnt].setBounds(X, Y, 80, 80);
                panel_contains_puzzle_page_2.add(puzzle[cnt]);
                X += 80 + 70;
                ++cnt;
            }
            Y += 80 + 50;

        }
    }

    public void initPage3() {
        panel_contains_puzzle_page_3 = new JPanel();
        panel_contains_puzzle_page_3.setBackground(new Color(41, 41, 41));
        panel_contains_puzzle_page_3.setLayout(null);
        panel_contains_puzzle_page_3.setBounds(260, 100, 1000, 620);
        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(puzzle_normal, puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = puzzle_normal;
                puzzle[cnt].selected1 = puzzle_selected;
                puzzle[cnt].setBounds(X, Y, 80, 80);
                panel_contains_puzzle_page_3.add(puzzle[cnt]);
                X += 80 + 70;
                ++cnt;
                if (cnt == 101)
                    return;
            }
            Y += 80 + 50;
        }
    }

    public void initController(final String FEN, final int lever) {
        /*
         * mouseClicked cần truy cập FEN nhưng lever không thể truy cập trực tiếp vì
         * chúng là các biến cục bộ.
         * Khai báo FEN and lever as final cung cấp quyền truy cập an toàn và có kiểm
         * soát từ lớp bên trong ẩn danh.
         */
        puzzle[lever].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Menu.panelCardLayout.add(new PuzzleGame(FEN, lever), "puzzleGame");
                Menu.cardLayout.show(Menu.panelCardLayout, "puzzleGame");
            }
        });
    }

    public void initPuzzleFailed() {
        String s = arr.get(0);
        if (s.isEmpty())
            return;
        else {
            String number[] = s.split(",");
            for (String x : number) {
                puzzle[Integer.parseInt(x)].setNormal(puzzle_failed_normal);
                puzzle[Integer.parseInt(x)].setSelected(puzzle_failed_selected);
                puzzle[Integer.parseInt(x)].setImage(puzzle_failed_normal);
            }
        }
    }

    public void initPuzzleSolved() {
        String s = arr.get(1);
        if (s.isEmpty())
            return;
        else {
            String number[] = s.split(",");
            for (String x : number) {
                puzzle[Integer.parseInt(x)].setNormal(puzzle_solved_normal);
                puzzle[Integer.parseInt(x)].setSelected(puzzle_solved_selected);
                puzzle[Integer.parseInt(x)].setImage(puzzle_solved_normal);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
    }
}
