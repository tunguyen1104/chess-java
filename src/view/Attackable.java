package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Attackable extends JPanel implements ActionListener{
	private int X,Y;
	Timer timer;
	public Attackable(int x, int y)
	{
		this.X=x;
		this.Y=y;
		this.timer=new Timer(1000,this);
		timer.start();
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
