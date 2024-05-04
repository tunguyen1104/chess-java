package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Board;
import model.Move;
import model.ReadImage;
import model.pieces.Piece;
import view.GamePVP;

public class Listener extends MouseAdapter {
    private GamePVP game;
    private Board board;
    private StringBuilder pgn = new StringBuilder();
    private boolean isTurn = true;// mặc định quân trắng đi trước
    private boolean checkDelete = false;
    private boolean checkPromotion = true;
    private boolean checkMateEndGame = false;
    public Listener(Board board, GamePVP game) {
        this.board = board;
        this.game = game;
    }
    private int count_step = 1;
    @Override
    public void mousePressed(MouseEvent e) {
        if (board.checkEndGame)
            return;
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
        if (board.checkEndGame)
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
                String step = board.step_begin(move);
                this.checkDelete = false;
                this.checkPromotion = false;

                board.makeMove(move);

                game.start_stop_time_label(isTurn);
                isTurn = !isTurn;
                board.paint_old_new(move.getOldCol(), move.getOldRow(), move.getNewCol(), move.getNewRow());
                board.paint_in_place(-1, -1);
                boolean checkMate = board.paintKingCheckMate(isTurn);
                if (!this.checkDelete && !this.checkPromotion)
                    ReadImage.sound.playMusic(2);
                String s = game.textArea.getText();
                String plus = checkMate ? "+" : "";
                board.selectedPiece = null;
                checkMateEndGame = board.checkMateEndGame(isTurn);
                if (checkMateEndGame) plus = "#";
                if (isTurn) {
                    s += board.step_end(step, move) + plus + "\n";
                    pgn.append(board.handle_step_review(move) + "\n");
                } else {
                    s += String.format("%3s %8s %6s", count_step + ".", board.step_end(step, move) + plus, " ");
                    pgn.append(board.handle_step_review(move));
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
        if (checkMateEndGame) {
            if(isTurn && !board.checkEndGame) {
                String text = board.convertDate() +
                        "\nPvP\n" +
                        game.minute +
                        "CheckMate\n" +
                        "Black win\n";
                board.saveFile(new String(text + pgn));
                ReadImage.sound.playMusic(1);
                game.timeLabelWhite.stop();
                game.timeLabelBlack.stop();
                board.addDialogEndGame("Black", "checkmate");
            } else if(!isTurn && !board.checkEndGame){
                String text = board.convertDate() +
                        "\nPvP\n" +
                        game.minute +
                        "\nCheckMate\n" +
                        "White win\n";
                board.saveFile(new String(text + pgn));
                ReadImage.sound.playMusic(1);
                game.timeLabelWhite.stop();
                game.timeLabelBlack.stop();
                board.addDialogEndGame("White", "checkmate");
            }
        }
    }
    public boolean isCheckDelete() {
        return checkDelete;
    }

    public void setCheckDelete(boolean checkDelete) {
        this.checkDelete = checkDelete;
    }
    public boolean isCheckPromotion() {
        return checkPromotion;
    }

    public void setCheckPromotion(boolean checkPromotion) {
        this.checkPromotion = checkPromotion;
    }
    public StringBuilder getPgn() {
        return pgn;
    }
}
