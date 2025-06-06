package view;

import model.JDBCConnection;
import model.ReadImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListPuzzle extends JPanel {
    private JPanel panel_contains_puzzle_page_1;
    private JPanel panel_contains_puzzle_page_2;
    private JPanel panel_contains_puzzle_page_3;
    private JLabel title_bar_label;
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
        initPanel();
    }

    public void initPanel() {
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        title_bar_label = new JLabel("Puzzles");
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        title_bar_label.setBounds(( Menu.screenWidth - 60 ) / 2, 18, title_bar_label.getPreferredSize().width + 2, title_bar_label.getPreferredSize().height);
        this.add(title_bar_label);
        // ----------------------
        // setting back_normal, home_normal
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 - 65, 10, 44, 44);
        home_normal_button.setBounds(( Menu.screenWidth - 450 ) / 2 + 450 + 20, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        this.add(back_normal_button);
        this.add(home_normal_button);
        //
        forward_left = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_left.setBounds(Menu.screenWidth / 2 - 32 * 2 + 4, Menu.screenHeight - 120, 32, 32);
        forward_right = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_right.setBounds(Menu.screenWidth / 2 + 34, Menu.screenHeight - 120, 32, 32);
        forward_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
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
            public void mousePressed(MouseEvent e) {
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
        page.setBounds(Menu.screenWidth / 2 - 12, Menu.screenHeight - 116, 34, 20);
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
        panel_contains_puzzle_page_1.setBounds(Menu.screenWidth / 6, Menu.screenHeight / 8 - 8, 1000, 620);
        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(ReadImage.puzzle_normal, ReadImage.puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = ReadImage.puzzle_normal;
                puzzle[cnt].selected1 = ReadImage.puzzle_selected;
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
        panel_contains_puzzle_page_2.setBounds(Menu.screenWidth / 6, Menu.screenHeight / 8 - 8, 1000, 620);

        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(ReadImage.puzzle_normal, ReadImage.puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = ReadImage.puzzle_normal;
                puzzle[cnt].selected1 = ReadImage.puzzle_selected;
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
        panel_contains_puzzle_page_3.setBounds(Menu.screenWidth / 6, Menu.screenHeight / 8 - 8, 1000, 620);
        int X = 10;
        int Y = 10;
        for (int i = 0; i < 5; ++i) {
            X = 10;
            for (int j = 0; j < 7; ++j) {
                puzzle[cnt] = new ButtonImage(ReadImage.puzzle_normal, ReadImage.puzzle_selected, 80, 80, String.valueOf(cnt));
                puzzle[cnt].normal1 = ReadImage.puzzle_normal;
                puzzle[cnt].selected1 = ReadImage.puzzle_selected;
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
         * mousePressed cần truy cập FEN nhưng lever không thể truy cập trực tiếp vì
         * chúng là các biến cục bộ.
         * Khai báo FEN and lever as final cung cấp quyền truy cập an toàn và có kiểm
         * soát từ lớp bên trong ẩn danh.
         */
        puzzle[lever].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
                puzzle[Integer.parseInt(x)].setNormal(ReadImage.puzzle_failed_normal);
                puzzle[Integer.parseInt(x)].setSelected(ReadImage.puzzle_failed_selected);
                puzzle[Integer.parseInt(x)].setImage(ReadImage.puzzle_failed_normal);
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
                puzzle[Integer.parseInt(x)].setNormal(ReadImage.puzzle_solved_normal);
                puzzle[Integer.parseInt(x)].setSelected(ReadImage.puzzle_solved_selected);
                puzzle[Integer.parseInt(x)].setImage(ReadImage.puzzle_solved_normal);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, ( Menu.screenWidth - 450 ) / 2, 10, 450, 44, this);
    }
}
