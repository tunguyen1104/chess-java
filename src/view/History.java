package view;

import javax.swing.*;

import model.JDBCConnection;
import model.ReadImage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class History extends JPanel {
    private JLabel title_bar_label;
    private ButtonImage back_normal_button;
    private ButtonImage home_normal_button;
    private ArrayList<ButtonImage> listHistory = new ArrayList<>();
    private ArrayList<JPanel> panel_page = new ArrayList<>();
    private ArrayList<String> history;
    String format = "%-18s %-18s %-24s %-9s";
    private int index_page = 0;
    private int max_index_page = 0;
    private JLabel page;
    private ButtonImage forward_left;
    private ButtonImage forward_right;
    public History() {
        initPanel();
        handlePgn();
        addButtonHistory();
    }

    public void initPanel() {
        this.setBackground(new Color(41, 41, 41));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        title_bar_label = new JLabel("History");
        title_bar_label.setBounds(720, 0, 400, 60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        this.add(title_bar_label);
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
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

        forward_left = new ButtonImage(ReadImage.forward_normal, ReadImage.forward_selected, 32, 32, "");
        forward_left.setBounds(702, 740, 32, 32);
        forward_right = new ButtonImage(ReadImage.forward_normal_v2, ReadImage.forward_selected_v2, 32, 32, "");
        forward_right.setBounds(790, 740, 32, 32);
        forward_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (index_page > 0) {
                    --index_page;
                } else if (index_page == 0) {
                    return;
                }
                panel_page.get(index_page).setVisible(true);
                for(int i = 0;i < max_index_page; ++i) {
                    if(i != index_page) panel_page.get(i).setVisible(false);
                }
                page.setText(index_page + 1 + "/" + max_index_page);
            }
        });
        forward_right.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (index_page < max_index_page - 1) {
                    ++index_page;
                } else if (index_page == max_index_page - 1) {
                    return;
                }
                panel_page.get(index_page).setVisible(true);
                for(int i = 0;i < max_index_page; ++i) {
                    if(i != index_page) panel_page.get(i).setVisible(false);
                }
                page.setText(index_page + 1 + "/" + max_index_page);
            }
        });
        this.add(forward_left);
        this.add(forward_right);
        page = new JLabel();
        page.setForeground(Color.WHITE);
        page.setFont(new Font("", Font.PLAIN, 20));
        page.setBounds(748, 740, 60, 30);
        this.add(page);
    }
    public void setupPanelPage() {
        for(int i = 0;i < panel_page.size(); ++i) {
            panel_page.get(i).setLayout(null);
            panel_page.get(i).setBounds(460, 76, 588, 640);
            panel_page.get(i).setBackground(getBackground());
            this.add(panel_page.get(i));
        }
        for(int i = 1;i < panel_page.size(); ++i) {
            panel_page.get(i).setVisible(false);
        }
    }
    public void addButtonHistory() {
        int index_panel_page = 0;
        int size_list_history = listHistory.size();
        int index_list_history = 0;
        while(size_list_history > 0) {
            JPanel newPanel = new JPanel();
            panel_page.add(index_panel_page, newPanel);
            newPanel.add(listHistory.get(index_list_history));
            ++index_list_history;
            --size_list_history;
            int size = (size_list_history > 6 ? 6 : size_list_history);
            for(int i = 1;i <= size; ++i) {
                newPanel.add(listHistory.get(index_list_history));
                ++index_list_history;
            }
            size_list_history -= size;
            if((index_list_history) % 7 == 0) ++index_panel_page;
        }
        setupPanelPage();
        eventButtonHistory();
    }
    public void eventButtonHistory() {
        int count = 0;
        for (String pgn: history) {
            listHistory.get(count).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Menu.panelCardLayout.add(new Review(pgn),"review");
                    Menu.cardLayout.show(Menu.panelCardLayout,"review");
                }
            });
            ++count;
        }
    }
    public void handlePgn() {
        history = JDBCConnection.takeDataHistory();
        int X = 4, Y = 4;
        int count = 0;
        int index = 0;
        for (String data : history) {
            if(count % 7 == 0) {
                X = 4; Y = 4;
            }
            ArrayList<String> value = new ArrayList<>();
            int cnt = 0;
            try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    value.add(line.trim());
                    ++cnt;
                    if(cnt == 5) break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String formatted = String.format(format, value.get(1),value.get(2), value.get(3),value.get(3).equals("Timeout") ? "   " + value.get(4) : value.get(4));
            listHistory.add(new ButtonImage(ReadImage.history_normal, ReadImage.history_selected, 580, 76, value.get(0), formatted));
            listHistory.get(index).setBounds(X, Y, 580, 76);
            Y += 90;
            ++count;
            ++index;
        }
        max_index_page = (history.size() + 6) / 7;
        page.setText(1 + "/" + max_index_page);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
    }
}
