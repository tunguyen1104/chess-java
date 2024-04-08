package model.pieces;

import model.Board;
import model.JDBCConnection;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Piece {
    public int col, row;
    public int xPos;
    public int yPos;
    public boolean isWhite;
    public String name;
    Image sprite;
    Board board;
    public boolean the_pawn_first_move = true;

    public Piece(Board board) {
        this.board = board;
    }

    protected BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(new File(JDBCConnection.takeDataSetting().get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6;

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos + 2, yPos + 2, 80, 80, null);
    }

    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }
}
