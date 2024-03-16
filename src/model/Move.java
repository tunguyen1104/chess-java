package model;

import model.pieces.Piece;

public class Move {
    private int oldCol;
    private int oldRow;
    private int newCol;
    private int newRow;
    Piece capture;//to
    Piece piece;//from
    public Move(Board board, Piece piece, int newCol, int newRow) {
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;
        this.capture = board.getPiece(newCol, newRow);
        this.piece = piece;
    }

    public int getOldCol() {
        return oldCol;
    }

    public int getOldRow() {
        return oldRow;
    }
    public int getNewCol() {
        return newCol;
    }

    public int getNewRow() {
        return newRow;
    }
}
