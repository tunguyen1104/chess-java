package controller;

import model.Board;
import model.Move;
import model.pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListenerPuzzle extends MouseAdapter {
    private Board board;
    public ListenerPuzzle(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (board.done_failed_puzzle != -1)
            return;
        if (board.selectedPiece != null) {
            if (board.color_to_move == board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            }
        }
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
            if (board.selectedPiece.isWhite != board.color_to_move)
                board.paint_in_place(col, row);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.done_failed_puzzle != -1)
            return;
        if (board.selectedPiece != null) {
            if (board.color_to_move == board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            } else {
                board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
                board.selectedPiece.yPos = e.getY() - board.tileSize / 2;
                board.repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
            if (board.isValidMove(move)) {
                board.makeMove(move);
                if (board.index_piecePuzzle < board.piecePuzzle.size()) {
                    board.solvePuzzle(move);
                }
                board.paint_old_new(move.getOldCol(), move.getOldRow(), move.getNewCol(), move.getNewRow());
            } else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
    }
}
