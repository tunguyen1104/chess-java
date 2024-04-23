package model.pieces;

import model.Board;
import java.awt.image.BufferedImage;
public class Rook extends Piece {

    public Rook(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "Rook";
        this.sprite = sheet.getSubimage(4 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return (this.col == col || this.row == row) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    @Override
    public boolean moveCollidesWithPiece(Board board, int col,int row){
        //up
        if(this.row > row) {
            for(int r = this.row - 1;r > row; --r){
                if(board.getPiece(this.col,r) != null) {
                    return true;
                }
            }
        }
        //down
        if(this.row < row){
            for(int r = this.row + 1;r < row; ++r){
                if(board.getPiece(this.col,r) != null) {
                    return true;
                }
            }
        }
        //left
        if(this.col > col) {
            for(int c = this.col - 1; c > col; --c) {
                if(board.getPiece(c,this.row) != null) {
                    return true;
                }
            }
        }
        //right
        if(this.col < col) {
            for(int c = this.col + 1; c < col; ++c) {
                if(board.getPiece(c,this.row) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
