package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Thread_MNX;
import view.DialogPromotion;
import view.PlayerView;
import view.ThreadDialog;
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
		if(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()]!=null&&this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].isWhite()==this.P.isWho_turn())
		{
			this.P.setFromCol(this.P.getMouseX()/this.view.getSquare_size());
			this.P.setFromRow(this.P.getMouseY()/this.view.getSquare_size());
			this.D.setFromCol(this.P.getMouseX()/this.view.getSquare_size());
			this.P.setDragX(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getLct_in_image_X());
			this.P.setDragY(this.view.getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getLct_in_image_Y());
			this.P.setDrag(true);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.P.setActiveValid_move(false);
		this.P.setMouseX(e.getPoint().x);
		this.P.setMouseY(e.getPoint().y);
		this.D.setEndCol(e.getPoint().x/this.view.getSquare_size());
		String enemy_piece_id="";
		String released_move="";
		if(this.view.getMy_board().board1[this.P.getFromRow()][this.P.getFromCol()].getId()=="a"&&this.P.getMouseY()/this.view.getSquare_size()==7)
		{
			if(this.P.getMouseX()/this.view.getSquare_size()==6)
			{
				if(this.view.getMy_board().board1[this.P.getFromRow()][this.P.getFromCol()].move(this.view.getMy_board()).contains("*7_ks"))
				{
					this.view.make_move_animated("*7_ks");
				}
			}
			if(this.P.getMouseX()/this.view.getSquare_size()==2)
			{
				if(this.view.getMy_board().board1[this.P.getFromRow()][this.P.getFromCol()].move(this.view.getMy_board()).contains("*7_qs"))
				{
					this.view.make_move_animated("*7_qs");
				}
			}
		}
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
				ThreadDialog promotion = new ThreadDialog(this.view.getMy_dialog());
				promotion.start();
			}	
		}
		String expected_move=""+this.P.getFromRow()+this.P.getFromCol()+(this.P.getMouseY()/this.view.getSquare_size())+(this.P.getMouseX()/this.view.getSquare_size())+enemy_piece_id;
		if(this.view.getMy_board().possible_move(true).length()!=this.view.getMy_board().possible_move(true).replaceAll(expected_move, "").length())
		{
			this.P.setActiveValid_move(false);
			this.view.player_make_move(expected_move);
			if(this.view.getOp().getOn_openings())
			{
				this.view.getOp().setMoved(expected_move);
				this.view.getOp().posible_openings();
				if(this.view.getOp().getOn_openings())
				{
					String AI_openings_move=this.view.getOp().Suggest_move();
					this.view.getOp().setMoved(AI_openings_move);
					this.view.make_move_animated(AI_openings_move);	
				}
			}
			if(this.view.getOp().getOn_openings()==false) {
				Thread_MNX enemy_move=new Thread_MNX(this.view);
				enemy_move.start();
			}
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
			this.P.setActiveValid_move(true);
			this.P.setMouseX(e.getPoint().x-40);
			this.P.setMouseY(e.getPoint().y-40);
			//this.P.setActiveValid_move(true);
			this.view.repaint();
			
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		System.out.println(Character.getNumericValue(' '));
	}
}
