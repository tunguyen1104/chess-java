package model.pieces;

import model.Board;
import model.BoardV2;
import model.ReadImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Piece {
    public int col, row;
    public int xPos;
    public int yPos;
    public boolean isWhite;
    public String name;
    Image sprite;
    public boolean isFirstMove = true;
    protected BufferedImage sheet = ReadImage.piece;
    protected int sheetScale = 100;

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, 80, 80, null);
    }

    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return true;
    }
    public boolean check_the_valid_moves_of_the_chess_pieces(Board board, int col, int row) {
        return true;
    }
    public boolean moveCollidesWithPiece(Board board, int col, int row) {
        return false;
    }

    public int lct_in_image_X,lct_in_image_Y;
    public String id;
    public int count_moved;
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
    public int rating_positional(int a, BoardV2 b)
    {
        return 0;
    }
    public int rating_attacked(BoardV2 a)
    {
        return 0;
    }
}
