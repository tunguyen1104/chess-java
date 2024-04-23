package model.pieces;

import model.Board;
import model.Move;

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
    public boolean check_the_valid_moves_of_the_chess_pieces(Board board, int col, int row) {
        return ( Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || can_castling(board, col,row) ) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    private boolean can_castling(Board board, int col,int row) {
        if(row == this.row) {
            if(col == 6) {
                Piece rook = board.getPiece(7, row);
                if(rook != null && rook.isFirstMove && this.isFirstMove) {
                    return board.getPiece(5, row) == null && board.getPiece(6, row) == null && !board.isKingChecked(new Move(board,this,col,row));
                }
            } else if(col == 2) {
                Piece rook = board.getPiece(0, row);
                if(rook != null && rook.isFirstMove && this.isFirstMove) {
                    return board.getPiece(1, row) == null && board.getPiece(2, row) == null && board.getPiece(3, row) == null && !board.isKingChecked(new Move(board,this,col,row));
                }
            }
        }
        return false;
    }
}
