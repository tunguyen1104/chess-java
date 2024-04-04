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

public class Board extends JPanel {
    private GamePVP game;
    public int tileSize = 85;
    public int rows = 8;
    public int cols = 8;
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    public ArrayList<String> piecePuzzle = new ArrayList<String>();
    private ArrayList<Piece> rotateList = new ArrayList<Piece>();
    public boolean rotating = false;
    public Piece selectedPiece;// quân cờ lúc bạn trỏ vào
    public Listener input;
    public ListenerPuzzle inputPuzzle;
    public int enPassantTile = -1;
    public int promotion = -1;
    Sound sound;
    private BufferedImage board_image;
    // paint old new piece
    private int old_col = -1;
    private int old_row = -1;
    private int new_col = -1;
    private int new_row = -1;
    private int col_in_place = -1;
    private int row_in_place = -1;
    private ArrayList<String> dataJDBC = JDBCConnection.takeDataSetting();
    private ArrayList<String> dataPuzzle = JDBCConnection.takeDataPuzzle();
    // dataJDBC =
    Timer timer;
    public boolean color_to_move;
    private PuzzleGame puzzleGame;
    public String column = "abcdefgh";
    public String column_rotate = "hgfedcba";
    private String FEN = "";
    public int index_piecePuzzle = 0;
    public int done_failed_puzzle = -1;// 1 = done, 2 = failed
    private int col_puzzle = -1;
    private int row_puzzle = -1;
    private int col_hint = -1;
    private int row_hint = -1;

    public void setHintBoolean(boolean hintBoolean) {
        this.hintBoolean = hintBoolean;
    }

    private boolean hintBoolean = false;

    public Board(PuzzleGame puzzleGame, String FEN) {
        if (dataJDBC.get(2).equals("1"))
            sound = new Sound();
        else
            sound = new Sound(1);
        this.puzzleGame = puzzleGame;
        this.FEN = FEN;
        sound.playMusic(0);
        inputPuzzle = new ListenerPuzzle(this, sound);
        try {
            board_image = ImageIO.read(new File(dataJDBC.get(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(inputPuzzle);
        this.addMouseMotionListener(inputPuzzle);
        handleFen(FEN);
        animation();
    }

    public Board(GamePVP game) {
        if (dataJDBC.get(2).equals("1"))
            sound = new Sound();
        else
            sound = new Sound(1);
        this.game = game;
        sound.playMusic(0);
        input = new Listener(this, game, sound);
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

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public void animation() {
        done_failed_puzzle = -1;
        hintBoolean = false;
        col_hint = -1;
        row_hint = -1;
        col_puzzle = -1;
        row_puzzle = -1;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    System.out.println();
                }
                animate(8 - (piecePuzzle.get(index_piecePuzzle).charAt(1) - '0'),
                        piecePuzzle.get(index_piecePuzzle).charAt(0) - 'a',
                        8 - (piecePuzzle.get(index_piecePuzzle).charAt(3) - '0'),
                        piecePuzzle.get(index_piecePuzzle).charAt(2) - 'a');
                ++index_piecePuzzle;
            }
        };
        thread.start();
    }

    public void hint(Graphics2D g2d) {
        col_hint = piecePuzzle.get(index_piecePuzzle).charAt(0) - 'a';
        row_hint = 8 - (piecePuzzle.get(index_piecePuzzle).charAt(1) - '0');
        g2d.setColor(new Color(102, 159, 128, 200));
        g2d.fillRect(col_hint * tileSize, row_hint * tileSize, tileSize, tileSize);
        hintBoolean = false;
        col_hint = -1;
        row_hint = -1;
    }

    public void solvePuzzle(Move move) {
        int currentCol = piecePuzzle.get(index_piecePuzzle).charAt(0) - 'a';
        int currentRow = 8 - (piecePuzzle.get(index_piecePuzzle).charAt(1) - '0');
        int newCol = piecePuzzle.get(index_piecePuzzle).charAt(2) - 'a';
        int newRow = 8 - (piecePuzzle.get(index_piecePuzzle).charAt(3) - '0');
        if (currentCol == move.getOldCol() && currentRow == move.getOldRow() && newCol == move.getNewCol()
                && newRow == move.getNewRow()) {
            ++index_piecePuzzle;
            if (index_piecePuzzle < piecePuzzle.size()) {
                sound.playMusic(7);
                done_failed_puzzle = 1;
                col_puzzle = newCol;
                row_puzzle = newRow;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            System.out.println();
                        }
                        animation();
                    }
                };
                thread.start();
            } else {
                // done puzzle
                sound.playMusic(1);
                col_puzzle = newCol;
                row_puzzle = newRow;
                puzzleGame.done_panel.setVisible(true);
                puzzleGame.hint_panel.setVisible(false);
                puzzleGame.undo_panel.setVisible(false);
                done_failed_puzzle = 1;
                /// handle JDBC
                String update_failed = "";
                int index_failed_puzzle = -1;
                String lever_string = String.valueOf(puzzleGame.lever);

                if (!dataPuzzle.get(0).isEmpty()) {
                    String[] numbers = dataPuzzle.get(0).split(",");
                    for (int i = 0; i < numbers.length; ++i) {
                        if (numbers[i].equals(lever_string)) {
                            index_failed_puzzle = i;
                            break;
                        }
                    }
                    if (index_failed_puzzle != -1) {
                        StringBuilder updatedBuilder = new StringBuilder();
                        for (int i = 0; i < numbers.length; ++i) {
                            if (i != index_failed_puzzle) {
                                updatedBuilder.append(numbers[i]).append(',');
                            }
                        }
                        update_failed = updatedBuilder.toString();
                        if (!update_failed.isEmpty()) {
                            update_failed = update_failed.substring(0, update_failed.length() - 1);
                        }
                    } else {
                        update_failed = dataPuzzle.get(0);
                    }
                }
                String update_solved = dataPuzzle.get(1);
                if (index_failed_puzzle == -1) {// nếu trong failed ko có
                    if (!update_solved.isEmpty()) {
                        String number[] = update_solved.split(",");
                        for (int i = 0; i < number.length; ++i) {
                            if (number[i].equals(lever_string)) {// nếu trong solved có rồi thì thôi
                                return;
                            }
                        }
                    }
                }
                if (update_solved.isEmpty())
                    update_solved = lever_string;
                else
                    update_solved += ',' + lever_string;
                JDBCConnection.updateDataPuzzle(update_failed, update_solved);
            }
        } else {
            // failed
            sound.playMusic(6);
            col_puzzle = move.piece.col;
            row_puzzle = move.piece.row;
            puzzleGame.done_panel.setVisible(false);
            puzzleGame.hint_panel.setVisible(false);
            puzzleGame.undo_panel.setVisible(true);
            done_failed_puzzle = 2;
            if (!dataPuzzle.get(1).isEmpty()) {
                String number[] = dataPuzzle.get(1).split(",");// nếu solved có rồi thì thôi
                for (int i = 0; i < number.length; ++i) {
                    if (Integer.parseInt(number[i]) == puzzleGame.lever) {
                        return;
                    }
                }
            }
            String update_failed = dataPuzzle.get(0);
            if (!update_failed.isEmpty()) {
                String number[] = update_failed.split(",");
                for (int i = 0; i < number.length; ++i) {
                    if (Integer.parseInt(number[i]) == puzzleGame.lever) {// nếu faile có rồi thì thôi
                        return;
                    }
                }
                update_failed += ',' + String.valueOf(puzzleGame.lever);
            } else
                update_failed += String.valueOf(puzzleGame.lever);
            JDBCConnection.updateDataPuzzle(update_failed, dataPuzzle.get(1));
        }
    }

    private void animate(int x, int y, int x_new, int y_new) {
        paint_old_new(y, x, y_new, x_new);
        Piece piece = getPiece(y, x);
        pieceList.remove(piece);
        if (getPiece(y_new, x_new) != null) {
            pieceList.remove(getPiece(y_new, x_new));
            sound.playMusic(3);
        } else {
            sound.playMusic(2);
        }
        if (piece.name.equals("King"))
            pieceList.add(new King(this, y_new, x_new, color_to_move));
        else if (piece.name.equals("Queen"))
            pieceList.add(new Queen(this, y_new, x_new, color_to_move));
        else if (piece.name.equals("Rook"))
            pieceList.add(new Rook(this, y_new, x_new, color_to_move));
        else if (piece.name.equals("Bishop"))
            pieceList.add(new Bishop(this, y_new, x_new, color_to_move));
        else if (piece.name.equals("Knight"))
            pieceList.add(new Knight(this, y_new, x_new, color_to_move));
        else if (piece.name.equals("Pawn"))
            pieceList.add(new Pawn(this, y_new, x_new, color_to_move));
        repaint();
    }

    public Piece findKing(boolean isWhite) {
        for (Piece piece : pieceList) {
            if (piece.isWhite == isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void notify_done_puzzle(Graphics2D g2d) {
        int _col = col_puzzle * tileSize + 70;
        int _row = row_puzzle * tileSize - 10;
        if (done_failed_puzzle == 1) {
            g2d.drawImage(puzzleGame.circle_check, _col, _row, 28, 28, puzzleGame);
        } else if (done_failed_puzzle == 2) {
            g2d.drawImage(puzzleGame.circle_xmark, _col, _row, 28, 28, puzzleGame);
        }
    }

    public void rotateBoard() {
        rotating = !rotating;
        rotateList.clear();
        for (Piece piece : pieceList) {
            if (piece.name.equals("Pawn")) {
                Piece pawn = new Pawn(this, 7 - piece.col, 7 - piece.row, piece.isWhite);
                pawn.the_pawn_first_move = piece.the_pawn_first_move;
                rotateList.add(pawn);
            } else if (piece.name.equals("King")) {
                rotateList.add(new King(this, 7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Queen")) {
                rotateList.add(new Queen(this, 7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Rook")) {
                rotateList.add(new Rook(this, 7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Bishop")) {
                rotateList.add(new Bishop(this, 7 - piece.col, 7 - piece.row, piece.isWhite));
            } else if (piece.name.equals("Knight")) {
                rotateList.add(new Knight(this, 7 - piece.col, 7 - piece.row, piece.isWhite));
            }
        }
        enPassantTile = 63 - enPassantTile;
        pieceList.clear();
        pieceList.addAll(rotateList);
        if (old_col != -1) {
            old_col = 7 - old_col;
            old_row = 7 - old_row;
            new_col = 7 - new_col;
            new_row = 7 - new_row;
        }
        if (col_in_place != -1) {
            col_in_place = 7 - col_in_place;
            row_in_place = 7 - row_in_place;
        }
        if (rotating) {
            game.getWhite_name().setBounds(1050, 140, 490, 120);
            game.getBlack_name().setBounds(1050, 610, 490, 120);
            game.getTimeLabelWhite().setBounds(1100, 160, 500, 200);
            game.getTimeLabelBlack().setBounds(1100, 505, 500, 200);
        } else {
            game.getWhite_name().setBounds(1050, 610, 490, 120);
            game.getBlack_name().setBounds(1050, 140, 490, 120);
            game.getTimeLabelWhite().setBounds(1100, 505, 500, 200);
            game.getTimeLabelBlack().setBounds(1100, 160, 500, 200);
        }
        sound.playMusic(4);
        repaint();
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            move.piece.col = move.getNewCol();
            move.piece.row = move.getNewRow();
            move.piece.xPos = move.getNewCol() * tileSize;
            move.piece.yPos = move.getNewRow() * tileSize;
            if (move.capture != null) {
                sound.playMusic(3);
                delete_piece(move.capture);
                if (FEN.equals(""))
                    input.change_check_delete(true);
            }
        }
    }

    public void paint_old_new(int col1, int row1, int col2, int row2) {
        old_col = col1;
        old_row = row1;
        new_col = col2;
        new_row = row2;
    }

    private void movePawn(Move move) {
        // enPassant
        int colorIndex;
        if (!rotating)
            colorIndex = move.piece.isWhite ? 1 : -1;
        else
            colorIndex = move.piece.isWhite ? -1 : 1;
        if (getTileNum(move.getNewCol(), move.getNewRow()) == enPassantTile) {
            move.capture = getPiece(move.getNewCol(), move.getNewRow() + colorIndex);
        }
        if (Math.abs(move.piece.row - move.getNewRow()) == 2) {
            enPassantTile = getTileNum(move.getNewCol(), move.getNewRow() + colorIndex);
        } else
            enPassantTile = -1;
        move.piece.col = move.getNewCol();
        move.piece.row = move.getNewRow();
        move.piece.xPos = move.getNewCol() * tileSize;
        move.piece.yPos = move.getNewRow() * tileSize;
        if (move.capture != null) {
            sound.playMusic(3);
            delete_piece(move.capture);
            if (FEN.equals(""))
                input.change_check_delete(true);
        }
        this.repaint();
        move.piece.the_pawn_first_move = false;
        // promotions
        if (!rotating)
            colorIndex = move.piece.isWhite ? 0 : 7;
        else
            colorIndex = move.piece.isWhite ? 7 : 0;

        if (move.getNewRow() == colorIndex && findKing(true) != null && findKing(false) != null) {
            promotePawn(move);
            if (FEN.equals(""))
                input.change_check_promotion(true);
        }
    }

    public void promotePawn(Move move) {
        promotion = -1;
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        int select = JOptionPane.showOptionDialog(null, "Choose Piece To Promote to", "Promote",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (select == -1) {
            pieceList.add(new Queen(this, move.getNewCol(), move.getNewRow(), move.piece.isWhite));
            promotion = 0;
        } else {
            switch (select) {
                case 0:
                    pieceList.add(new Queen(this, move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 0;
                    break;
                case 1:
                    pieceList.add(new Rook(this, move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 1;
                    break;
                case 2:
                    pieceList.add(new Knight(this, move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 2;
                    break;
                case 3:
                    pieceList.add(new Bishop(this, move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 3;
                    break;
            }
        }
        sound.playMusic(5);
        delete_piece(move.piece);
    }

    public String step(String step, Move move) {
        if (input.check_delete) {
            step += 'x';
        } else {
            if (Character.isLowerCase(step.charAt(0))) {
                step = "";
            }
        }
        if (!rotating) {
            step += column.charAt(move.getNewCol());
            step += String.valueOf(8 - move.getNewRow());
        } else {
            step += column_rotate.charAt(move.getNewCol());
            step += String.valueOf(move.getNewRow() + 1);
        }
        if (input.check_promotion) {
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

    public void delete_piece(Piece piece) {
        pieceList.remove(piece);
    }

    public boolean sameTeam(Piece p1, Piece p2) {// check team
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public void paint_in_place(int col, int row) {
        col_in_place = col;
        row_in_place = row;
    }

    public boolean isValidMove(Move move) {// check nước đi
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        if (!move.piece.check_the_valid_moves_of_the_chess_pieces(move.getNewCol(), move.getNewRow())) {
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.getNewCol(), move.getNewRow())) {// Nếu quân cờ di chuyển gặp va chạm
                                                                                   // với một quân cờ thì dừng
                                                                                   // trước nó
            return false;
        }
        if (isKingChecked(move)) {
            return false;
        }
        return true;
    }

    public boolean isKingChecked(Move move) {
        Piece king = findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (selectedPiece != null && selectedPiece.name.equals("King")) {
            kingCol = move.getNewCol();
            kingRow = move.getNewRow();
        }
        return false;
    }

    private boolean hitByRook(int col, int row, Piece King, int kingCol, int kingRow, int colVal, int rowVal) {
        return true;
    }

    public int getTileNum(int col, int row) {
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
        // 4R1k1/2p2q1p/4pBpQ/p2pP3/r3p3/4P2P/5PP1/6K1 b - - 0 27,f7e8 h6g7
        int cnt = 0;
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                char ch = fen.charAt(cnt);
                if (Character.isDigit(ch)) {
                    col += Character.getNumericValue(ch) - 1;
                } else {
                    switch (ch) {
                        case 'r':
                            pieceList.add(new Rook(this, col, row, false));
                            break;
                        case 'p':
                            pieceList.add(new Pawn(this, col, row, false));
                            break;
                        case 'n':
                            pieceList.add(new Knight(this, col, row, false));
                            break;
                        case 'b':
                            pieceList.add(new Bishop(this, col, row, false));
                            break;
                        case 'q':
                            pieceList.add(new Queen(this, col, row, false));
                            break;
                        case 'k':
                            pieceList.add(new King(this, col, row, false));
                            break;
                        case 'R':
                            pieceList.add(new Rook(this, col, row, true));
                            break;
                        case 'P':
                            pieceList.add(new Pawn(this, col, row, true));
                            break;
                        case 'N':
                            pieceList.add(new Knight(this, col, row, true));
                            break;
                        case 'B':
                            pieceList.add(new Bishop(this, col, row, true));
                            break;
                        case 'Q':
                            pieceList.add(new Queen(this, col, row, true));
                            break;
                        case 'K':
                            pieceList.add(new King(this, col, row, true));
                            break;
                    }
                }
                ++cnt;
            }
            if (fen.charAt(cnt) == '/') {
                ++cnt;
            }
            if (row == 7) {
                ++cnt;
                color_to_move = (fen.charAt(cnt) == 'b') ? false : true;
                while (cnt < fen.length()) {
                    if (fen.charAt(cnt) == ',') {
                        ++cnt;
                        break;
                    }
                    ++cnt;
                }
                while (cnt < fen.length() - 1) {
                    piecePuzzle.add(fen.substring(cnt, cnt + 4));
                    cnt += 5;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(board_image, 0, 0, tileSize * 8, tileSize * 8, this);
        if (col_in_place != -1) {
            g2d.setColor(new Color(224, 207, 56, 131));
            g2d.fillRect(col_in_place * tileSize, row_in_place * tileSize, tileSize, tileSize);
        }
        if (selectedPiece != null) {
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    if (isValidMove(new Move(this, selectedPiece, i, j))) {
                        g2d.setColor(new Color(90, 171, 93, 190));
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
        if (old_col != -1) {
            g2d.setColor(new Color(36, 144, 193, 151));
            g2d.fillRect(old_col * tileSize, old_row * tileSize, tileSize, tileSize);
            g2d.fillRect(new_col * tileSize, new_row * tileSize, tileSize, tileSize);
        }
        if (hintBoolean == true) {
            hint(g2d);
        }
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
        if (done_failed_puzzle != -1) {
            notify_done_puzzle(g2d);
        }
    }
}
