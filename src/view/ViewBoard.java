package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;

import model.*;
import model.pieces.Piece;

public class ViewBoard extends JPanel implements ActionListener  {
	private PlayerView P;
	final int width=640;
	final int height=640;
	BoardV2 my_board;
	Image img_chessboard,img_tick,img_border_cell,img_border_red,img_ischeck,img_promotion,img_castling;
	private int square_size=80;
	private boolean AI_turn;
	private int stX,stY,eX,eY,qdX,qdY,enemy_lct_X,enemy_lct_Y,hsX,hsY;
	String enemy_move;
	Piece temp_piece;
	Timer timer;
	DialogPromotion my_dialog;
	Boolean is_check=false;
	private Openings op;
	private StringBuilder StrSaveData;
	ViewBoard()
	{
		StrSaveData= new StringBuilder("");
		this.P=new PlayerView();
		hsX=1;
		hsY=1;
		this.timer = new Timer(10,this);
		this.AI_turn=false;
		this.op=new Openings();
		this.op.read_file();
		this.setBounds(100, 100, width, height);
		this.setLocation(100,100);
		try {
			img_chessboard = (Image)ReadImage.piece;
			img_tick = ImageIO.read(new File("resources/img_src/Tick2.png"));
			img_border_cell = ImageIO.read(new File("resources/img_src/Green_square.png"));
			img_ischeck = ImageIO.read(new File("resources/img_src/Red_border2.png"));
			img_border_red = ImageIO.read(new File("resources/img_src/Red_border.png"));
			img_promotion = ImageIO.read(new File("resources/img_src/level_up4.png"));
			img_castling = ImageIO.read(new File("resources/img_src/Green_square2.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		my_board=new BoardV2();
		//my_board.create_game();
		my_board.test();
	}
	public StringBuilder getStrSaveData() {
		return StrSaveData;
	}
	public void setStrSaveData(StringBuilder strSaveData) {
		StrSaveData = strSaveData;
	}
	public Openings getOp() {
		return op;
	}
	public PlayerView getP() {
		return P;
	}
	public DialogPromotion getMy_dialog() {
		return my_dialog;
	}
	public void setMy_dialog(DialogPromotion my_dialog) {
		this.my_dialog = my_dialog;
	}
	public boolean isAI_turn() {
		return AI_turn;
	}
	public void setAI_turn(boolean aI_turn) {
		AI_turn = aI_turn;
	}
	public BoardV2 getMy_board() {
		return my_board;
	}
	public int getSquare_size() {
		return square_size;
	}
	public void setIs_check(Boolean is_check) {
		this.is_check = is_check;
	}
	public boolean checked(String move,boolean isW)
	{
		this.getMy_board().make_move(move);
		if(!this.getMy_board().is_kingsafe(!isW)) 
		{
			this.getMy_board().undo_move(move);
			return false;
		}
		else {
			this.getMy_board().undo_move(move);
			return true;
		}
	}
	public boolean checked_mate(String move,boolean isW)
	{
		this.getMy_board().make_move(move);
		if(this.getMy_board().possible_move(!isW).length()==0) 
		{
			this.getMy_board().undo_move(move);
			return true;
		}
		else {
			this.getMy_board().undo_move(move);
			return false;
		}
	}
	public String convert(String imove,boolean isW)
	{
		String omove="";
		if((char)imove.charAt(0)=='*'){
			if(imove.substring(3).equals("ks"))
				omove+="0-0____";
			if(imove.substring(3).equals("qs"))
				omove+="0-0-0__";
		}
		else
		{
			if(Character.getNumericValue(imove.charAt(3))<=7&&Character.getNumericValue(imove.charAt(3))!=-1)
			{
				omove+=""+imove.charAt(1)+imove.charAt(0)+imove.charAt(3)+imove.charAt(2);
				if(imove.charAt(4)==' ')
				{
					omove+="__";
				}
				else omove+=imove.charAt(4)+"_";
				if(!checked(imove,isW))
				{
					if(checked_mate(imove,isW))
					{
						omove+="#";
					}
					else omove+="+";
				}
				else omove+="_";
			}
			//612pk
			else {
				if(!isW)
				{
					omove+=""+imove.charAt(1)+imove.charAt(0)+imove.charAt(2)+(Character.getNumericValue(imove.charAt(0))+1);
				}
				else
					omove+=""+imove.charAt(1)+imove.charAt(0)+imove.charAt(2)+(Character.getNumericValue(imove.charAt(0))-1);
				if(imove.charAt(3)==' ')
				{
					omove+="_";
				}
				else omove+=imove.charAt(3);
				String temp=""+imove.charAt(4);
				omove+=temp.toUpperCase();
				if(!checked(imove,isW))
				{
					if(checked_mate(imove,isW))
					{
						omove+="#";					
					}
					else omove+="+";
				}
				else omove+="_";
					
			}
		}
		return omove;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(ReadImage.board_image, 0, 0, 80 * 8, 80 * 8, this);
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(my_board.board1[i][j]!=null)
				{
					g.drawImage(img_chessboard, j*80, i*80, j*80+80, i*80+80,
							my_board.board1[i][j].getLct_in_image_X()*100,my_board.board1[i][j].getLct_in_image_Y()*100 , my_board.board1[i][j].getLct_in_image_X()*100+100, my_board.board1[i][j].getLct_in_image_Y()*100+100, this);
				}
			}
		}
		if(this.P.isDrag()==true)
		{
			g.drawImage(img_chessboard, this.P.getMouseX(), this.P.getMouseY(), this.P.getMouseX()+square_size, this.P.getMouseY()+square_size, this.P.getDragX()*100, this.P.getDragY()*100, this.P.getDragX()*100+100, this.P.getDragY()*100+100, this);
		}
		if(this.AI_turn==true)
		{
			g.drawImage(img_chessboard, stX, stY, stX+square_size, stY+square_size, enemy_lct_X*100, enemy_lct_Y*100, enemy_lct_X*100+100, enemy_lct_Y*100+100, null);
		}
		if(this.P.isActiveValid_move())
		{ 
			float alpha1=0.5f;
			AlphaComposite ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha1);
			g2D.setComposite(ac);
			g2D.drawImage(img_border_cell, this.P.getFromCol()*80,this.P.getFromRow()*80 , 80, 80, null);
			String valid_move=this.my_board.board1[this.P.getFromRow()][this.P.getFromCol()].move(my_board);
			if(valid_move.length()>0) {
				for(int i=0;i<valid_move.length();i+=5)
				{
					if(Character.getNumericValue(valid_move.charAt(i+4))==-1)
					{
						float alpha=0.2f;
						AlphaComposite ac2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						g2D.setComposite(ac2);
						g2D.drawImage(img_tick, (Character.getNumericValue(valid_move.charAt(i+3)))*square_size+17, (Character.getNumericValue(valid_move.charAt(i+2)))*square_size+14, 46, 52, null);
					}
					else if(Character.getNumericValue(valid_move.charAt(i+4))!=-1&&Character.getNumericValue(valid_move.charAt(i+3))<=7&&Character.getNumericValue(valid_move.charAt(i+3))!=-1) 
					{
						float alpha2=1.0f;
						AlphaComposite ac3=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2);
						g2D.setComposite(ac3);
						g2D.drawImage(img_border_red,(Character.getNumericValue(valid_move.charAt(i+3)))*square_size-29,(Character.getNumericValue(valid_move.charAt(i+2)))*square_size-6,138,92,null);
					}
					else if((char)valid_move.charAt(i)=='*')
					{
						float alpha=0.8f;
						AlphaComposite ac2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						g2D.setComposite(ac2);
						if(valid_move.substring(i+3,i+5).equals("qs"))
						{
							g2D.drawImage(img_castling, 157, 557, 86, 86, null);
						}
						if(valid_move.substring(i+3,i+5).equals("ks"))
						{
							g2D.drawImage(img_castling, 477, 557, 86, 86, null);
						}
					}
					else 
					{						
						float alpha=0.2f;
						AlphaComposite ac2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						g2D.setComposite(ac2);
						g2D.drawImage(img_promotion, Character.getNumericValue(valid_move.charAt(i+2))*80+14, 14,52, 52, null);
					}
				}
			}
		}
		if(this.is_check)
		{
			g2D.drawImage(img_ischeck,(this.my_board.getKing_location_w()%8)*square_size,(this.my_board.getKing_location_w()/8)*square_size,80,80,null);
		}
		}
	public void player_make_move(String move)
	{
		this.my_board.make_move(move);
		this.is_check=false;
		repaint();
	}
	public void make_move_animated(String move)
	{
		if((char)move.charAt(0)=='*')
		{
			Thread castling_animated=new ThreadCastling(this,move);
			castling_animated.start();
        }
		else
		{
			hsX=1;
			hsY=1;
			this.enemy_move=move;
			stX=Character.getNumericValue(move.charAt(1))*square_size;
			stY=Character.getNumericValue(move.charAt(0))*square_size;
			if(Character.getNumericValue(move.charAt(3))<=7&&Character.getNumericValue(move.charAt(3))!=-1)
			{
				eX=Character.getNumericValue(move.charAt(3))*square_size;
				eY=Character.getNumericValue(move.charAt(2))*square_size;
			}
			else {
				eX=Character.getNumericValue(move.charAt(2))*square_size;
				eY=7*square_size;
			}
			if(stX>eX) hsX=-1;
			if(stY>eY) hsY=-1;
			qdX=Math.abs(eX-stX);
			qdY=Math.abs(eY-stY);
			enemy_lct_X=this.my_board.board1[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))].getLct_in_image_X();
			enemy_lct_Y=this.my_board.board1[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))].getLct_in_image_Y();		
			this.temp_piece=this.my_board.board1[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			this.my_board.board1[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=null;
			this.AI_turn=true;
			timer.start();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((stX!=eX&&Math.abs(eX-stX)>qdX/4)||(stY!=eY&&Math.abs(eY-stY)>qdY/4))
		{
			stX=stX+qdX/20*hsX;
			stY=stY+qdY/20*hsY;
			repaint();
		}
		else if((stX!=eX&&Math.abs(eX-stX)<=qdX/4)||(stY!=eY&&Math.abs(eY-stY)<=qdY/4))
		{
			stX=stX+qdX/16*hsX;
			stY=stY+qdY/16*hsY;  
			repaint();
		}
		else {
			this.AI_turn=false;
			this.my_board.board1[(eY-qdY*hsY)/square_size][(eX-qdX*hsX)/square_size]=temp_piece;
			this.my_board.make_move(enemy_move);
			if(this.my_board.is_kingsafe(true)==false)
			{
				this.is_check=true;
			}
			repaint();
			timer.stop();
		}
	}
}
