package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Thread_MNX;
import view.DialogPromotion;
import view.ViewBoard;

public class SelectPromotion implements MouseListener,MouseMotionListener{

	private DialogPromotion dialog;
	private ViewBoard board;
	public SelectPromotion(DialogPromotion d , ViewBoard v)
	{
		this.dialog=d;
		this.board=v;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX()>70 && e.getX()<390 && e.getY()>90 && e.getY()<170 && (e.getX()-70)/80!=(this.dialog.getMovedX()-70)/80)
		{
			this.dialog.setMovedX(e.getX());
			this.dialog.setMovedY(e.getY());
			this.dialog.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String promotion_piece ="";
		if(e.getX()>70 && e.getX()<390 && e.getY()>90 && e.getY()<170)
		{
			switch((e.getX()-70)/80) {
			case 0:
				promotion_piece="q";
				break;
			case 1:
				promotion_piece="b";
				break;
			case 2:
				promotion_piece="k";
				break;
			case 3:
				promotion_piece="r";
				break;
			}
		}
		String promotion_move="1"+this.dialog.getFromCol()+this.dialog.getEndCol()+this.dialog.getEnemy_piece()+promotion_piece;
		this.board.getMy_board().make_move(promotion_move);
		this.board.getP().setActiveValid_move(false);
		this.board.repaint();
		this.dialog.setVisible(false);
		Thread_MNX enemy_move=new Thread_MNX(this.board);
		enemy_move.start();
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
