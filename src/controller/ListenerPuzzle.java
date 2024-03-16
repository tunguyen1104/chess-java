package controller;

import model.Board;
import model.Move;
import model.Sound;

import model.pieces.Piece;
import view.PuzzleGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListenerPuzzle extends MouseAdapter {
    private PuzzleGame game;
    private Board board;
    public Sound sound;
    public Integer check_delete_or_promotion = -1;
    public ListenerPuzzle(Board board, Sound sound) {
        this.board = board;
        this.sound = sound;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(board.selectedPiece != null) {
            if(board.color_to_move != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            }
        }
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(board.selectedPiece != null) {
            if(board.color_to_move != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            }
            else {
                board.selectedPiece.xPos =  e.getX() - board.tileSize / 2;
                board.selectedPiece.yPos =  e.getY() - board.tileSize / 2;
                board.repaint();
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        if(board.selectedPiece != null){
            Move move = new Move(board,board.selectedPiece,col,row);
            if(board.isValidMove(move)){
                this.check_delete_or_promotion = -1;
                board.makeMove(move);
                board.paint_old_new(move.getOldCol(),move.getOldRow(),move.getNewCol(),move.getNewRow());
                sound.playMusic(2);
            }
            else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
    }
}
