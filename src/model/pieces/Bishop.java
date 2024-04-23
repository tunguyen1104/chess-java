package model.pieces;

import model.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {

    public Bishop(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "Bishop";
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    @Override
    public boolean moveCollidesWithPiece(Board board, int col,int row) {
        //up left
        if(this.col > col && this.row > row) {
            for(int i = 1;i < Math.abs(this.col - col); ++i) {
                if(board.getPiece(this.col - i,this.row - i) != null) return true;
            }
        }
        //up right
        if(this.col < col && this.row > row) {
            for(int i = 1;i < Math.abs(this.col - col); ++i) {
                if(board.getPiece(this.col + i,this.row - i) != null) return true;
            }
        }
        //down left
        if(this.col > col && this.row < row) {
            for(int i = 1;i < Math.abs(this.col - col); ++i) {
                if(board.getPiece(this.col - i,this.row + i) != null) return true;
            }
        }
        //down right
        if(this.col < col && this.row < row) {
            for(int i = 1;i < Math.abs(this.col - col); ++i) {
                if(board.getPiece(this.col + i,this.row + i) != null) return true;
            }
        }
        return false;
    }
}
