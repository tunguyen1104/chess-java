package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.*;
import model.pieces.Piece;
import view.GameOptions;
import view.GamePVP;
import view.Menu;

import javax.swing.*;

public class Listener extends MouseAdapter {
    private GamePVP game;
    private Board board;
    public Sound sound = new Sound();
    private boolean isTurn = true;// mặc định quân trắng đi trước
    public boolean isEnd = false;// end because King die
    public Integer check_delete_or_promotion = -1;
    public Listener(Board board,GamePVP game) {
        this.board = board;
        this.game = game;
    }
    public void change_check_delete_or_promotion(int x) {
        this.check_delete_or_promotion = x;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(isEnd) return;
        if ("00:00".equals(game.timeLabelWhite.getText())) {
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("White","Time out");
        } else if ("00:00".equals(game.timeLabelBlack.getText())) {
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("Black", "Time out");
        }
        if(board.selectedPiece != null) {
            if(isTurn != board.selectedPiece.isWhite) {
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
        if(isEnd) return;
        if(board.selectedPiece != null) {
            if(isTurn != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
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
                if(isTurn == true) {
                    game.start_white();
                    game.stop_black();
                    isTurn = false;
                }
                else{
                    game.start_black();
                    game.stop_white();
                    isTurn = true;
                }
                board.paint_old_new(move.getOldCol(),move.getOldRow(),move.getNewCol(),move.getNewRow());
                if(this.check_delete_or_promotion == -1) sound.playMusic(2);
                else if(this.check_delete_or_promotion == 1000) {

                }
                else if(this.check_delete_or_promotion == 2000) {

                }
            }
            else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
        if (board.findKing(true) == null) {
            isEnd = true;
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("Black","King die");
        }
        else if (board.findKing(false) == null) {
            isEnd = true;
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("White","King die");
        }
    }
    public void noti_end_game(String name_win, String reason) {
        Object[] options = { "New Game", "Home", "Review"};
        int select = JOptionPane.showOptionDialog(null,name_win + " Win (" + reason + " )","Notification",JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch(select) {
            case 0:
                game.frame.dispose();
                new GameOptions();
                break;
            case 1:
                game.frame.dispose();
                new Menu();
                break;
            case 2:
                game.frame.dispose();
                break;
        }
    }
}

