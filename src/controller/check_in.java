package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.PlayerView;
import view.ViewBoard;

public class check_in implements MouseListener{
	private PlayerView view;
	public check_in(PlayerView v)
	{
		super();
		this.view=v;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		this.view.setCheck_in(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.view.setCheck_in(false);
	}

}
