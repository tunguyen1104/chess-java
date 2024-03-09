package model;

import pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

public class GamePVP extends JPanel {
    public JFrame frame;
    private JPanel panel;
    private BufferedImage game_gui;
    private BufferedImage title_bar;
    private BufferedImage back_normal;
    private BufferedImage home_normal;
    Sound sound = new Sound();
    private JLabel white_name;
    private JLabel black_name;
    private JLabel title_bar_label;
    //time
    public JLabel timeLabelWhite = new JLabel();
    public JLabel timeLabelBlack = new JLabel();
    private int seconds_white;
    private int minutes_white;
    private int seconds_black;
    private int minutes_black;
    public String seconds_string1;
    public String minutes_string1;
    public String seconds_string2;
    public String minutes_string2;
    //
    public int tileSize = 85;
    public int rows = 8;
    public int cols = 8;
    Set<Piece> pieceList = new HashSet<Piece>();
    protected Piece selectedPiece;// quân cờ lúc bạn trỏ vào
    Listener input = new Listener(this);
    public int enPassantTile = -1;
    //paint old new piece
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    public GamePVP(int minute) {
        seconds_white = 0;
        minutes_white = minute;
        seconds_black = 0;
        minutes_black = minute;
        seconds_string1 = String.format("%02d", seconds_white);
        minutes_string1 = String.format("%02d", minutes_white);
        seconds_string2 = String.format("%02d", seconds_black);
        minutes_string2 = String.format("%02d", minutes_black);
        initPanel();//panel
        initBoard();//this
        panel.add(this);
        frame = new JFrame("CHESS PVP");
        sound.playMusic(0);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-6,0);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "You want exit?", "Notification",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION)
                    System.exit(0);
                else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
    public void initPanel() {
        // Load image
        try {
            game_gui = ImageIO.read(new File("src/res/gui/game_gui.png"));
            title_bar = ImageIO.read(new File("src/res/gui/title_bar.png"));
            back_normal = ImageIO.read(new File("src/res/buttons/back_normal.png"));
            home_normal = ImageIO.read(new File("src/res/buttons/home_normal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                g2d.drawImage(game_gui, 250, 70, this);
                g2d.drawImage(title_bar,530,10,450,42,this);
            }

        };
        // Set preferred size of the panel to match background image size
        panel.setBackground(new Color(41,41,41));
        panel.setPreferredSize(new Dimension(1600, 1000));
        panel.setLayout(null);
        //setting name
        white_name = new JLabel("Player2 (Black)");
        white_name.setBounds(1050,140,490,120);
        white_name.setForeground(Color.WHITE);
        white_name.setFont(white_name.getFont().deriveFont(20.0f)); // Tạo font mới với kích thước mới và thiết lập cho nhãn

        black_name = new JLabel("Player1 (White)");
        black_name.setBounds(1050,610,490,120);
        black_name.setForeground(Color.WHITE);
        black_name.setFont(black_name.getFont().deriveFont(20.0f));
        //==========Time_label_white==========
        timeLabelWhite.setText(minutes_string1 + ":" + seconds_string1);
        timeLabelWhite.setBounds(1100, 160, 500, 200);
        timeLabelWhite.setFont(timeLabelWhite.getFont().deriveFont(40.0f));
        // ==========Time_label_black==========
        timeLabelBlack.setText(minutes_string2 + ":" + seconds_string2);
        timeLabelBlack.setBounds(1100, 505, 500, 200);
        timeLabelBlack.setFont(timeLabelBlack.getFont().deriveFont(40.0f));
        //===========Title Bar============
        title_bar_label = new JLabel("Standard - PvP");
        title_bar_label.setBounds(680,0,400,60);
        title_bar_label.setForeground(Color.WHITE);
        title_bar_label.setFont(title_bar_label.getFont().deriveFont(18.0f)); // Tạo font mới với kích thước mới và thiết lập cho nhãn
        //================================
        panel.add(timeLabelWhite);
        panel.add(timeLabelBlack);
        panel.add(white_name);
        panel.add(black_name);

        MyImageButton back_normal_button = new MyImageButton(back_normal,"back_normal");
        MyImageButton home_normal_button = new MyImageButton(home_normal,"home_normal");
        back_normal_button.setBounds(465,10,42,42);
        home_normal_button.setBounds(1000,10,42,42);
        back_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new GameOptions();
            }
        });
        home_normal_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Menu();
            }
        });
        panel.add(back_normal_button);
        panel.add(home_normal_button);
        panel.add(title_bar_label);
    }
    public void initBoard() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPiece();
        this.setBounds(280,90,rows * tileSize,cols * tileSize);
    }
    public Piece getPiece(int col,int row){// setting o day vi Board co pieceList
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
            if(move.capture != null)
             delete_piece(move.capture);
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
        // Nếu hàng và cột mới có quan cớ khác thì xoá đi
        move.piece.col = move.getNewCol();
        move.piece.row = move.getNewRow();
        move.piece.xPos = move.getNewCol() * tileSize;
        move.piece.yPos = move.getNewRow() * tileSize;
        move.piece.the_pawn_first_move = false;
        if(move.capture != null)
            delete_piece(move.capture);
        this.repaint();
        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.getNewRow() == colorIndex && findKing(true) != null && findKing(false) != null) {
            promotePawn(move);
        }
    }
    public void promotePawn (Move move) {
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        sound.playMusic(5);
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
    }
    public void delete_piece(Piece piece){
        sound.playMusic(3);
        pieceList.remove(piece);
    }
    public boolean sameTeam(Piece p1,Piece p2){// check team, neu cung team return true
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
        return true;
    }
    public int getTileNum(int col,int row) {
        return row * rows + col;
    }//lấy ra vị trí ô thú mấy trong 64 ô

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
    public void paintComponent(Graphics g) {// chạy liền khi new Board add vào JFrame, khi di chuyển quân cờ thì hàm sẽ load lại
        Graphics2D g2d = (Graphics2D) g;

        //paint board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor(((c + r) % 2 == 0) ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }
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
    public Timer timer_white = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (seconds_white > 0) {
                seconds_white = seconds_white - 1;
            } else if (seconds_white == 0 && minutes_white > 0) {
                minutes_white = minutes_white - 1;
                seconds_white = 59;
            }
            seconds_string1 = String.format("%02d", seconds_white);
            minutes_string1 = String.format("%02d", minutes_white);
            timeLabelWhite.setText(minutes_string1 + ":" + seconds_string1);
        }
    });
    public Timer timer_black = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (seconds_black > 0) {
                seconds_black = seconds_black - 1;
            } else if (seconds_black == 0 && minutes_black > 0) {
                minutes_black = minutes_black - 1;
                seconds_black = 59;
            }
            seconds_string2 = String.format("%02d", seconds_black);
            minutes_string2 = String.format("%02d", minutes_black);
            timeLabelBlack.setText(minutes_string2 + ":" + seconds_string2);
        }

    });
    public void start_white() {
        timer_white.start();
    }

    public void start_black() {
        timer_black.start();
    }

    public void stop_white() {
        timer_white.stop();
    }

    public void stop_black() {
        timer_black.stop();
    }

    public static void main(String[] args) {
        new GamePVP(3);
    }
}
