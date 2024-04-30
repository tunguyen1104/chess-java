package model;

import java.util.ArrayList;

import model.pieces.Piece;

public class SnapShot {
    private ArrayList<Piece> pieces;
    private String move;
    private int sound;
    private String pgn;
    public SnapShot() {}
    public SnapShot(ArrayList<Piece> pieces, String move, int sound, String pgn) {
        this.pieces = pieces;
        this.move = move;
        this.sound = sound;
        this.pgn = pgn;
    }
    public String getPgn() {
        return pgn;
    }
    public void setPgn(String pgn) {
        this.pgn = pgn;
    }
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public String getMove() {
        return move;
    }
    public void setMove(String move) {
        this.move = move;
    }
    public int getSound() {
        return sound;
    }
}
