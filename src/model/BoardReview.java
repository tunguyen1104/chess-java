package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import model.pieces.*;
import controller.ListenerReview;
import view.Review;

public class BoardReview extends JPanel{
    private Review review;
    private ListenerReview input;
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    private ArrayList<Piece> rotateList = new ArrayList<Piece>();
    private List<String> move = new ArrayList<>();
    private Map<Character, Integer> columnMap = new HashMap<>();
    private boolean rotating = false;
    private int index = -1;
    private String column = "abcdefgh";
    private String column_rotate = "hgfedcba";
    private ArrayList<SnapShot> mSnapShots = new ArrayList<>();
    private ArrayList<Piece> piece_list_default = new ArrayList<>();
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    private int col_checkmate = -1;
    private int row_checkmate = -1;
    public BoardReview(Review review, String pgn) {
        this.review = review;
        input = new ListenerReview(review, this);
        ReadImage.sound.playMusic(0);
        for (char column = 'a'; column <= 'h'; ++column) {
            columnMap.put(column, column - 'a');
        }
        addPiece();
        handlePgn(pgn);
        saveSnapShot();
    }
    public void handlePgn(String pgn) {
        try (BufferedReader reader = new BufferedReader(new StringReader(pgn))) {
            String line;
            int cnt = 0;
            while ((line = reader.readLine()) != null) {
                cnt++;
                if (cnt <= 5) continue;
                String move2 = line.trim().substring(7);
                move.add(line.trim().substring(0, 7));
                if (move2.contains("_")) move.add(move2);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void rotateBoard(boolean request) {
        if(request) rotating = !rotating;
        rotateList.clear();
        for (Piece piece : pieceList) {
            if (piece.name.equals("Pawn")) {
                Piece pawn = new Pawn(7 - piece.col, 7 - piece.row, piece.isWhite);
                pawn.isFirstMove = piece.isFirstMove;
                rotateList.add(pawn);
            } else if (piece.name.equals("King")) {
                rotateList.add(new King(7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Queen")) {
                rotateList.add(new Queen(7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Rook")) {
                rotateList.add(new Rook(7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Bishop")) {
                rotateList.add(new Bishop(7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Knight")) {
                rotateList.add(new Knight(7 - piece.col, 7 - piece.row, piece.isWhite));
            }
        }
        pieceList.clear();
        pieceList.addAll(rotateList);
        if (old_col != -1) {
            old_col = 7 - old_col;
            old_row = 7 - old_row;
            new_col = 7 - new_col;
            new_row = 7 - new_row;
        }
        if (col_checkmate != -1) {
            col_checkmate = 7 - col_checkmate;
            row_checkmate = 7 - row_checkmate;
        }
        if(!rotating) {
            review.getWhite_name().setBounds(1000, 590, 490, 120);
            review.getBlack_name().setBounds(1000, 140, 490, 120);
        } else {
            review.getBlack_name().setBounds(1000, 590, 490, 120);
            review.getWhite_name().setBounds(1000, 140, 490, 120);
        }
        review.setBoard_index((rotating) ? ReadImage.board_index_black : null);
        if(request) ReadImage.sound.playMusic(4);
        repaint();
    }
    public void handle_first_button() {
        if(index == -1) return;
        index = -1;
        paintDefault();
    }
    public void handle_last_button() {
        if(index == move.size() - 1) return;
        index = move.size() - 1;
        loadSnapShot(index);
    }
    public void handle_next_button() {
        if(index == move.size() - 1) return;
        if(index < move.size() - 1) {
            ++index;
        }
        loadSnapShot(index);
    }
    public void handle_previous_button() {
        if(index == -1) return;
        if(index > -1) --index;
        if(index == -1) {
            paintDefault();
        } else loadSnapShot(index);
    }
    public void paintDefault() {
        old_col = -1;
        old_row = -1;
        new_col = -1;
        new_row = -1;
        col_checkmate = -1;
        row_checkmate = -1;
        review.getTextArea().setText("");
        pieceList.clear();
        pieceList.addAll(piece_list_default);
        if(rotating) rotateBoard(false);
        ReadImage.sound.playMusic(2);
        repaint();
    }
    public Piece getPiece(ArrayList<Piece> piecesBefore, int col, int row) {
        for (Piece piece : piecesBefore) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }
    public SnapShot getSnapShot(int index) {
        if (index < 0 || index >= mSnapShots.size()) {
            System.out.println("GameSaver - Index out of range");
        }
        return mSnapShots.get(index);
    }
    public void loadSnapShot(int index) {
        old_col = -1;
        old_row = -1;
        new_col = -1;
        new_row = -1;
        col_checkmate = -1;
        row_checkmate = -1;
        SnapShot x = getSnapShot(index);
        pieceList.clear();
        pieceList.addAll(x.getPieces());
        review.getTextArea().setText(x.getPgn());
        if(x.getSound() != 4) {
            old_col = Character.getNumericValue(x.getMove().charAt(0));
            old_row = Character.getNumericValue(x.getMove().charAt(1));
            new_col = Character.getNumericValue(x.getMove().charAt(2));
            new_row = Character.getNumericValue(x.getMove().charAt(3));
        } else {
            if(x.getMove().contains("O-O-O")) {
                if(index % 2 == 0) {
                    old_col = 4;
                    old_row = 7;
                    new_col = 2;
                    new_row = 7;
                } else {
                    old_col = 4;
                    old_row = 0;
                    new_col = 2;
                    new_row = 0;
                }
            } else if(x.getMove().contains("O-O___")) {
                if(index % 2 == 0) {
                    old_col = 4;
                    old_row = 7;
                    new_col = 6;
                    new_row = 7;
                } else {
                    old_col = 4;
                    old_row = 0;
                    new_col = 6;
                    new_row = 0;
                }
            }
        }
        if(x.getMove().charAt(6) == '#') {
            boolean isWhite = (index % 2 != 0) ? true : false;
            for(Piece piece : x.getPieces()) {
                if(piece.name.equals("King") && piece.isWhite == isWhite) {
                    col_checkmate = piece.col;
                    row_checkmate = piece.row;
                    break;
                }
            }
        }
        ReadImage.sound.playMusic(x.getSound());
        if(rotating) rotateBoard(false);
        repaint();
    }
    public ArrayList<Piece> handle_snapshot(String x, boolean isWhite, ArrayList<Piece> pieceBefore) {
        if(x.length() != 7) return null;
        ArrayList<Piece> current_piece = new ArrayList<>();
        for(Piece piece : pieceBefore) {
            if(piece.name.equals("Pawn")) {
                current_piece.add(new Pawn(piece.col, piece.row, piece.isWhite));
            } else if(piece.name.equals("Rook")) {
                current_piece.add(new Rook(piece.col, piece.row, piece.isWhite));
            } else if(piece.name.equals("Knight")) {
                current_piece.add(new Knight(piece.col, piece.row, piece.isWhite));
            } else if(piece.name.equals("Queen")) {
                current_piece.add(new Queen(piece.col, piece.row, piece.isWhite));
            } else if(piece.name.equals("King")) {
                current_piece.add(new King(piece.col, piece.row, piece.isWhite));
            } else if(piece.name.equals("Bishop")) {
                current_piece.add(new Bishop(piece.col, piece.row, piece.isWhite));
            }
        }
        if(x.contains("O-O-O")) {
                for(Piece piece: current_piece) {
                    if(piece.name.equals("King") && piece.isWhite == isWhite) {
                        if(isWhite) {
                            piece.col = 2;piece.row = 7;
                        } else {
                            piece.col = 2;piece.row = 0;
                        }
                        piece.xPos = piece.col * 80;
                        piece.yPos = piece.row * 80;
                    } else if(piece.name.equals("Rook") && piece.isWhite == isWhite && piece.col == 0) {
                        if(isWhite) {
                            piece.col = 3;piece.row = 7;
                        } else {
                            piece.col = 3;piece.row = 0;
                        }
                        piece.xPos = piece.col * 80;
                        piece.yPos = piece.row * 80;
                    }
                }

        } else if(x.contains("O-O___")) {
            for(Piece piece: current_piece) {
                if(piece.name.equals("King") && piece.isWhite == isWhite) {
                    if(isWhite) {
                        piece.col = 6;piece.row = 7;
                    } else {
                        piece.col = 6;piece.row = 0;
                    }
                    piece.xPos = piece.col * 80;
                    piece.yPos = piece.row * 80;

                } else if(piece.name.equals("Rook") && piece.isWhite == isWhite && piece.col == 7) {
                    if(isWhite) {
                        piece.col = 5;piece.row = 7;
                    } else {
                        piece.col = 5;piece.row = 0;
                    }
                    piece.xPos = piece.col * 80;
                    piece.yPos = piece.row * 80;
                }
            }
        } else {
            int oldCol = Character.getNumericValue(x.charAt(0));
            int oldRow = Character.getNumericValue(x.charAt(1));
            int newCol = Character.getNumericValue(x.charAt(2));
            int newRow = Character.getNumericValue(x.charAt(3));
            for(Piece piece: current_piece) {
                if (piece.col == oldCol && piece.row == oldRow) {
                    if(x.charAt(4) == '_') {
                        if(x.charAt(5) != '_') {
                            Piece k = getPiece(current_piece, oldCol, oldRow);
                            assert k != null;
                            current_piece.remove(k);
                            switch (x.charAt(5)) {
                                case 'Q':
                                    current_piece.add(new Queen(newCol,newRow,isWhite));
                                    break;
                                case 'R':
                                    current_piece.add(new Rook(newCol,newRow,isWhite));
                                    break;
                                case 'B':
                                    current_piece.add(new Bishop(newCol,newRow,isWhite));
                                    break;
                                case 'N':
                                    current_piece.add(new Knight(newCol,newRow,isWhite));
                                    break;
                            }
                        } else piece.col = newCol;piece.row = newRow;
                    } else {
                        Piece k = getPiece(current_piece, newCol, newRow);
                        assert k != null;
                        current_piece.remove(k);
                        if(x.charAt(5) != '_') {
                            Piece n = getPiece(current_piece, oldCol, oldRow);
                            assert n != null;
                            current_piece.remove(n);
                            switch (x.charAt(5)) {
                                case 'Q':
                                    current_piece.add(new Queen(newCol,newRow,isWhite));
                                    break;
                                case 'R':
                                    current_piece.add(new Rook(newCol,newRow,isWhite));
                                    break;
                                case 'B':
                                    current_piece.add(new Bishop(newCol,newRow,isWhite));
                                    break;
                                case 'N':
                                    current_piece.add(new Knight(newCol,newRow,isWhite));
                                    break;
                            }
                        } else piece.col = newCol;piece.row = newRow;
                    }
                    piece.xPos = piece.col * 80;
                    piece.yPos = piece.row * 80;
                    break;
                }
            }
        }
        return current_piece;
    }
    //0536qQ_
    //O-O-O__
    //O-O____
    public String handleStep(String x, ArrayList<Piece> piecesBefore) {
        if(x.length() != 7) return "";
        String step = "";
        if(x.contains("O-O-O")) {
            step = "O-O-O";
            if(x.charAt(x.length() - 1) == '+') step += '+';
        } else if(x.contains("O-O___")){
            step = "O-O";
            if(x.charAt(x.length() - 1) == '+') step += '+';
        }
        if(step.length() > 0) return step;
        int oldCol = Character.getNumericValue(x.charAt(0));
        int oldRow = Character.getNumericValue(x.charAt(1));
        int newCol = Character.getNumericValue(x.charAt(2));
        int newRow = Character.getNumericValue(x.charAt(3));
        Piece check = getPiece(piecesBefore, oldCol, oldRow);
        assert check != null;
        if(check != null) {
            if (check.name.equals("Pawn")) {
                if (!rotating)
                    step += column.charAt(oldCol);
                else
                    step += column_rotate.charAt(oldCol);
            } else if (check.name.equals("Knight")) {
                step += 'N';
            } else {
                step += check.name.charAt(0);
            }
        }
        if (x.charAt(4) != '_') {
            step += 'x';
        } else {
            if (Character.isLowerCase(step.charAt(0))) {
                step = "";
            }
        }
        if (!rotating) {
            step += column.charAt(newCol);
            step += String.valueOf(8 - newRow);
        } else {
            step += column_rotate.charAt(newCol);
            step += String.valueOf(newRow + 1);
        }
        if(x.charAt(5) != '_') {
            step += "=" + x.charAt(5);
        }
        if(x.charAt(x.length() - 1) != '_') {
            if(x.charAt(x.length() - 1) == '+') step += '+';
            else step += '#';
        }
        return step;
    }
    public int handleSound(String x) {
        if(x.length() != 7) return -1;
        if(x.contains("O-O-O")) {
            return 4;
        } else if(x.contains("O-O___")){
            return 4;
        } else if(x.charAt(6) == '#') {
            return 1;
        } else if(x.charAt(5) != '_') {
            return 5;
        } else if(x.charAt(4) != '_'){
            return 3;
        }
        return 2;
    }
    public void saveSnapShot() {
        String notion = String.format("%3s %8s %6s", 1 + ".", handleStep(move.get(0), pieceList), " ");
        mSnapShots.add(new SnapShot(handle_snapshot(move.get(0),true,pieceList),
            move.get(0),2,notion));
        int count_step = 2;
        for(int i = 1;i < move.size(); ++i) {
            if (i % 2 != 0) {
                notion += handleStep(move.get(i), mSnapShots.get(i - 1).getPieces()) + "\n";
            } else {
                notion += String.format("%3s %8s %6s", count_step + ".", handleStep(move.get(i), mSnapShots.get(i - 1).getPieces()), " ");
                ++count_step;
            }
            mSnapShots.add(new SnapShot(handle_snapshot(move.get(i), i % 2 == 0 ? true : false, mSnapShots.get(i - 1).getPieces()),
            move.get(i),i != move.size() - 1 ? handleSound(move.get(i)) : 1,notion));
        }
    }
    public void cnmdmvndn(ArrayList<Piece> gg) {
        for(int i = 0;i < 8; ++i) {
            for(int j = 0;j < 8; ++j) {
                Piece kl = getPiece(gg,j,i);
                if(kl == null) System.out.print(" ");
                else System.out.print(kl.name.charAt(0));
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }
    public String getName(Character x) {
        switch (x) {
            case 'R':
                return "Rook";
            case 'N':
                return "Knight";
            case 'B':
                return "Bishop";
            case 'Q':
                return "Queen";
            case 'K':
                return "King";
        }
        return "";
    }
    public void addPiece() {
        pieceList.add(new Rook(0, 0, false));
        pieceList.add(new Knight(1, 0, false));
        pieceList.add(new Bishop(2, 0, false));
        pieceList.add(new Queen(3, 0, false));
        pieceList.add(new King(4, 0, false));
        pieceList.add(new Bishop(5, 0, false));
        pieceList.add(new Knight(6, 0, false));
        pieceList.add(new Rook(7, 0, false));
        
        for(int i = 0;i <= 7; ++i) {
            pieceList.add(new Pawn(i, 1, false));
            pieceList.add(new Pawn(i, 6, true));
        }

        pieceList.add(new Rook(0, 7, true));
        pieceList.add(new Knight(1, 7, true));
        pieceList.add(new Bishop(2, 7, true));
        pieceList.add(new Queen(3, 7, true));
        pieceList.add(new King(4, 7, true));
        pieceList.add(new Bishop(5, 7, true));
        pieceList.add(new Knight(6, 7, true));
        pieceList.add(new Rook(7, 7, true));
        piece_list_default.addAll(pieceList);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(ReadImage.board_image, 0,0,640,640, this);
        if(col_checkmate != -1) {
            g2d.setColor(new Color(214,114,114,190));
            g2d.fillRect(col_checkmate * 80, row_checkmate * 80, 80, 80);
        }
        if (old_col != -1) {
            g2d.setColor(new Color(0, 150, 150, 100));
            g2d.fillRect(old_col * 80, old_row * 80, 80, 80);
            g2d.fillRect(new_col * 80, new_row * 80, 80, 80);
        }
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
