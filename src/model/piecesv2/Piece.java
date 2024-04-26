package model.piecesv2;

import model.BoardV2;

public class Piece {
	private int row;
	private int col;
	private String id;
	private int lct_in_image_X,lct_in_image_Y;
	private boolean isWhite;
	public int Positional_board[][]= {};
	public Piece()
	{
		this.row=-1;
		this.col=-1;
		this.id="ok";
		this.isWhite = false;
	}
	public Piece(int r, int c)
	{
		this.row=r;
		this.col=c;
		this.id=" ";
		this.isWhite = false;
	}
	public int getLct_in_image_X() {
		return lct_in_image_X;
	}
	public int getLct_in_image_Y() {
		return lct_in_image_Y;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getId() {
		return id;
	}
	public boolean isWhite() {
		return isWhite;
	}
	public String move(BoardV2 a)
	{
		return "ok";
	}
	public int isSafe()
	{
		return 0;
	}
	public int rating_positional()
	{
		return 0;
	}
	public int rating_positional(int a)
	{
		return 0;
	}
	public int rating_attacked(BoardV2 a)
	{
		return 0;
	}
}
