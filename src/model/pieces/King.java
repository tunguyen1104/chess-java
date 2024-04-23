package model.pieces;

import model.Board;

import java.awt.image.BufferedImage;

public class King extends Piece {

    public King(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "King";
        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return ( Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || can_castling(col,row) ) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    private boolean can_castling(int col,int row) {
        return false;
    }
}
