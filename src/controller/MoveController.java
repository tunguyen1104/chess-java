package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Thread_MNX;
import view.DialogPromotion;
import view.PlayerView;
import view.ViewBoard;

public class MoveController implements MouseListener,MouseMotionListener {
	private ViewBoard view;
	private PlayerView P;
	private DialogPromotion D;
	
	public MoveController(ViewBoard view, PlayerView player, DialogPromotion dialog) {
		super();
		this.view = view;
		this.P=player;
		this.D = dialog;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.P.setMouseX(e.getPoint().x);
		this.P.setMouseY(e.getPoint().y);	
		if(this.P.getMouseY()/this.view.getSquare_size()==this.P.getFromRow()&&this.P.getMouseX()/this.view.getSquare_size()==this.P.getFromCol())
		{
			this.P.setFromRow(-1);
			this.P.setFromCol(-1);
			this.P.setActiveValid_move(false);
			this.P.setDrag(true);
		}
		else if(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()]!=null&&this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].isWhite()==true)
		{
			this.P.setFromCol(this.P.getMouseX()/this.view.getSquare_size());
			this.P.setFromRow(this.P.getMouseY()/this.view.getSquare_size());
			this.D.setFromCol(this.P.getMouseX()/this.view.getSquare_size());
			this.P.setDragX(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getLct_in_image_X());
			this.P.setDragY(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getLct_in_image_Y());
			this.P.setDrag(true);
			this.P.setActiveValid_move(true);
		}
		if(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()]==null)
		{
			this.P.setActiveValid_move(false);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.P.setMouseX(e.getPoint().x);
		this.P.setMouseY(e.getPoint().y);
		this.D.setEndCol(e.getPoint().x/this.view.getSquare_size());
		String enemy_piece_id="";
		String released_move="";
		if(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()]!=null)
		{
			enemy_piece_id=this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getId();
		}
		else
		{
			enemy_piece_id=" ";
		}
		this.D.setEnemy_piece(enemy_piece_id);
		if(this.view.getMy_board().board1[1][this.D.getFromCol()]!=null&&this.view.getMy_board().board1[1][this.D.getFromCol()].getId()=="p"&&this.P.getMouseY()/this.view.getSquare_size()==0) 
		{
			if(this.view.getMy_board().possible_move(true).length()-this.view.getMy_board().possible_move(true).replaceAll("1"+this.D.getFromCol()+this.D.getEndCol()+this.D.getEnemy_piece(), "").length()!=16)
			{
				this.view.repaint();
			}
			else {
				this.view.getMy_dialog().start();
			}	
		}
		String expected_move=""+this.P.getFromRow()+this.P.getFromCol()+(this.P.getMouseY()/this.view.getSquare_size())+(this.P.getMouseX()/this.view.getSquare_size())+enemy_piece_id;
		if(this.view.getMy_board().possible_move(true).length()!=this.view.getMy_board().possible_move(true).replaceAll(expected_move, "").length())
		{
			this.P.setActiveValid_move(false);
			this.view.player_make_move(expected_move);
			Thread_MNX enemy_move=new Thread_MNX(this.view);
			enemy_move.start();
		}
		else {
			this.view.repaint();
		}
		this.P.setDrag(false);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
			this.P.setMouseX(e.getPoint().x-40);
			this.P.setMouseY(e.getPoint().y-40);
			//this.P.setActiveValid_move(true);
			this.view.repaint();
			
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
