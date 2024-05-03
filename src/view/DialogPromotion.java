package view;

import model.JDBCConnection;
import model.ReadImage;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogPromotion extends JDialog {
	Image img,red_border;
	int movedX, movedY,fromCol,endCol;
	String enemy_piece;
	public int getFromCol() {
		return fromCol;
	}

	public void setFromCol(int fromCol) {
		this.fromCol = fromCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public String getEnemy_piece() {
		return enemy_piece;
	}

	public void setEnemy_piece(String enemy_piece) {
		this.enemy_piece = enemy_piece;
	}

	public DialogPromotion(JFrame parent, String title, Boolean modal)
	{
		super(parent,title,modal);
		this.setSize(460,200);
		this.setLocationRelativeTo(parent);
		this.setUndecorated(false);
		JPanel panel= new JPanel();
		JLabel label = new JLabel("choose your piece you want promotion !");
		label.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		panel.add(label,BorderLayout.EAST);
		try {
			img = ImageIO.read(new File(JDBCConnection.takeDataSetting().get(0)));
			red_border = ImageIO.read(new File("resources/img_src/Red_border2.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		getContentPane().add(panel);
		movedX=-1;
		movedY=-1;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D g2D=(Graphics2D) g;
		g.drawImage(img, 70, 90, 390, 170,100,0,500,100 , this);
		if(movedX>70&&movedX<390&&movedY>90&&movedY<170)
		{
			g2D.drawImage(red_border, 70+80*((movedX-70)/80), 90, 80, 80, this);
		}
	}
	public int getMovedX() {
		return movedX;
	}

	public int getMovedY() {
		return movedY;
	}

	public void setMovedX(int movedX) {
		this.movedX = movedX;
	}
	public void setMovedY(int movedY) {
		this.movedY = movedY;
	}

}
