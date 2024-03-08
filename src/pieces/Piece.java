package pieces;

import model.GamePVP;
import model.Setting;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Piece {
    public int col,row;
    public int xPos;
    public int yPos;
    public boolean isWhite;
    public String name;
    Image sprite;
    GamePVP board;
    public boolean the_pawn_first_move = true;

    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces/default.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6;

    public Piece(GamePVP board) {
        this.board = board;
    }
    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite,xPos,yPos,null);
    }
    public boolean check_the_valid_moves_of_the_chess_pieces(int col,int row) { return true; }
    public boolean moveCollidesWithPiece(int col,int row) { return false; }
}
