package view;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Test extends JLabel{
	Icon img_border_cell;
	public Test()
	{
		setOpaque(true);
		setBounds(60, 250, 300,300);
		setBackground(Color.red);
		img_border_cell = new ImageIcon(this.getClass().getResource("/img_src/Blue_flame.gif"));
		this.setIcon(img_border_cell);
	}

	/*public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img_border_cell, 0,0 , 80, 80, this);
		super.paint(g);
	}*/

}
