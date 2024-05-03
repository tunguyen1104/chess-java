package model.piecesv2;

import model.Board;
import model.BoardV2;

public class King extends Piece {
	private int row;
	private int col;
	private String id;
	private int lct_in_image_X,lct_in_image_Y;
	private boolean isWhite;
	private int Positional_board_mid[][]={
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-20,-30,-30,-40,-40,-30,-30,-20},
	        {-10,-20,-20,-20,-20,-20,-20,-10},
	        { 20, 20,  0,  0,  0,  0, 20, 20},
	        { 20, 30, 10,  0,  0, 10, 30, 20}};
	private int Positional_board_end[][]={
	        {-50,-40,-30,-20,-20,-30,-40,-50},
	        {-30,-20,-10,  0,  0,-10,-20,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-30,  0,  0,  0,  0,-30,-30},
	        {-50,-30,-30,-30,-30,-30,-30,-50}};  
	public King(boolean iswhite)
	{
		this.isWhite=iswhite;
		this.id="a";
		if(this.isWhite)
		{
			this.lct_in_image_Y=0;
			this.lct_in_image_X=0;
		}
		else
		{
			this.lct_in_image_Y=1;
			this.lct_in_image_X=0;
		}
	}
	public King(boolean iswhite, int row, int col)
	{
		this.isWhite=iswhite;
		this.row=row;
		this.col=col;
		this.id="a";
		if(this.isWhite)
		{
			this.lct_in_image_Y=0;
			this.lct_in_image_X=0;
		}
		else
		{
			this.lct_in_image_Y=1;
			this.lct_in_image_X=0;
		}
	}
	public int getLct_in_image_X() {
		return lct_in_image_X;
	}
	public int getLct_in_image_Y() {
		return lct_in_image_Y;
	}
	public void setRow(int r)
	{
		this.row=r;
	}
	public void setCol(int c)
	{
		this.col=c;
	}
	public int getRow() {
		return this.row;
	}
	public int getCol()
	{
		return this.col;
	}
	public String getId() {
		return this.id;
	}
	public boolean isWhite()
	{
		return this.isWhite;
	}
	public String move(BoardV2 a)
	{
		String list="";
		Piece enemy_piece=new Piece();
			for(int i=0;i<9;i++)
			{
				if(i!=4)
				{
					try {
						if(a.board1[this.row-1+i/3][this.col-1+i%3]==null||a.board1[this.row-1+i/3][this.col-1+i%3].isWhite()!=this.isWhite)
						{
							enemy_piece=a.board1[this.row-1+i/3][this.col-1+i%3];
							a.setKing(this.isWhite,this.row-1+i/3,this.col-1+i%3);
							a.board1[this.row][this.col]= null; 
							if(a.is_kingsafe(this.isWhite))
							{
								if(enemy_piece!=null)
								list+=""+this.row+this.col+(this.row-1+i/3)+(this.col-1+i%3)+enemy_piece.getId();//list=1123k meanings from [1][1] to [2][3] captures knight !
								else
								{
									list+=""+this.row+this.col+(this.row-1+i/3)+(this.col-1+i%3)+" ";
								}							
							}
							a.setKing(this.isWhite,this.row,this.col);
							a.board1[this.row-1+i/3][this.col-1+i%3]=enemy_piece;
						}
					}
					catch(Exception e) {};
				}
			}
			String temp_moved_king_rook=a.getMoved_rook_king_b();
			int row_side=0;
			if(this.isWhite) 
			{
				temp_moved_king_rook=a.getMoved_rook_king_w();
				row_side=7;
			}
			//check King has moved ever ?
			if(temp_moved_king_rook.length()==temp_moved_king_rook.replace(""+row_side+"4","").length())
			{
				//check "rook queen side" has moved ever ?
				if(temp_moved_king_rook.length()==temp_moved_king_rook.replace(""+this.row+"0","").length()&&a.board1[this.row][0]!=null&&a.board1[this.row][0].getId()=="r"&&a.board1[this.row][0].isWhite()==this.isWhite)
				{
					//check has any piece between king and rook queen's side
					int check=0;
					for(int i=1;i<4;i++)
					{
						if(a.board1[this.row][i]!=null) check=1;
					}
					if(check==0)
					{
						a.setKing(this.isWhite, this.row, 2);
						a.setRook(this.isWhite,this.row,3);
						a.board1[this.row][4]=null;
						a.board1[this.row][0]=null;
						if(a.is_kingsafe(this.isWhite))
						{
							list+="*"+this.row+"_qs";
						}
						a.setKing(this.isWhite, this.row, this.col);
						a.setRook(isWhite, this.row, 0);
						a.board1[this.row][2]=null;
						a.board1[this.row][3]=null;
					}
				}
				//check "rook king side" has moved ever ?
				if(temp_moved_king_rook.length()==temp_moved_king_rook.replace(""+this.row+"7","").length()&&a.board1[this.row][7]!=null&&a.board1[this.row][7].getId()=="r"&&a.board1[this.row][7].isWhite()==this.isWhite)
				{ 
					//check has any piece between king and rook king's side
					int check=0;
					for(int i=6;i>4;i--)
					{
						if(a.board1[this.row][i]!=null) check=1;
					}
					if(check==0)
					{
						a.setKing(this.isWhite, this.row, 6);
						a.setRook(this.isWhite,this.row,5);
						a.board1[this.row][this.col]=null;
						a.board1[this.row][7]=null;
						if(a.is_kingsafe(this.isWhite))
						{
							list+="*"+this.row+"_ks";
						}
						a.setKing(this.isWhite, this.row, 4);
						a.setRook(isWhite, this.row, 7);
						a.board1[this.row][6]=null;
						a.board1[this.row][5]=null;
					}
				}
			}
		return list;
	}
	public int rating_positional(int a, BoardV2 b)
	{
		if(a>1750) {
			int point_castling=0;
			if(this.isWhite==true)
			{
				if(this.row==7&&this.col==2&&b.board1[7][3]!=null&&b.board1[7][3].getId()=="r") point_castling=700;
				if(this.row==7&&this.col==6&&b.board1[7][5]!=null&&b.board1[7][5].getId()=="r") point_castling=100;
				return this.Positional_board_mid[this.row][this.col]+point_castling;
			}
			else
			{
				if(this.row==0&&this.col==2&&b.board1[0][3]!=null&&b.board1[0][3].getId()=="r") point_castling=700;
				if(this.row==0&&this.col==6&&b.board1[0][5]!=null&&b.board1[0][5].getId()=="r") point_castling=100;
				return this.Positional_board_mid[7-this.row][7-this.col]+point_castling;
			}
		
		}
		else {
			if(this.isWhite==true)
				return this.Positional_board_end[this.row][this.col];
			else
				return this.Positional_board_end[7-this.row][7-this.col];
		}
	}

}

