package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.JDBCConnection;
import model.ReadImage;
import model.Thread_MNX;
import view.*;

public class MoveController implements MouseListener,MouseMotionListener {
	private ViewBoard view;
	private PlayerView P;
	private DialogPromotion D;
	private GamePVC G;
	public MoveController(ViewBoard view, PlayerView player, DialogPromotion dialog,GamePVC pvc) {
		super();
		this.view = view;
		this.P=player;
		this.D = dialog;
		this.G=pvc;
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
			this.P.setDragX(this.G.getPanel().getMy_board().board1[this.P.getMouseY()/this.view.getSquare_size()][this.P.getMouseX()/this.view.getSquare_size()].getLct_in_image_X());
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
					String rs = this.G.textArea.getText()+String.format("%3s %8s %6s", this.G.getCnt_move()+".","*7_ks","");
					this.G.textArea.setText(rs);
					this.view.getStrSaveData().append("0-0____");
					Thread_MNX move_after_castling =new Thread_MNX(this.G,true);
					move_after_castling.start();
				}
			}
			if(this.P.getMouseX()/this.view.getSquare_size()==2)
			{
				if(this.view.getMy_board().board1[this.P.getFromRow()][this.P.getFromCol()].move(this.view.getMy_board()).contains("*7_qs"))
				{
					this.view.make_move_animated("*7_qs");
					String rs = this.G.textArea.getText()+String.format("%3s %8s %6s", this.G.getCnt_move()+".","*7_qs","");
					this.G.textArea.setText(rs);
					this.view.getStrSaveData().append("0-0-0__");
					Thread_MNX move_after_castling =new Thread_MNX(this.G,true);
					move_after_castling.start();
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
			System.out.println(this.view.getMy_board().getDepth());
			this.P.setActiveValid_move(false);
			ReadImage.sound.playMusic(2);
			this.view.getStrSaveData().append(this.view.convert(expected_move,true));
			if((char)this.view.convert(expected_move,true).charAt(6)=='#')
			{
				for(int i=0;i<this.view.getStrSaveData().length();i+=14)
				{
					if(i+14<=this.view.getStrSaveData().length())
					{
						this.view.getStrSaveData().insert(i+14, "\n");
						i++;
					}
				}
				String result=this.view.getMy_board().convertDate()
						+ "\nPvC\n" +
		                        "3" +
		                        "\nCheckMate\n" +
		                        "White win\n"+
						this.view.getStrSaveData();
				JDBCConnection.insertHistory(result);
				Menu.panelCardLayout.add(new History(),"history");
				this.G.TurnEndGameLog();
				System.out.println(result);
			}
			//"%3s %8s %6s"
			String rs = this.G.textArea.getText()+String.format("%3s %8s %6s", this.G.getCnt_move()+".",expected_move,"");
			this.G.textArea.setText(rs);
			int rankOfmove=this.view.getMy_board().sort_list_move(this.view.getMy_board().possible_move(true), true).indexOf(expected_move);
			if(rankOfmove<10) this.G.setLabel(1);
			else if(rankOfmove<20) this.G.setLabel(2);
			else if(rankOfmove<40) this.G.setLabel(3);
			else this.G.setLabel(4);
			this.view.player_make_move(expected_move);
			if(this.view.getOp().getOn_openings())
			{
				this.view.getOp().setMoved(expected_move);
				this.view.getOp().posible_openings();
				if(this.view.getOp().getOn_openings())
				{
					String AI_openings_move=this.view.getOp().Suggest_move();
					this.G.textArea.setText(this.G.textArea.getText()+AI_openings_move+"\n");
					this.G.setCnt_move(this.G.getCnt_move()+1);
					this.view.getStrSaveData().append(this.view.convert(AI_openings_move,true));
					this.view.getOp().setMoved(AI_openings_move);
					this.view.make_move_animated(AI_openings_move);	
				}
			}
			if(this.view.getOp().getOn_openings()==false) {
				Thread_MNX enemy_move=new Thread_MNX(this.G,false);
				enemy_move.start();
				this.G.setCnt_move(this.G.getCnt_move()+1);
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
		String t="123456789";
		System.out.println(t.indexOf("45"));
	}
}
