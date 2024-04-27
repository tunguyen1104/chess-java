package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import model.pieces.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.ListenerReview;
import view.Review;
public class BoardReview extends JPanel{
    private ArrayList data_link;
    private Sound sound;
    private Image board_image;
    private Review review;
    private ListenerReview listenerReview;
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    List<String> move = new ArrayList<>();
    Map<Character, Integer> columnMap = new HashMap<>();
    public boolean rotating = false;
    public int index = 0;
    public BoardReview(Review review, File namFile) {
        this.review = review;
        listenerReview = new ListenerReview(review, this);
        data_link = JDBCConnection.takeDataSetting();
        if (data_link.get(2).equals("1"))
            sound = new Sound();
        else
            sound = new Sound(1);
        sound.playMusic(0);
        try {
            board_image = ImageIO.read(new File(data_link.get(1).toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (char column = 'a'; column <= 'h'; ++column) {
            columnMap.put(column, column - 'a');
        }
        addPiece();
        handlePgn(namFile);
    }
    public void handlePgn(File nameFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nameFile));
            String line;
            int cnt = 0;
            while ((line = reader.readLine()) != null) {
                ++cnt;
                if(cnt == 5) break;
            }
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String move1 = line.substring(4, 12).trim();
                String move2 = line.substring(13).trim();
                move.add(move1);move.add(move2);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void handle_first_button() {
        if(index == 0) return;
        index = 0;
        pieceList.clear();
        addPiece();
        repaint();
    }
    public void handle_last_button() {
        if(index == move.size() - 1) return;
        index = move.size() - 1;
    }
    public void handle_next_button() {
        if(index == move.size() - 1) return;
        ++index;
    }
    public void handle_previous_button() {
        if(index == 0) return;
        --index;
    }
    public void handlev1(String x) {
        int length = x.length();
        if(length == 2) {
            int col = columnMap.get(x.charAt(0));
            int row = x.charAt(1) - '0';
        } else if(length == 3) {

        }
    }
    public void rotateBoard() {
        rotating = !rotating;
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
        
        pieceList.add(new Pawn(0, 1, false));
        pieceList.add(new Pawn(1, 1, false));
        pieceList.add(new Pawn(2, 1, false));
        pieceList.add(new Pawn(3, 1, false));
        pieceList.add(new Pawn(4, 1, false));
        pieceList.add(new Pawn(5, 1, false));
        pieceList.add(new Pawn(6, 1, false));
        pieceList.add(new Pawn(7, 1, false));

        pieceList.add(new Rook(0, 7, true));
        pieceList.add(new Knight(1, 7, true));
        pieceList.add(new Bishop(2, 7, true));
        pieceList.add(new Queen(3, 7, true));
        pieceList.add(new King(4, 7, true));
        pieceList.add(new Bishop(5, 7, true));
        pieceList.add(new Knight(6, 7, true));
        pieceList.add(new Rook(7, 7, true));
        
        pieceList.add(new Pawn(0, 6, true));
        pieceList.add(new Pawn(1, 6, true));
        pieceList.add(new Pawn(2, 6, true));
        pieceList.add(new Pawn(3, 6, true));
        pieceList.add(new Pawn(4, 6, true));
        pieceList.add(new Pawn(5, 6, true));
        pieceList.add(new Pawn(6, 6, true));
        pieceList.add(new Pawn(7, 6, true));
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(board_image, 0,0,640,640, this);
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
