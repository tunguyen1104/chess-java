package model.piecesv2;
import model.BoardV2;
public class Pawn extends Piece {
	private int row;
	private int col;
	private String id;
	private int lct_in_image_X,lct_in_image_Y;
	private boolean isWhite;
	public int Positional_board[][]= { 
			{ 0,  0,  0,  0,  0,  0,  0,  0},
	        {50, 50, 50, 50, 50, 50, 50, 50},
	        {10, 10, 20, 30, 30, 20, 10, 10},
	        { 5,  5, 10, 25, 25, 10,  5,  5},
	        { 0,  0,  0, 20, 20,  0,  0,  0},
	        { 5, -5,-10,  0,  0,-10, -5,  5},
	        { 5, 10, 10,-20,-20, 10, 10,  5},
	        { 0,  0,  0,  0,  0,  0,  0,  0}			
	};
	public Pawn(boolean iswhite)
	{
		this.isWhite=iswhite;
		this.id="p";
		if(this.isWhite)
		{
			this.lct_in_image_Y=0;
			this.lct_in_image_X=5;
		}
		else
		{
			this.lct_in_image_Y=1;
			this.lct_in_image_X=5;
		}
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Pawn(boolean iswhite, int row, int col)
	{
		this.isWhite=iswhite;
		this.row=row;
		this.col=col;
		this.id="p";
		if(this.isWhite)
		{
			this.lct_in_image_Y=0;
			this.lct_in_image_X=5;
		}
		else
		{
			this.lct_in_image_Y=1;
			this.lct_in_image_X=5;
		}
	}
	public int getLct_in_image_X() {
		return lct_in_image_X;
	}
	public int getLct_in_image_Y() {
		return lct_in_image_Y;
	}
	public int getRow() {
		return this.row;
	}
	public int getCol() {
		return this.col;
	}
	public String getId() {
		return this.id;
	}
	public boolean isWhite() {
		return this.isWhite;
	}
	public String move(BoardV2 a)
	{
		int hs=0;
		if(this.isWhite) hs=-1;
		else hs=1;
		String list="";
		Piece enemy_piece=new Piece();
		if((this.isWhite==true&&this.row==6)||(this.isWhite==false&&this.row==1))
		{
			try {
				if(a.board1[this.row+2*hs][this.col]==null&&a.board1[this.row+hs][this.col]==null)
				{
					a.setPawn(this.isWhite,this.row+2*hs,this.col);
					a.board1[this.row][this.col]=null;
					if(a.is_kingsafe(this.isWhite))
					{
						list+=""+this.row+this.col+(this.row+2*hs)+(this.col)+" ";
					}
					a.board1[this.row+2*hs][this.col]=null;
					a.setPawn(this.isWhite,this.row,this.col);
				}
			}
			catch(Exception e) {};
		}
		if((this.isWhite==true&&this.row>1)||(this.isWhite==false&&this.row<6))
		{
			try {
				if(a.board1[this.row+hs][this.col]==null)
				{
					a.setPawn(this.isWhite,this.row+hs,this.col);
					a.board1[this.row][this.col]=null;
					if(a.is_kingsafe(this.isWhite))
					{
						list+=""+this.row+this.col+(this.row+hs)+(this.col)+" ";
					}
					a.setPawn(this.isWhite,this.row,this.col);
					a.board1[this.row+hs][this.col]=null;
				}
			}
			catch(Exception e) {};
			for(int i=-1;i<=1;i+=2)
			{
				try {
					if(a.board1[this.row+hs][this.col+hs*i].isWhite()!=this.isWhite)
					{
						enemy_piece=a.board1[this.row+hs][this.col+hs*i];
						a.setPawn(this.isWhite,this.row+hs,this.col+hs*i);
						a.board1[this.row][this.col]=null;
						if(a.is_kingsafe(this.isWhite))
						{
							list+=""+this.row+this.col+(this.row+hs)+(this.col+hs*i)+enemy_piece.getId();
						}
						a.board1[this.row+hs][this.col+hs*i]=enemy_piece;
						a.setPawn(this.isWhite,this.row,this.col);
					}
				}
				catch(Exception e) {};
			}
			}
		if((this.isWhite==true&&this.row==1)||(this.isWhite==false&&this.row==6))
		{
			try {
				if(a.board1[this.row+hs][this.col]==null)
				{
					Rook r=new Rook(this.isWhite,this.row+hs,this.col);
					Knight k=new Knight(this.isWhite,this.row+hs,this.col);
					Bishop b=new Bishop(this.isWhite,this.row+hs,this.col);
					Queen q=new Queen(this.isWhite,this.row+hs,this.col);
					Piece[] promotion_chess= {r,k,b,q};
					a.board1[this.row][this.col]=null;
					for(int i=0;i<4;i++)
					{
						a.board1[this.row+hs][this.col]=promotion_chess[i];
						if(a.is_kingsafe(this.isWhite))
						{
							list+=""+this.row+this.col+(this.col)+" "+promotion_chess[i].getId();
						}
						a.board1[this.row+hs][this.col]=null;
					}
					a.board1[this.row][this.col]=new Pawn(this.isWhite,this.row,this.col);
				}
			}
			catch(Exception e) {};
			for(int i=-1;i<=1;i+=2)
			{
				try {
					if(a.board1[this.row+hs][this.col+hs*i].isWhite()!=this.isWhite)
					{
						
						enemy_piece=a.board1[this.row+hs][this.col+hs*i];
						a.board1[this.row][this.col]=null;
						Rook r=new Rook(this.isWhite,this.row+hs,this.col+hs*i);
						Knight k=new Knight(this.isWhite,this.row+hs,this.col+hs*i);
						Bishop b=new Bishop(this.isWhite,this.row+hs,this.col+hs*i);
						Queen q=new Queen(this.isWhite,this.row+hs,this.col+hs*i);
						Piece[] promotion_chess= {r,k,b,q};
						for(int j=0;j<4;j++)
						{
							a.board1[this.row+hs][this.col+hs*i]=promotion_chess[j];
							if(a.is_kingsafe(this.isWhite))
							{
								list+=""+this.row+this.col+(this.col+hs*i)+enemy_piece.getId()+promotion_chess[j].getId();
							}
							a.board1[this.row+hs][this.col+hs*i]=enemy_piece;
						}
						a.board1[this.row][this.col]=new Pawn(this.isWhite,this.row,this.col);
					}
				}
				catch(Exception e) {};
			}
		}
		return list;
	}
	public int rating_positional()
	{
		if(this.isWhite==true)
		return this.Positional_board[this.row][this.col];
		else
		return this.Positional_board[7-this.row][7-this.col];
	}
	public int rating_attacked(BoardV2 a)
	{
		int temp=0;
		int kqua=0;
		if(this.isWhite==true) temp=a.getKing_location_w();
		else temp=a.getKing_location_b();
		int my_lct=this.row*8+this.col;
		a.setKing_location(my_lct, this.isWhite);
		if(!a.is_kingsafe(this.isWhite)) kqua=-64;
		a.setKing_location(temp, this.isWhite);
		return kqua/2;
	}
}

