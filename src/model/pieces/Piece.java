package model.pieces;

import model.Board;
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
}
