package view;

import controller.MoveController;
import controller.SelectPromotion;
import model.ReadImage;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GamePVC extends JPanel {
    private JLabel title_bar_label;
    ButtonImage back_normal_button;
	ButtonImage home_normal_button;
    public JTextArea textArea,rating_box;
    public JScrollPane scrollPaneTextArea;
    private ViewBoard panel;
    private int cnt_move;
    private JLabel rating_move;
    private DialogEndGame EndGameLog;
    public GamePVC(int minute) {
    	this.cnt_move=1;
        this.setLayout(null);
        initPanel(minute);
        DialogPromotion dialog_promo = new DialogPromotion((JFrame)this.getParent(),"",true);
        this.panel = new ViewBoard();
        this.panel.setMy_dialog(dialog_promo);
        this.panel.setBounds(280, 100, 8 * 80, 8 * 80);
        MoveController m_ctrl=new MoveController(panel,panel.getP(),dialog_promo,this);
        panel.addMouseListener(m_ctrl);
        panel.addMouseMotionListener(m_ctrl);
        SelectPromotion choosing = new SelectPromotion(dialog_promo,this);
        dialog_promo.addMouseListener(choosing);
        dialog_promo.addMouseMotionListener(choosing);
        this.rating_move=new JLabel("");
        rating_move.setBackground(new Color(55, 55, 55));
        rating_move.setOpaque(true);
        //rating_move.setVisible(false);
		this.add(rating_move);
        this.add(panel);
    }
	public void initPanel(int minute) {
        this.setBackground(new Color(41, 41, 41));
        this.setPreferredSize(new Dimension(Menu.screenWidth, Menu.screenHeight));
        // ===========Title Bar============
        title_bar_label = new JLabel("Standard - PvC");
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(new Font("",Font.BOLD,20));
        title_bar_label.setBounds(( Menu.screenWidth - 140 ) / 2, 18, title_bar_label.getPreferredSize().width + 4, title_bar_label.getPreferredSize().height);
        // ================================
        back_normal_button = new ButtonImage(ReadImage.back_normal, ReadImage.back_selected, 44, 44, "");
        home_normal_button = new ButtonImage(ReadImage.home_normal, ReadImage.home_selected, 44, 44, "");
        back_normal_button.setBounds(465, 10, 44, 44);
        home_normal_button.setBounds(1000, 10, 44, 44);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "gameOptions");
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Menu.cardLayout.show(Menu.panelCardLayout, "menu");
            }
        });
        EndGameLog= new DialogEndGame("Black","CheckMate");
        EndGameLog.setBounds(300,90,640,640);
        EndGameLog.setVisible(false);
        this.add(EndGameLog);
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
        scrollPaneTextArea.setBounds(962, 270, 298, 348);
        Border border = BorderFactory.createLineBorder(new Color(55, 55, 55));
        scrollPaneTextArea.setBorder(border);
        scrollPaneTextArea.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(scrollPaneTextArea);
        rating_box = new JTextArea(String.format("%26s", "Rating your last move"));
        rating_box.setFont(new Font("",Font.BOLD|Font.ITALIC,22));
        rating_box.setBackground(new Color(55, 55, 55));
        rating_box.setForeground(Color.WHITE);
        rating_box.setEditable(false);
        rating_box.setBounds(962, 99, 298,148);
        this.add(rating_box);
        this.add(back_normal_button);
        this.add(home_normal_button);
        this.add(title_bar_label);
    }
	public void TurnEndGameLog() {
		this.EndGameLog.setOpaque(true);
		this.panel.setOpaque(false);
		this.EndGameLog.setVisible(true);
	}
	public void setLabel(int Case)
	{
		switch(Case)
		{
			case 1:
				Icon nice = new ImageIcon("resources/gif/nice.gif");
				this.rating_move.setIcon(nice);
		        this.rating_move.setBounds(1010, 140, 186, 80);
		        break;
			case 2:
				Icon nice2 = new ImageIcon("resources/gif/rating1.gif");
				this.rating_move.setIcon(nice2);
		        this.rating_move.setBounds(1055, 145, 103, 103);
		        break;
			case 3:
				Icon nice3 = new ImageIcon("resources/gif/wow-good.gif");
				this.rating_move.setIcon(nice3);
		        this.rating_move.setBounds(1035, 138, 128, 93);
		        break;
			case 4:
				Icon nice4 = new ImageIcon("resources/gif/ratingbad.gif");
				this.rating_move.setIcon(nice4);
		        this.rating_move.setBounds(1048, 145, 116, 100);
		        break;
		}
	}
	public JLabel getRating_move() {
		return rating_move;
	}
    public ViewBoard getPanel() {
		return panel;
	}
    public void setCnt_move(int cnt_move) {
		this.cnt_move = cnt_move;
	}

	public int getCnt_move() {
		return cnt_move;
	}
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.title_bar, 530, 10, 450, 44, this);
    }
}
