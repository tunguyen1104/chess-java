package model;

import model.pieces.Piece;
//Kiem tra chieu tuong
public class CheckScanner {
    private Board board;
    public CheckScanner(Board board) {
        this.board = board;
    }
    public boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.getNewCol();
            kingRow = move.getNewRow();
        }
        return false;
    }
    private boolean hitByRook(int col, int row,Piece King, int kingCol, int kingRow,int colVal, int rowVal) {
        return true;
    }

}