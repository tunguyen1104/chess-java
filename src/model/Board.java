package model;

import controller.Listener;
import controller.ListenerPuzzle;
import model.pieces.*;
import view.GamePVP;
import view.PuzzleGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Board extends JPanel{
    private GamePVP game;
    public int tileSize = 85;
    public int rows = 8;
    public int cols = 8;
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    private ArrayList<String> pieceMove = new ArrayList<String>();
    private ArrayList<Piece> rotateList = new ArrayList<Piece>();
    public boolean rotating = false;
    public Piece selectedPiece;// quân cờ lúc bạn trỏ vào
    public Listener input;
    public ListenerPuzzle inputPuzzle;
    public int enPassantTile = -1;
    public int promotion = -1;
    Sound sound;
    private BufferedImage board_image;
    //paint old new piece
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    private int col_in_place = -1;
    private int row_in_place = -1;
    public ArrayList<String> dataJDBC = JDBCConnection.takeDataSetting();
    //dataJDBC =
    public boolean color_to_move;
    public CheckScanner checkScanner = new CheckScanner(this);
    private PuzzleGame puzzleGame;
    public String column = "abcdefgh";
    public String column_rotate = "hgfedcba";
    private String FEN = "";
    public Board(PuzzleGame puzzleGame, String FEN) {
        if(dataJDBC.get(2) == "1") sound = new Sound();
        else sound = new Sound(1);
        this.puzzleGame = puzzleGame;
        this.FEN = FEN;
        sound.playMusic(0);
        inputPuzzle = new ListenerPuzzle(this,sound);
        try {
            board_image = ImageIO.read(new File(dataJDBC.get(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(inputPuzzle);
        this.addMouseMotionListener(inputPuzzle);
        handleFen(FEN);
        for(String x: pieceMove)
            System.out.println(x);
    }
    public Board (GamePVP game) {
        if(dataJDBC.get(2) == "1") sound = new Sound();
        else sound = new Sound(1);
        this.game = game;
        sound.playMusic(0);
        input = new Listener(this,game,sound);
        try {
            board_image = ImageIO.read(new File(dataJDBC.get(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPiece();
    }
    public Piece getPiece(int col, int row){
        for(Piece piece: pieceList){
            if(piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }
    public Piece findKing(boolean isWhite) {
        for(Piece piece: pieceList) {
            if(piece.isWhite == isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }
    public void rotateBoard() {
        rotating = !rotating;
        rotateList.clear();
        for(Piece piece: pieceList) {
            if(piece.name.equals("Pawn")) {
                Piece pawn = new Pawn(this,7-piece.col,7-piece.row,piece.isWhite);
                pawn.the_pawn_first_move = piece.the_pawn_first_move;
                rotateList.add(pawn);
            }
            else if(piece.name.equals("King")) {
                rotateList.add(new King(this,7-piece.col,7-piece.row,piece.isWhite));
            }
            else if(piece.name.equals("Queen")) {
                rotateList.add(new Queen(this,7-piece.col,7-piece.row,piece.isWhite));
            }
            else if(piece.name.equals("Rook")) {
                rotateList.add(new Rook(this,7-piece.col,7-piece.row,piece.isWhite));
            }
            else if(piece.name.equals("Bishop")) {
                rotateList.add(new Bishop(this,7-piece.col,7-piece.row,piece.isWhite));
            }
            else if(piece.name.equals("Knight")) {
                rotateList.add(new Knight(this,7-piece.col,7-piece.row,piece.isWhite));
            }
        }
        enPassantTile = 63 - enPassantTile;
        pieceList.clear();
        pieceList.addAll(rotateList);
        if(old_col != -1) {
            old_col = 7 - old_col;
            old_row = 7 - old_row;
            new_col = 7 - new_col;
            new_row = 7 - new_row;
        }
        if(col_in_place != -1) {
            col_in_place = 7 - col_in_place;
            row_in_place = 7 - row_in_place;
        }
        if(!rotating) {
            game.getWhite_name().setBounds(1050,140,490,120);
            game.getBlack_name().setBounds(1050,610,490,120);
            game.getTimeLabelWhite().setBounds(1100, 160, 500, 200);
            game.getTimeLabelBlack().setBounds(1100, 505, 500, 200);
        }
        else {
            game.getWhite_name().setBounds(1050,610,490,120);
            game.getBlack_name().setBounds(1050,140,490,120);
            game.getTimeLabelWhite().setBounds(1100, 505, 500, 200);
            game.getTimeLabelBlack().setBounds(1100, 160, 500, 200);
        }
        sound.playMusic(4);
        repaint();
    }
    public void makeMove(Move move) {
        if(move.piece.name.equals("Pawn")) {
            movePawn(move);
        }
        else {
            move.piece.col = move.getNewCol();
            move.piece.row = move.getNewRow();
            move.piece.xPos = move.getNewCol() * tileSize;
            move.piece.yPos = move.getNewRow() * tileSize;
            if (move.capture != null) {
                sound.playMusic(3);
                delete_piece(move.capture);
                if(FEN.equals("")) input.change_check_delete(true);
            }
        }
    }
    public void paint_old_new(int col1,int row1,int col2, int row2) {
        old_col = col1;
        old_row = row1;
        new_col = col2;
        new_row = row2;
    }
    private void movePawn(Move move) {
        //enPassant
        int colorIndex;
        if(!rotating) colorIndex = move.piece.isWhite ? 1 : -1;
        else colorIndex = move.piece.isWhite ? -1 : 1;
        if(getTileNum(move.getNewCol(),move.getNewRow()) == enPassantTile) {
            move.capture = getPiece(move.getNewCol(),move.getNewRow() + colorIndex);
        }
        if(Math.abs(move.piece.row - move.getNewRow()) == 2) {
            enPassantTile = getTileNum(move.getNewCol(),move.getNewRow() + colorIndex);
        }
        else enPassantTile = -1;
        move.piece.col = move.getNewCol();
        move.piece.row = move.getNewRow();
        move.piece.xPos = move.getNewCol() * tileSize;
        move.piece.yPos = move.getNewRow() * tileSize;
        if(move.capture != null) {
            sound.playMusic(3);
            delete_piece(move.capture);
            if(FEN.equals(""))input.change_check_delete(true);
        }
        this.repaint();
        move.piece.the_pawn_first_move = false;
        //promotions
        if(!rotating) colorIndex = move.piece.isWhite ? 0 : 7;
        else colorIndex = move.piece.isWhite ? 7 : 0;

        if(move.getNewRow() == colorIndex && findKing(true) != null && findKing(false) != null) {
            promotePawn(move);
            if(FEN.equals("")) input.change_check_promotion(true);
        }
    }
    public void promotePawn (Move move) {
        promotion = -1;
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        int select = JOptionPane.showOptionDialog(null, "Choose Piece To Promote to", null, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch(select) {
            case 0:
                pieceList.add(new Queen(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                promotion = 0;
                break;
            case 1:
                pieceList.add(new Rook(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                promotion = 1;
                break;
            case 2:
                pieceList.add(new Knight(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                promotion = 2;
                break;
            case 3:
                pieceList.add(new Bishop(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                promotion = 3;
                break;
        }
        sound.playMusic(5);
        delete_piece(move.piece);
    }
    public String step(String step,Move move) {
        if(input.check_delete) {
            step += 'x';
        }
        else {
            if(Character.isLowerCase(step.charAt(0))) {
                step = "";
            }
        }
        if(!rotating) {
            step += column.charAt(move.getNewCol());
            step += String.valueOf(8 - move.getNewRow());
        }
        else {
            step += column_rotate.charAt(move.getNewCol());
            step += String.valueOf(move.getNewRow() + 1);
        }
        if(input.check_promotion) {
            switch (promotion) {
                case 0:
                    step += "=Q";
                    break;
                case 1:
                    step += "=R";
                    break;
                case 2:
                    step += "=B";
                    break;
                case 3:
                    step += "=N";
                    break;
            }
        }
        return step;
    }
    public void delete_piece(Piece piece){
        pieceList.remove(piece);
    }
    public boolean sameTeam(Piece p1,Piece p2){// check team
        if(p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }
    public void paint_in_place(int col, int row) {
        col_in_place = col;
        row_in_place = row;
    }
    public boolean isValidMove(Move move) {// check nước đi
        if(sameTeam(move.piece,move.capture)) {
            return false;
        }
        if(!move.piece.check_the_valid_moves_of_the_chess_pieces(move.getNewCol(),move.getNewRow())){
            return false;
        }
        if(move.piece.moveCollidesWithPiece(move.getNewCol(),move.getNewRow())){//Nếu quân cờ di chuyển gặp va chạm với một quân cờ cùng team thì dừng trước nó
            return false;
        }
        if(checkScanner.isKingChecked(move)) {
            return false;
        }
        return true;
    }
    public int getTileNum(int col,int row) {
        return row * rows + col;
    }

    public void addPiece() {
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));

        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));

        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));

        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));
    }
    public void handleFen(String fen) {
        //   4R1k1/2p2q1p/4pBpQ/p2pP3/r3p3/4P2P/5PP1/6K1 b - - 0 27,f7e8 h6g7
        int cnt = 0;
        for (int j = 0; j < 8; ++j) {
            for (int i = 0; i < 8; ++i) {
                char ch = fen.charAt(cnt);
                if (Character.isDigit(ch)) {
                    i += Character.getNumericValue(ch) - 1;
                } else {
                    switch (ch) {
                        case 'r':
                            pieceList.add(new Rook(this,i,j,false));
                            break;
                        case 'p':
                            pieceList.add(new Pawn(this,i,j,false));
                            break;
                        case 'n':
                            pieceList.add(new Knight(this,i,j,false));
                            break;
                        case 'b':
                            pieceList.add(new Bishop(this,i,j,false));
                            break;
                        case 'q':
                            pieceList.add(new Queen(this,i,j,false));
                            break;
                        case 'k':
                            pieceList.add(new King(this,i,j,false));
                            break;
                        case 'R':
                            pieceList.add(new Rook(this,i,j,true));
                            break;
                        case 'P':
                            pieceList.add(new Pawn(this,i,j,true));
                            break;
                        case 'N':
                            pieceList.add(new Knight(this, i,j,true));
                            break;
                        case 'B':
                            pieceList.add(new Bishop(this,i,j,true));
                            break;
                        case 'Q':
                            pieceList.add(new Queen(this,i,j,true));
                            break;
                        case 'K':
                            pieceList.add(new King(this,i,j,true));
                            break;
                    }
                }
                ++cnt;
            }
            if (fen.charAt(cnt) == '/') {
                ++cnt;
            }
            if(j == 7) {
                ++cnt;
                color_to_move = (fen.charAt(cnt) == 'b') ?  false : true;
                while (cnt < fen.length()) {
                    if (fen.charAt(cnt) == ',') {
                        ++cnt;
                        break;
                    }
                    ++cnt;
                }
                while (cnt < fen.length() - 1) {
                    pieceMove.add(fen.substring(cnt, cnt + 4));
                    cnt += 5;
                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(board_image,0,0,tileSize * 8,tileSize * 8,this);
        if(col_in_place != -1) {
            g2d.setColor(new Color(224, 207, 56, 131));
            g2d.fillRect(col_in_place* 85, row_in_place * 85, 85, 85);
        }
        if(selectedPiece != null) {
            for(int i = 0;i < rows; ++i){
                for(int j = 0;j < cols; ++j){
                    if(isValidMove(new Move(this,selectedPiece,i, j))){
                        g2d.setColor(new Color(90, 171, 93, 190));
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
        if(old_col != -1) {
            g2d.setColor(new Color(36, 144, 193, 151));
            g2d.fillRect(old_col* 85, old_row * 85, 85, 85);
            g2d.fillRect(new_col* 85, new_row * 85, 85, 85);
        }
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
