package view;

import controller.Listener;
import model.Sound;
import model.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BoardPuzzle extends JPanel {
    private PuzzleGame puzzleGame;
    private String FEN;
    public int tileSize = 85;
    public int rows = 8;
    public int cols = 8;
    public boolean isTurn;
    public Piece selectedPiece;
    public Listener input;
    private BufferedImage br;
    private BufferedImage bn;
    private BufferedImage bb;
    private BufferedImage bq;
    private BufferedImage bk;
    private BufferedImage bp;
    private BufferedImage wr;
    private BufferedImage wn;
    private BufferedImage wb;
    private BufferedImage wq;
    private BufferedImage wk;
    private BufferedImage wp;
    private BufferedImage board;
    Sound sound = new Sound();
    private Set<String> whiteMove = new HashSet<String>();
    private Set<String> blackMove = new HashSet<String>();
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    public BoardPuzzle(PuzzleGame puzzleGame,String FEN) {
        this.puzzleGame = puzzleGame;
        this.FEN = FEN;
        sound.playMusic(0);
        //input = new Listener(this,puzzleGame);
        try {
            board = ImageIO.read(new File("src/res/board/metal.png"));
            br = ImageIO.read(new File("src/res/pieces/br.png"));
            bn = ImageIO.read(new File("src/res/pieces/bn.png"));
            bb = ImageIO.read(new File("src/res/pieces/bb.png"));
            bq = ImageIO.read(new File("src/res/pieces/bq.png"));
            bk = ImageIO.read(new File("src/res/pieces/bk.png"));
            bp = ImageIO.read(new File("src/res/pieces/bp.png"));
            wr = ImageIO.read(new File("src/res/pieces/wr.png"));
            wn = ImageIO.read(new File("src/res/pieces/wn.png"));
            wb = ImageIO.read(new File("src/res/pieces/wb.png"));
            wq = ImageIO.read(new File("src/res/pieces/wq.png"));
            wp = ImageIO.read(new File("src/res/pieces/wp.png"));
            wk = ImageIO.read(new File("src/res/pieces/wk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
    }
    public void handleFen(String fen, Graphics2D g2d) {
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
                            paintPiece(g2d, br, i, j);
                            break;
                        case 'p':
                            paintPiece(g2d, bp, i, j);
                            break;
                        case 'n':
                            paintPiece(g2d, bn, i, j);
                            break;
                        case 'b':
                            paintPiece(g2d, bb, i, j);
                            break;
                        case 'q':
                            paintPiece(g2d, bq, i, j);
                            break;
                        case 'k':
                            paintPiece(g2d, bk, i, j);
                            break;
                        case 'R':
                            paintPiece(g2d, wr, i, j);
                            break;
                        case 'P':
                            paintPiece(g2d, wp, i, j);
                            break;
                        case 'N':
                            paintPiece(g2d, wn, i, j);
                            break;
                        case 'B':
                            paintPiece(g2d, wb, i, j);
                            break;
                        case 'Q':
                            paintPiece(g2d, wq, i, j);
                            break;
                        case 'K':
                            paintPiece(g2d, wk, i, j);
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
                isTurn = (fen.charAt(cnt) == 'b') ?  false : true;
                while (cnt < fen.length()) {
                    if (fen.charAt(cnt) == ',') {
                        ++cnt;
                        break;
                    }
                    ++cnt;
                }
                while (cnt + 5 < fen.length()) {
                    whiteMove.add(fen.substring(cnt, cnt + 4));
                    cnt += 5;
                    blackMove.add(fen.substring(cnt, cnt + 4));
                    cnt += 5;
                }
            }
        }
    }


    public void paintPiece(Graphics2D g2d,BufferedImage image,int X,int Y) {
        g2d.drawImage(image,X*tileSize,Y*tileSize,tileSize,tileSize,this);

    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(board,0,0,tileSize * 8,tileSize * 8,this);
        handleFen(FEN,g2d);
    }
}
