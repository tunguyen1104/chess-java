package model.pieces;

import model.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Pawn";
        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        int colorIndex = isWhite ? 1 : -1;
        //push pawn 1
        if(this.col == col && this.row - colorIndex == row && board.getPiece(col,row) == null) return true;
        //push pawn 2
        if(the_pawn_first_move && this.col == col && this.row - colorIndex * 2 == row && board.getPiece(col,row) == null && board.getPiece(col,row + colorIndex) == null)
            return true;
        //capture left
        if(this.col - 1 == col && this.row - colorIndex == row && board.getPiece(col,row) != null)
            return true;
        //capture right
        if(this.col + 1 == col && this.row - colorIndex == row && board.getPiece(col,row) != null)
            return true;
        //en passant left
        if(board.getTileNum(col,row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col,row + colorIndex) != null) {
            return true;
        }
        //en passant right
        if(board.getTileNum(col,row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col,row + colorIndex) != null) {
            return true;
        }
        return false;
    }
}
