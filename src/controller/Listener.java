package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Board;
import model.Sound;
import model.Move;
import model.pieces.Piece;
import view.GamePVP;

public class Listener extends MouseAdapter {
    private GamePVP game;
    private Board board;
    public Sound sound;
    public boolean isTurn = true;// mặc định quân trắng đi trước
    public boolean isEnd = false;// end because King die
    public boolean check_delete = false;
    public boolean check_promotion = true;

    public Listener(Board board, GamePVP game, Sound sound) {
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
        if (isEnd)
            return;
        if ("00:00".equals(game.getTimeLabelWhite().getText())) {
            sound.playMusic(1);
            game.noti_end_game("Black", "Time out");
        } else if ("00:00".equals(game.getTimeLabelBlack().getText())) {
            sound.playMusic(1);
            game.noti_end_game("White", "Time out");
        }
        if (board.selectedPiece != null) {
            if (isTurn != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            }
        }
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
            if (board.selectedPiece.isWhite == isTurn)
                board.paint_in_place(col, row);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isEnd)
            return;
        if (board.selectedPiece != null) {
            if (isTurn != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
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
                String step = "";
                Piece check = board.getPiece(move.getOldCol(), move.getOldRow());
                if (check.name.equals("Pawn")) {
                    if (!board.rotating)
                        step += board.column.charAt(move.getOldCol());
                    else
                        step += board.column_rotate.charAt(move.getOldCol());
                } else if (check.name.equals("Knight")) {
                    step += 'N';
                } else {
                    step += check.name.charAt(0);
                }
                this.check_delete = false;
                this.check_promotion = false;
                board.makeMove(move);
                if (isTurn == true) {
                    game.start_black();
                    game.stop_white();
                } else {
                    game.start_white();
                    game.stop_black();
                }
                isTurn = !isTurn;
                board.paint_old_new(move.getOldCol(), move.getOldRow(), move.getNewCol(), move.getNewRow());
                board.paint_in_place(-1, -1);
                if (!this.check_delete && !this.check_promotion)
                    sound.playMusic(2);
                String s = game.textArea.getText();
                if (isTurn)
                    s += board.step(step, move) + "\n";
                else {
                    s += String.format("%3s %8s %6s", count_step + ".", board.step(step, move), " ");
                    ++count_step;
                }
                game.textArea.setText(s);
            } else {
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
            game.noti_end_game("Black", "Checkmate");
        } else if (board.findKing(false) == null) {
            isEnd = true;
            sound.playMusic(1);
            game.stop_white();
            game.stop_black();
            game.noti_end_game("White", "Checkmate");
        }
    }

}
