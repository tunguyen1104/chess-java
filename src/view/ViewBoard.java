package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.BoardV2;
import model.JDBCConnection;
import model.piecesv2.*;

public class ViewBoard extends JPanel implements ActionListener {
	private PlayerView P;
	final int width=640;
	final int height=640;
	BoardV2 my_board;
	private Image board_image;
	Image img_chessboard,img_tick,img_border_cell,img_border_red,img_ischeck;
	private int square_size=80;
	private boolean AI_turn;
	private int stX,stY,eX,eY,qdX,qdY,enemy_lct_X,enemy_lct_Y,hsX,hsY;
	String enemy_move;
	Piece temp_piece;
	Timer timer;
	ThreadDialog my_dialog;
	Boolean is_check=false;
	ViewBoard()
	{
		this.P=new PlayerView();
		hsX=1;
		hsY=1;
		this.timer = new Timer(10,this);
		this.AI_turn=false;
		this.setBounds(100, 100, width, height);
		this.setLocation(100,100);
		ArrayList<String> arr = JDBCConnection.takeDataSetting();
        try {
            img_chessboard = ImageIO.read(new File(arr.get(0)));
			board_image = ImageIO.read(new File(arr.get(1)));
			img_tick = ImageIO.read(new File("resources/img_src/Tick2.png"));
			img_border_cell = ImageIO.read(new File("resources/img_src/Green_square.png"));
			img_ischeck = ImageIO.read(new File("resources/img_src/Red_border2.png"));
			img_border_red = ImageIO.read(new File("resources/img_src/Red_border.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        my_board=new BoardV2();
		my_board.create_game();
		//my_board.test();
	}
	public PlayerView getP() {
		return P;
	}
	public ThreadDialog getMy_dialog() {
		return my_dialog;
	}
	public void setMy_dialog(ThreadDialog my_dialog) {
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
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g2D);
		g2D.drawImage(board_image, 0, 0, 80 * 8, 80 * 8, this);
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
			g.drawImage(img_chessboard, stX, stY, stX+square_size, stY+square_size, enemy_lct_X*100, enemy_lct_Y*100, enemy_lct_X*100+100, enemy_lct_Y*100+100, this);
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
					float alpha=0.2f;
					AlphaComposite ac2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
					g2D.setComposite(ac2);
					if(Character.getNumericValue(valid_move.charAt(i+4))==-1)
					{
						g2D.drawImage(img_tick, (Character.getNumericValue(valid_move.charAt(i+3)))*square_size+17, (Character.getNumericValue(valid_move.charAt(i+2)))*square_size+14, 46, 52, null);
					}
					else 
					{
						float alpha2=1.0f;
						AlphaComposite ac3=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2);
						g2D.setComposite(ac3);
						g2D.drawImage(img_border_red,(Character.getNumericValue(valid_move.charAt(i+3)))*square_size-29,(Character.getNumericValue(valid_move.charAt(i+2)))*square_size-6,138,92,null);
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
