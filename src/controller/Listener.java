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
    public Sound sound;
    public boolean isTurn = true;// mặc định quân trắng đi trước
    public boolean isEnd = false;// end because King die
    public boolean check_delete = false;
    public boolean check_promotion = true;
    public Listener(Board board,GamePVP game,Sound sound) {
        this.board = board;
        this.game = game;
        this.sound = sound;
    }
    private int count_step = 1;
    public void change_check_delete(boolean x) {
        this.check_delete = x;
    }
    public void change_check_promotion(boolean x) {
        this.check_promotion = x;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(isEnd) return;
        if ("00:00".equals(game.getTimeLabelWhite().getText())) {
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("White","Time out");
        } else if ("00:00".equals(game.getTimeLabelBlack().getText())) {
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
            board.paint_in_place(col, row);
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
                String step = "";
                Piece check = board.getPiece(move.getOldCol(),move.getOldRow());
                if(check.name.equals("Pawn")) {
                    step += board.column.charAt(move.getNewCol());
                }
                else if(check.name.equals("Knight")) {
                    step += 'N';
                }
                else {
                    step += check.name.charAt(0);
                }
                this.check_delete = false;
                this.check_promotion = false;
                board.makeMove(move);
                if(isTurn == true) {
                    game.start_white();
                    game.stop_black();
                }
                else{
                    game.start_black();
                    game.stop_white();
                }
                isTurn = !isTurn;
                board.paint_old_new(move.getOldCol(),move.getOldRow(),move.getNewCol(),move.getNewRow());
                board.paint_in_place(-1,-1);
                if(this.check_delete) sound.playMusic(3);
                else sound.playMusic(2);
                if(this.check_promotion) sound.playMusic(5);
                String s = game.textArea.getText();
                if(isTurn) s += board.step(step,move) + "\n";
                else {
                    s += String.format("%2s %8s %-12s", count_step, board.step(step,move), " ");
                    ++count_step;
                }
                game.textArea.setText(s);
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
            noti_end_game("Black","Checkmate");
        }
        else if (board.findKing(false) == null) {
            isEnd = true;
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            noti_end_game("White","Checkmate");
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

