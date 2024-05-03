package model;

import java.util.Scanner;

import model.piecesv2.*;
public class BoardV2 {
	static public Piece[][] board1;
	private int king_location_w ;
	private int king_location_b ;
	private String kqua_MNX;
	private int soTH=0;
	private int soTHTT=0;
	private String moved_rook_king_b="",moved_rook_king_w="";
	public BoardV2()
	{
		board1=new Piece[8][8];
	}
	public void setKing(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]= new King(true,r,c);
			this.king_location_w=r*8+c;
		}
		else
		{
			this.board1[r][c]=new King(false,r,c);
			this.king_location_b=r*8+c;
		}
	}
	public void setQueen(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]= new Queen(true,r,c);
		}
		else
		{
			this.board1[r][c]=new Queen(false,r,c);
		}
	}
	public void setRook(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]=new Rook(true,r,c);
		}
		else
		{
			this.board1[r][c]=new Rook(false,r,c);
		}
	}
	public void setBishop(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]=new Bishop(true,r,c);
		}
		else
		{
			this.board1[r][c]=new Bishop(false,r,c);
		}
	}
	public void setKnight(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]=new Knight(true,r,c);
		}
		else
		{
			this.board1[r][c]=new Knight(false,r,c);
		}
	}
	
	public void setPawn(boolean iswhite,int r,int c)
	{
		if(iswhite==true)
		{
			this.board1[r][c]=new Pawn(true,r,c);
		}
		else
		{
			this.board1[r][c]=new Pawn(false,r,c);
		}
	}
	public void create_game()
	{
		this.setRook(false, 0, 0);
		this.setRook(false, 0, 7);
		this.setRook(true, 7, 0);
		this.setRook(true, 7, 7);
		this.setKnight(false, 0,1);
		this.setKnight(false, 0,6);
		this.setKnight(true, 7,1);
		this.setKnight(true, 7,6);
		this.setBishop(false, 0, 2);
		this.setBishop(false, 0, 5);
		this.setBishop(true, 7, 2);
		this.setBishop(true, 7, 5);
		this.setKing(false, 0, 4);
		this.setKing(true, 7, 4);
		this.setQueen(false,0, 3);
		this.setQueen(true,7, 3);
		for(int i=0;i<8;i++)
		{
			this.setPawn(false, 1, i);
			this.setPawn(true, 6, i);
		}
	}
	public String getMoved_rook_king_b() {
		return moved_rook_king_b;
	}
	public String getMoved_rook_king_w() {
		return moved_rook_king_w;
	}
	public int getKing_location_w() {
		return king_location_w;
	}
	public int getKing_location_b() {
		return king_location_b;
	}
	public void setKing_location(int king_location,boolean iswhite) {
		if(iswhite==true) {
			this.king_location_w=king_location;
		}
		else
		{
			this.king_location_b=king_location;
		}
	}
	public boolean is_kingsafe(boolean iswhite)
	{
		int r=0,c=0,hs=0,my_king_lct=0,e_king_lct=0;
		if(iswhite==true)
		{
			r=this.king_location_w/8;
			c=this.king_location_w%8;
			hs=-1;
			my_king_lct=this.king_location_w;
			e_king_lct=this.king_location_b;
		}
		else {
			r=this.king_location_b/8;
			c=this.king_location_b%8;
			hs=1;
			my_king_lct=this.king_location_b;
			e_king_lct=this.king_location_w;
		}
		//check rook , queen
		int[] rook_queen_row_check = {-1,0,1,0};
		int[] rook_queen_col_check = {0,1,0,-1};
		for(int i=0;i<4;i++)
		{
			int cnt=1;
			try {
				while(this.board1[r+rook_queen_row_check[i]*cnt][c+rook_queen_col_check[i]*cnt]==null)
				{
					cnt++;
				}
				if(this.board1[r+rook_queen_row_check[i]*cnt][c+rook_queen_col_check[i]*cnt].isWhite()!=iswhite&&(this.board1[r+rook_queen_row_check[i]*cnt][c+rook_queen_col_check[i]*cnt].getId()=="r"||this.board1[r+rook_queen_row_check[i]*cnt][c+rook_queen_col_check[i]*cnt].getId()=="q"))
				{
					return false;
				}
			}
			catch(Exception e) {};
		}
		//check bishop , queen 
		int[] bishop_queen_row_check= {-1,1,1,-1};
		int[] bishop_queen_col_check= {1,1,-1,-1};
		for(int i=0;i<4;i++)
		{
			int cnt=1;
			try {
				while(this.board1[r+bishop_queen_row_check[i]*cnt][c+bishop_queen_col_check[i]*cnt]==null)
				{
					cnt++;
				}
				if(this.board1[r+bishop_queen_row_check[i]*cnt][c+bishop_queen_col_check[i]*cnt].isWhite()!=iswhite&&(this.board1[r+bishop_queen_row_check[i]*cnt][c+bishop_queen_col_check[i]*cnt].getId()=="b"||this.board1[r+bishop_queen_row_check[i]*cnt][c+bishop_queen_col_check[i]*cnt].getId()=="q"))
				{
					return false;
				}
			}
			catch(Exception e) {};
		}
		//check knight
		int[] knight_row_check= {-2,-2,-1,1,2,2,1,-1};
		int[] knight_col_check= {-1,1,2,2,1,-1,-2,-2};
		for(int i=0;i<8;i++)
		{
			try {
				if(this.board1[r+knight_row_check[i]][c+knight_col_check[i]].isWhite()!=iswhite&&this.board1[r+knight_row_check[i]][c+knight_col_check[i]].getId()=="k")
				{
					return false;
				}
			}
			catch(Exception e) {};
		}
		//check pawn
		for(int i=-1;i<=1;i+=2)
		{
			try
			{
				if(this.board1[r+hs][c+i].isWhite()!=iswhite&&this.board1[r+hs][c+i].getId()=="p")
				{
					return false;
				}
			}
			catch(Exception e) {};
		}
		//check king
		for(int i=0;i<9;i++)
		{
			if(my_king_lct-9+(i/3)*8+(i%3)==e_king_lct)
			{
				return false;
			}
		}
		return true;
	}
	public String possible_move(boolean iswhite)
	{
		String list="";
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(this.board1[i][j]!=null&&this.board1[i][j].isWhite()==iswhite)
				{
					list +=""+this.board1[i][j].move(this);
				}
			}
		}
		return list;
	}
	public void show_console_board()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(this.board1[i][j]==null)
				{
					System.out.printf("%-6s", 'X');
				}
				else {
					if(this.board1[i][j].isWhite()==true)
					{
						System.out.print('W');
					}
					else System.out.print('B');
					switch(this.board1[i][j].getId()) {
					case  "r":
						System.out.printf("%-5s",'R');
					break;
					case  "b":
						System.out.printf("%-5s",'B');
					break;
					case  "k":
						System.out.printf("%-5s",'K');
					break;
					case  "q":
						System.out.printf("%-5s",'Q');
					break;
					case  "a":
						System.out.printf("%-5s",'A');
					break;
					case  "p":
						System.out.printf("%-5s",'P');
					break;
					}
				}
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	public void make_move(String code)
	{
		//make castling move
		//eg *0_qs
		if((char)code.charAt(0)=='*')
		{
			if(code.substring(3).equals("qs"))
			{
				this.make_move((char)code.charAt(1)+"4"+(char)code.charAt(1)+"2 ");
				this.make_move((char)code.charAt(1)+"0"+(char)code.charAt(1)+"3 ");
			}
			if(code.substring(3).equals("ks"))
			{
				this.make_move((char)code.charAt(1)+"4"+(char)code.charAt(1)+"6 ");
				this.make_move((char)code.charAt(1)+"7"+(char)code.charAt(1)+"5 ");
			}
		}
		else
		{
			//e.g : 1234b meanings from [1][2] to [3][4] and captures b(bishop)
			// make a king's move so must change king_location_w or king_location_b
			if(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].getId()=="a")
			{
				int lct=Character.getNumericValue(code.charAt(2))*8+Character.getNumericValue(code.charAt(3));
				if(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite()==true)
				{
					this.king_location_w=lct;
				}
				else this.king_location_b=lct;
			}
			//make a normal move 
			if(Character.getNumericValue(code.charAt(3))<=7&&Character.getNumericValue(code.charAt(3))!=-1)
			{
				//check if King and Rook has ever moved
				if((code.substring(0,2).equals("00")&&this.board1[0][0].getId()=="r")||(code.substring(0, 2).equals("07")&&this.board1[0][7].getId()=="r"))
				{
					this.moved_rook_king_b+=code;
				}
				if(code.substring(0,2).equals("04")&&this.board1[0][4].getId()=="a")
				{
					this.moved_rook_king_b+=code;
				}
				if((code.substring(0,2).equals("70")&&this.board1[7][0].getId()=="r")||(code.substring(0, 2).equals("77")&&this.board1[7][7].getId()=="r"))
				{
					this.moved_rook_king_w+=code;
				}
				if(code.substring(0,2).equals("74")&&this.board1[7][4].getId()=="a")
				{
					this.moved_rook_king_w+=code;
				}
				this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))]=this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))];
				this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].setRow(Character.getNumericValue(code.charAt(2)));
				this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].setCol(Character.getNumericValue(code.charAt(3)));
				this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))]=null;			
			}
			//make a promotion move
			else
			{
				int hs=0;
				if(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite()==true)
				{
					hs=-1;
				}
				else hs=1;
				switch (code.charAt(4)){
				case 'r' :
					this.setRook(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(0))+hs, 
							Character.getNumericValue(code.charAt(2)));
					break;
				case 'k' :
					this.setKnight(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(0))+hs, 
							Character.getNumericValue(code.charAt(2)));
					break;
				case 'b' :
					this.setBishop(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(0))+hs, 
							Character.getNumericValue(code.charAt(2)));
					break;
				case 'q' :
					this.setQueen(this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(0))+hs, 
							Character.getNumericValue(code.charAt(2)));
					break;
				}
				this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))]=null;
			}
		}
	}
	public void undo_move(String code)
	{
		//undo castling move
		//eg *7_ks
		if((char)code.charAt(0)=='*')
		{
			if(code.substring(3).equals("qs"))
			{
				this.undo_move((char)code.charAt(1)+"4"+(char)code.charAt(1)+"2 ");
				this.undo_move((char)code.charAt(1)+"0"+(char)code.charAt(1)+"3 ");
			}
			if(code.substring(3).equals("ks"))
			{
				this.undo_move((char)code.charAt(1)+"4"+(char)code.charAt(1)+"6 ");
				this.undo_move((char)code.charAt(1)+"7"+(char)code.charAt(1)+"5 ");
			}
		}
		//undo normal move
		//e.g 4563k
		//change location of king
		//a normal move
		else
		{
			if(Character.getNumericValue(code.charAt(3))<=7&&Character.getNumericValue(code.charAt(3))!=-1)
			{
				if(this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].getId()=="a")
				{
					int lct=Character.getNumericValue(code.charAt(0))*8+Character.getNumericValue(code.charAt(1));
					if(this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].isWhite()==true)
					{
						this.king_location_w=lct;
					}
					else this.king_location_b=lct;
				}
			if(this.moved_rook_king_b.contains(code))
				{
					String temp=this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].getId();
					if(temp=="a"||temp=="r")
					{
						this.moved_rook_king_b=this.moved_rook_king_b.replaceFirst(code, "");
					}
				}
				if(this.moved_rook_king_w.contains(code))
				{
					String temp=this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))].getId();
					if(temp=="a"||temp=="r")
					{
						this.moved_rook_king_w=this.moved_rook_king_w.replaceFirst(code, "");
					}
				}
				this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))]=this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))];
				this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].setRow(Character.getNumericValue(code.charAt(0)));
				this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].setCol(Character.getNumericValue(code.charAt(1)));
				switch(code.charAt(4))
				{
				case 'r' :
					this.setRook(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(2)), 
							Character.getNumericValue(code.charAt(3)));
					break;
				case 'k' :
					this.setKnight(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(2)), 
							Character.getNumericValue(code.charAt(3)));
					break;
				case 'b' :
					this.setBishop(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(2)), 
							Character.getNumericValue(code.charAt(3)));
					break;
				case 'q' :
					this.setQueen(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(2)), 
							Character.getNumericValue(code.charAt(3)));
					break;
				case 'p' :
					this.setPawn(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
							Character.getNumericValue(code.charAt(2)), 
							Character.getNumericValue(code.charAt(3)));
					break;
				case ' ' :
					this.board1[Character.getNumericValue(code.charAt(2))][Character.getNumericValue(code.charAt(3))]=null;
					break;	
				}
			}
			else
			{
				//undo promotion move
				//e.g : 633bk
				int hs=0;
				if(Character.getNumericValue(code.charAt(0))==6) hs=1;
				else hs=-1;
				if(this.board1[Character.getNumericValue(code.charAt(0))+hs][Character.getNumericValue(code.charAt(2))]!=null)
				{
					this.setPawn(this.board1[Character.getNumericValue(code.charAt(0))+hs][Character.getNumericValue(code.charAt(2))].isWhite(),
							Character.getNumericValue(code.charAt(0)),
							Character.getNumericValue(code.charAt(1)));
					switch(code.charAt(3))
					{
					case 'r' :
						this.setRook(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
								Character.getNumericValue(code.charAt(0))+hs, 
								Character.getNumericValue(code.charAt(2)));
						break;
					case 'k' :
						this.setKnight(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
								Character.getNumericValue(code.charAt(0))+hs, 
								Character.getNumericValue(code.charAt(2)));
						break;
					case 'b' :
						this.setBishop(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
								Character.getNumericValue(code.charAt(0))+hs, 
								Character.getNumericValue(code.charAt(2)));
						break;
					case 'q' :
						this.setQueen(!this.board1[Character.getNumericValue(code.charAt(0))][Character.getNumericValue(code.charAt(1))].isWhite(),
								Character.getNumericValue(code.charAt(0))+hs, 
								Character.getNumericValue(code.charAt(2)));
						break;	
					case ' ' :
						this.board1[Character.getNumericValue(code.charAt(0))+hs][Character.getNumericValue(code.charAt(2))]=null;
						break;	
					}
				}
			}
		}
	}	
	public int rating_material(boolean iswhite)
	{
		int kqua=0;
		int counting_bishop=0;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(this.board1[i][j]!=null)
				{
					if(this.board1[i][j].isWhite()==iswhite)
					{
						switch(this.board1[i][j].getId())
						{
						case "p" :
							kqua +=100;
							break;
						case "r" :
							kqua+=500;
							break;
						case "k" :
							kqua+=300;
							break;
						case "b" :
							counting_bishop+=1;
							break;
						case "q" :
							kqua+=900;
							break;
						}
					}
				}
				}
		}
		if(counting_bishop>=2) kqua+=300*counting_bishop;
		if(counting_bishop==1) kqua+=250;
		return kqua;
	}
	public int rating_moveablitly(int list_length,boolean iswhite,int depth)
	{
		int kqua=0;
		kqua+=list_length;// +5 point/1 valid move
		if(list_length==0) {
			if(!this.is_kingsafe(!iswhite)==true) kqua-=200000*depth;//checkmate
			else kqua-=150000*depth;//stalemate
		}
		return kqua;
	}
	public int rating(boolean iswhite,int depth,int list_length)
	{
		int kqua=0;
		int my_material = this.rating_material(iswhite);
		int enemy_material = this.rating_material(!iswhite);
		kqua+=my_material-enemy_material;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(this.board1[i][j]!=null)
				{
					if(this.board1[i][j].isWhite()==iswhite)
					{
						if(this.board1[i][j].getId()!="a")
							kqua+=this.board1[i][j].rating_positional();
						else
							kqua+=this.board1[i][j].rating_positional(my_material,this);
						kqua+=this.board1[i][j].rating_attacked(this);
					}
					else
					{
						if(this.board1[i][j].getId()!="a")
							kqua-=this.board1[i][j].rating_positional();
						else
							kqua-=this.board1[i][j].rating_positional(enemy_material,this);
						kqua-=this.board1[i][j].rating_attacked(this);
					}
				}
			}
		}
		int enemy_moveabitly=this.possible_move(!iswhite).length();
		kqua+=this.rating_moveablitly(list_length,iswhite,depth);
		kqua-=this.rating_moveablitly(enemy_moveabitly, !iswhite, depth);
		kqua+=depth*50;
		return -kqua;
	}
	public String sort_list_move(String list,boolean iswhite)
	{
		int value[]=new int[list.length()/5];
		for(int i=0;i<list.length();i+=5)
		{
			this.make_move(list.substring(i, i+5));
			value[i/5]=-this.rating(iswhite, 0,-1);
			this.undo_move(list.substring(i, i+5));
		}
		String new_list="";
		String old_list=list;
		for(int i=0;i<Math.min(10,list.length()/5);i++)
		{
			int max=-999999999;
			int lct_max=0;
			for(int j=0;j<list.length()/5;j++)
			{
				if(value[j]>max)
				{
					max=value[j];
					lct_max=j;
				}
			}
			new_list+=list.substring(lct_max*5, lct_max*5+5);
			old_list=old_list.replace(list.substring(lct_max*5, lct_max*5+5),"");
			value[lct_max]=-999999999;
		}	
		return new_list+old_list;
	}
	public String minimax_alpha_beta(int depth,int alpha,int beta,boolean iswhite)
	{
		String list_move=this.possible_move(iswhite);
		if(list_move.length()==0||depth==0)
		{
			int hs=1;
			if(iswhite==true) hs=-1;
				return ""+this.rating(iswhite, depth,list_move.length())*hs; 
		}
		list_move=this.sort_list_move(list_move, iswhite);
		for(int i=0;i<Math.min(50,list_move.length());i+=5)
		{
			this.make_move(list_move.substring(i, i+5));
			String child_node = this.minimax_alpha_beta(depth-1,alpha,beta,!iswhite);
			int value = Integer.valueOf(child_node);
			this.undo_move(list_move.substring(i, i+5));
			if(iswhite==false&&value<beta)
			{
				beta=value;
				if(depth==4)
				{
					this.kqua_MNX=list_move.substring(i, i+5);
				}
			}  
			if(iswhite==true&&value>=alpha)
			{
				alpha=value;
				if(depth==4)
				{
					this.kqua_MNX=list_move.substring(i, i+5);
				}
			}
			if(alpha>=beta)
			{
					if(iswhite==true)
						return ""+alpha;
					else
						return ""+beta;
			}
		}
		if(depth==4)
			return this.kqua_MNX;
		else
		{
			if(iswhite==true)
				return ""+alpha;
			else
				return ""+beta;
		}
	}
	public void test1()
	{
		while(true)
		{
			this.show_console_board();
			Scanner ts = new Scanner(System.in);
			String nuocdi="";
			System.out.println("Nhập nước đi của bạn : ");
			nuocdi=ts.nextLine();
			this.make_move(nuocdi);
			long st_time=System.currentTimeMillis();
			this.make_move(this.minimax_alpha_beta(4, -1000000, 1000000, false));
			long end_time=System.currentTimeMillis();
			System.out.println("It's took "+ (end_time-st_time)+" miliseconds");
		}
	}
	public void test3()
	{
		this.setRook(false, 0, 0);
		this.setRook(false, 0, 7);
		this.setRook(true, 7, 0);
		this.setRook(true, 7, 7);
		this.setKnight(false, 2, 2);
		this.setKnight(false, 2, 5);
		this.setKnight(true, 7, 1);
		this.setKnight(true, 3, 6);
		this.setBishop(false, 0, 2);
		this.setBishop(false, 0, 5);
		this.setBishop(true, 4, 2);
		this.setBishop(true, 7, 2);
		this.setQueen(false, 0, 3);
		this.setQueen(true, 7, 3);
		this.setKing(false, 0, 4);
		this.setKing(true, 7, 4);
		for(int i=0;i<3;i++)
		{
			this.setPawn(false, 1, i);
		}
		for(int i=5;i<8;i++)
		{
			this.setPawn(false, 1, i);
		}
		this.setPawn(false, 3, 3);
		this.setPawn(false, 3, 4);
		for(int i=0;i<4;i++)
		{
			this.setPawn(true, 6, i);
		}
		for(int i=5;i<8;i++)
		{
			this.setPawn(true, 6, i);
		}
		this.setPawn(true, 4, 4);
		/*this.moved_rook_king_b+="04";
		this.moved_rook_king_w+="74";*/
	}
	public void test()//remember to change moved_rook_king(not yet)
	{
		this.setKing(true, 6, 4);
		this.setKing(false, 2, 6);
		//a.setQueen(false, 3, 1);
		this.setQueen(true, 0, 3);
		this.setKnight(true, 5, 5);
		this.setKnight(true, 5, 7);
		//this.setKnight(false, 1, 2);
		this.setPawn(true, 1, 2);
		this.setKnight(true, 3,2 );
		this.setRook(false, 0, 7);
		this.setRook(false, 7, 7);
		this.setRook(true, 5, 3);
		this.setBishop(true, 6, 6);
		this.setBishop(false, 2, 2);
		this.setBishop(false, 7, 2);
		this.setRook(true, 7, 1);
		this.setPawn(false, 5, 6);
		this.setPawn(false, 4, 5);
		this.setPawn(false, 3, 3);
		this.setPawn(true, 1, 0);
		this.setPawn(true, 4, 0);
		this.setPawn(true, 1, 4);
		this.setPawn(false, 6, 0);
		this.setPawn(true, 1, 0);
		this.moved_rook_king_b+="040007";
		this.moved_rook_king_w+="747077";
	}
	public static void main(String[] args) {
		BoardV2 a = new BoardV2();
		a.test();
		a.setKing(false, 7, 0);
		a.setRook(true,7,1);
		a.board1[7][0]=null;
		a.board1[7][1]=null;
		a.show_console_board();
		System.out.println(a.possible_move(false));
		long start = System.currentTimeMillis();
		System.out.println(a.minimax_alpha_beta(4, -1000000, 1000000, false));
		long end= System.currentTimeMillis();
		System.out.println("It's took "+(end-start)+" miliseconds");
	}
}
