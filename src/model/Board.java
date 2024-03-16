package model;

import controller.Listener;
import model.pieces.*;
import view.GamePVP;

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
    public Piece selectedPiece;// quân cờ lúc bạn trỏ vào
    public Listener input;
    public int enPassantTile = -1;
    Sound sound = new Sound();
    private BufferedImage board_image;
    //paint old new piece
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    public ArrayList<String> dataJDBC;
    Piece piece;
    public CheckScanner checkScanner = new CheckScanner(this);
    public Board (GamePVP game) {
        dataJDBC = new ArrayList<String>();
        dataJDBC = JDBCConnection.takeData();
        piece = new Piece(this);
        this.game = game;
        sound.playMusic(0);
        input = new Listener(this,game);
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
    public Piece getPiece(int col, int row){// setting o day vi Board co pieceList
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
    public void makeMove(Move move) {// hàm xử lý khi di chuyển quân cờ này ăn một quân cờ khác
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
                input.change_check_delete_or_promotion(1000);
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
        int colorIndex = move.piece.isWhite ? 1 : -1;
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
            this.repaint();
            input.change_check_delete_or_promotion(1000);
        }
        move.piece.the_pawn_first_move = false;
        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.getNewRow() == colorIndex && findKing(true) != null && findKing(false) != null) {
            promotePawn(move);
            input.change_check_delete_or_promotion(2000);
        }
    }
    public void promotePawn (Move move) {
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        int select = JOptionPane.showOptionDialog(null, "Choose Piece To Promote to", null, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch(select) {
            case 0:
                pieceList.add(new Queen(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                break;
            case 1:
                pieceList.add(new Rook(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                break;
            case 2:
                pieceList.add(new Knight(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                break;
            case 3:
                pieceList.add(new Bishop(this,move.getNewCol(),move.getNewRow(),move.piece.isWhite));
                break;
        }
        delete_piece(move.piece);
        sound.playMusic(5);
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
    public int getTileNum(int col,int row) {//lấy ra vị trí ô thú mấy trong 64 ô
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

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(board_image,0,0,tileSize * 8,tileSize * 8,this);
        // paint hightlights
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
        //paint location new and old
        if(old_col != -1) {
            g2d.setColor(new Color(36, 144, 193, 151));
            g2d.fillRect(old_col* 85, old_row * 85, 85, 85);
            g2d.fillRect(new_col* 85, new_row * 85, 85, 85);
        }
        // paint pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
