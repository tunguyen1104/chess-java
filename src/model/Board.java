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
    public int tileSize = 80;
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
    private int castLing = -1;
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
    private boolean hintBoolean = false;
    private boolean checkMateWhite = false;
    private boolean checkMateBlack = false;
    private int colKingCheckMate = -1;
    private int rowKingCheckMate = -1;
    public void setHintBoolean(boolean hintBoolean) {
        this.hintBoolean = hintBoolean;
    }
    public boolean isCheckMateWhite() {
        return checkMateWhite;
    }

    public void setCheckMateWhite(boolean checkMateWhite) {
        this.checkMateWhite = checkMateWhite;
    }
    public boolean isCheckMateBlack() {
        return checkMateBlack;
    }

    public void setCheckMateBlack(boolean checkMateBlack) {
        this.checkMateBlack = checkMateBlack;
    }
    
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
            pieceList.add(new King(y_new, x_new, color_to_move));
        else if (piece.name.equals("Queen"))
            pieceList.add(new Queen(y_new, x_new, color_to_move));
        else if (piece.name.equals("Rook"))
            pieceList.add(new Rook(y_new, x_new, color_to_move));
        else if (piece.name.equals("Bishop"))
            pieceList.add(new Bishop( y_new, x_new, color_to_move));
        else if (piece.name.equals("Knight"))
            pieceList.add(new Knight(y_new, x_new, color_to_move));
        else if (piece.name.equals("Pawn"))
            pieceList.add(new Pawn(y_new, x_new, color_to_move));
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
        int _col = 0;// distance to x
        int _row = 0;// distance to y
        if (row_puzzle == 0) {
            _row = row_puzzle * tileSize + 70;
            if (col_puzzle == 7) {
                _col = col_puzzle * tileSize - 12;
            } else {
                _col = col_puzzle * tileSize + 70;
            }
        } else if (col_puzzle == 7) {
            _col = col_puzzle * tileSize - 12;
            _row = row_puzzle * tileSize - 10;
        } else {
            _col = col_puzzle * tileSize + 70;
            _row = row_puzzle * tileSize - 10;
        }

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
        if(enPassantTile != -1) enPassantTile = 63 - enPassantTile;
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
            game.getWhite_name().setBounds(1000, 140, 490, 120);
            game.getBlack_name().setBounds(1000, 590, 490, 120);
            game.getTimeLabelWhite().setBounds(1050, 160, 500, 200);
            game.getTimeLabelBlack().setBounds(1050, 490, 500, 200);
        } else {
            game.getWhite_name().setBounds(1000, 590, 490, 120);
            game.getBlack_name().setBounds(1000, 140, 490, 120);
            game.getTimeLabelWhite().setBounds(1050, 490, 500, 200);
            game.getTimeLabelBlack().setBounds(1050, 160, 500, 200);
        }
        sound.playMusic(4);
        repaint();
    }

    public void makeMove(Move move) {
        castLing = -1;
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            if (move.piece.name.equals("King")) {
                moveKing(move);
            }
            move.piece.col = move.getNewCol();
            move.piece.row = move.getNewRow();
            move.piece.xPos = move.getNewCol() * tileSize;
            move.piece.yPos = move.getNewRow() * tileSize;
            move.piece.isFirstMove = false;
            if (move.capture != null) {
                sound.playMusic(3);
                delete_piece(move.capture);
                if (FEN.equals(""))
                    input.change_check_delete(true);
            }
        }
    }
    private void moveKing(Move move) {
        if(Math.abs(move.piece.col - move.getNewCol()) == 2) {
            Piece rook;
            if(move.piece.col < move.getNewCol()) {
                castLing = 1;
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
                
            } else {
                castLing = 2;
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * tileSize;
        }
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
        move.piece.isFirstMove = false;
        // promotions
        if (!rotating)
            colorIndex = move.piece.isWhite ? 0 : 7;
        else
            colorIndex = move.piece.isWhite ? 7 : 0;

        if (move.getNewRow() == colorIndex) {
            promotePawn(move);
            if (FEN.equals(""))
                input.change_check_promotion(true);
        }
    }
    public void paint_old_new(int col1, int row1, int col2, int row2) {
        old_col = col1;
        old_row = row1;
        new_col = col2;
        new_row = row2;
    }
    public void promotePawn(Move move) {
        promotion = -1;
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        int select = JOptionPane.showOptionDialog(null, "Choose Piece To Promote to", "Promote",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (select == -1) {
            pieceList.add(new Queen(move.getNewCol(), move.getNewRow(), move.piece.isWhite));
            promotion = 0;
        } else {
            switch (select) {
                case 0:
                    pieceList.add(new Queen(move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 0;
                    break;
                case 1:
                    pieceList.add(new Rook( move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 1;
                    break;
                case 2:
                    pieceList.add(new Knight(move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 2;
                    break;
                case 3:
                    pieceList.add(new Bishop(move.getNewCol(), move.getNewRow(), move.piece.isWhite));
                    promotion = 3;
                    break;
            }
        }
        sound.playMusic(5);
        delete_piece(move.piece);
    }
    public String step_begin(Move move) {
        String step = "";
        Piece check = getPiece(move.getOldCol(), move.getOldRow());
        if (check.name.equals("Pawn")) {
            if (!rotating)
                step += column.charAt(move.getOldCol());
            else
                step += column_rotate.charAt(move.getOldCol());
        } else if (check.name.equals("Knight")) {
            step += 'N';
        } else {
            step += check.name.charAt(0);
        }
        return step;
    }
    public String step_end(String step, Move move) {
        if(castLing != -1) {
            if(castLing == 1) {
                step = "O-O";
            } else if(castLing == 2) {
                step = "O-O-O";
            }
            return step;
        }
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
    public boolean checkMateEndGame(boolean color) {
        for(Piece piece: pieceList) {
            if(piece.isWhite == color) {
                for (int r = 0; r < rows; ++r) {
                    for (int c = 0; c < cols; ++c) {
                        if (isValidMove(new Move(this, piece, c, r))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean paintKingCheckMate(boolean color) {
        Piece king = findKing(color);
        assert king != null;
        boolean check = checkMate(color, king.col, king.row);
        if(check) {
            if(color) checkMateWhite = true;
            else checkMateBlack = true;
            colKingCheckMate = king.col;
            rowKingCheckMate = king.row;
            return true;
        } else {
            colKingCheckMate = -1;
            rowKingCheckMate = -1;
            return false;
        }
    }
    public boolean isValidMove(Move move) {// check nước đi
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        if(move.piece.name.equals("Pawn") || move.piece.name.equals("King")) {
            if (!move.piece.check_the_valid_moves_of_the_chess_pieces(this, move.getNewCol(), move.getNewRow())) {
                return false;
            }
        } else {
            if (!move.piece.check_the_valid_moves_of_the_chess_pieces(move.getNewCol(), move.getNewRow())) {
                return false;
            }
            if (move.piece.moveCollidesWithPiece(this, move.getNewCol(), move.getNewRow())) {// Nếu quân cờ di chuyển gặp va chạm với một quân cờ thì dừng trước nó
                return false;
            }
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
        
        if((selectedPiece != null && selectedPiece.name.equals("King") ) || move.piece.name.equals("King")) {
            kingCol = move.getNewCol();
            kingRow = move.getNewRow();
        }
        return hitByRook(move.getNewCol(), move.getNewRow(), king.isWhite,kingCol,kingRow) ||
                hitByBishop(move.getNewCol(), move.getNewRow(), king.isWhite,kingCol,kingRow) ||
                hitByKnight(move.getNewCol(), move.getNewRow(), king.isWhite,kingCol,kingRow) ||
                hitByPawn(move.getNewCol(), move.getNewRow(), king.isWhite,kingCol,kingRow) ||
                hitByKing(king.isWhite,kingCol,kingRow);
    }
    public boolean checkMate(boolean color, int kingCol, int kingRow) {
        //Rook
        for(int i = kingCol - 1;i >= 0; --i) {//left
            Piece piece = getPiece(i, kingRow);
            if(piece != null) {
                if(piece.isWhite != color && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        for(int i = kingCol + 1;i < 8; ++i) {//right
            Piece piece = getPiece(i, kingRow);
            if(piece != null) {
                if(piece.isWhite != color && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        for(int i = kingRow - 1;i >= 0; --i) {//up
            Piece piece = getPiece(kingCol, i);
            if(piece != null) {
                if(piece.isWhite != color && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        for(int i = kingRow + 1;i < 8; ++i) {//down
            Piece piece = getPiece(kingCol, i);
            if(piece != null) {
                if(piece.isWhite != color && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        //Bishop
        // up left
        for (int i = 1; kingCol - i >= 0 && kingRow - i >= 0; ++i) {
            Piece piece = getPiece(kingCol - i, kingRow - i);
            if (piece != null) {
                if (piece.isWhite != color && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        // down left
        for (int i = 1; kingCol - i >= 0 && kingRow + i < 8; ++i) {
            Piece piece = getPiece(kingCol - i, kingRow + i);
            if (piece != null) {
                if (piece.isWhite != color && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        // up right
        for (int i = 1; kingCol + i < 8 && kingRow - i >= 0; ++i) {
            Piece piece = getPiece(kingCol + i, kingRow - i);
            if (piece != null) {
                if (piece.isWhite != color && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        // down right
        for (int i = 1; kingCol + i < 8 && kingRow + i < 8; ++i) {
            Piece piece = getPiece(kingCol + i, kingRow + i);
            if (piece != null) {
                if (piece.isWhite != color && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        //Knight
        for (int[] move : KNIGHT_MOVES) {
            int newCol = kingCol + move[0];
            int newRow = kingRow + move[1];
            if (isValidPosition(newCol, newRow)) {
                Piece piece = getPiece(newCol, newRow);
                if (piece != null && piece.name.equals("Knight") && piece.isWhite != color) {
                    return true;
                }
            }
        }
        //King
        for (int col_index = -1; col_index <= 1; col_index++) {
            for (int row_index = -1; row_index <= 1; row_index++) {
                if (col_index == 0 && row_index == 0) {
                    continue;
                }
                int newCol = kingCol + col_index;
                int newRow = kingRow + row_index;
                if (isValidPosition(newCol, newRow)) {
                    Piece piece = getPiece(newCol, newRow);
                    if (piece != null && piece.isWhite != color && piece.name.equals("King")) {
                        return true;
                    }
                }
            }
        }
        //Pawn
        int colorVal = color ? -1 : 1;
        Piece piece_left = getPiece(kingCol - 1,kingRow + colorVal);
        Piece piece_right = getPiece(kingCol + 1,kingRow + colorVal);
        if(piece_left != null && piece_left.isWhite != color && piece_left.name.equals("Pawn") ||
            piece_right != null && piece_right.isWhite != color && piece_right.name.equals("Pawn")) {
            return true;
        }
        return false;
    }
    private final int[][] ROOK_MOVES = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private boolean hitByRook(int col, int row,boolean kingIsWhite, int kingCol, int kingRow) {
        for (int[] dir : ROOK_MOVES) {
            int colDir = dir[0];
            int rowDir = dir[1];
            for (int i = 1; i < 8; ++i) {
                int newCol = kingCol + (i * colDir);
                int newRow = kingRow + (i * rowDir);
                if (!isValidPosition(newCol, newRow)) break;
                if (newCol == col && newRow == row) {
                    break;
                }
                Piece piece = getPiece(newCol, newRow);
                if (piece != null && piece != selectedPiece) {
                    if (piece.isWhite != kingIsWhite && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
    private final int[][] BISHOP_MOVES = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
    private boolean hitByBishop(int col, int row,boolean kingIsWhite, int kingCol, int kingRow) {
        for (int[] dir : BISHOP_MOVES) {
            int colDir = dir[0];
            int rowDir = dir[1];
            for (int i = 1; i < 8; ++i) {
                int newCol = kingCol - (i * colDir);
                int newRow = kingRow - (i * rowDir);
                if (!isValidPosition(newCol, newRow)) break;
                if (newCol == col && newRow == row) {
                    break;
                }
                Piece piece = getPiece(newCol, newRow);
                if (piece != null && piece != selectedPiece) {
                    if (piece.isWhite != kingIsWhite && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
    private final int[][] KNIGHT_MOVES = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
    private boolean hitByKnight(int col, int row, boolean kingIsWhite, int kingCol, int kingRow) {
        for (int[] move : KNIGHT_MOVES) {
            int newCol = kingCol + move[0];
            int newRow = kingRow + move[1];

            if (isValidPosition(newCol, newRow)) {
                Piece piece = getPiece(newCol, newRow);
                if (piece != null && piece.isWhite != kingIsWhite  && piece.name.equals("Knight") && !(newCol == col && newRow == row)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isValidPosition(int col, int row) {
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }
    private boolean hitByKing(boolean kingIsWhite, int kingCol, int kingRow) {
        for (int col_index = -1; col_index <= 1; col_index++) {
            for (int row_index = -1; row_index <= 1; row_index++) {
                if (col_index == 0 && row_index == 0) {
                    continue;
                }
                int newCol = kingCol + col_index;
                int newRow = kingRow + row_index;
                if (isValidPosition(newCol, newRow)) {
                    Piece piece = getPiece(newCol, newRow);
                    if (piece != null && piece.isWhite != kingIsWhite && piece.name.equals("King")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean hitByPawn(int col,int row,boolean kingIsWhite, int kingCol, int kingRow) {
        int colorVal = kingIsWhite ? -1 : 1;
        Piece piece_left = getPiece(kingCol - 1,kingRow + colorVal);
        Piece piece_right = getPiece(kingCol + 1,kingRow + colorVal);
        return piece_left != null && piece_left.isWhite != kingIsWhite && piece_left.name.equals("Pawn") && !(piece_left.col == col && piece_left.row == row) ||
                piece_right != null && piece_right.isWhite != kingIsWhite && piece_right.name.equals("Pawn") && !(piece_right.col == col && piece_right.row == row);
    }

    public int getTileNum(int col, int row) {
        return row * rows + col;
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
                            pieceList.add(new Rook(col, row, false));
                            break;
                        case 'p':
                            pieceList.add(new Pawn(col, row, false));
                            break;
                        case 'n':
                            pieceList.add(new Knight(col, row, false));
                            break;
                        case 'b':
                            pieceList.add(new Bishop(col, row, false));
                            break;
                        case 'q':
                            pieceList.add(new Queen(col, row, false));
                            break;
                        case 'k':
                            pieceList.add(new King(col, row, false));
                            break;
                        case 'R':
                            pieceList.add(new Rook(col, row, true));
                            break;
                        case 'P':
                            pieceList.add(new Pawn(col, row, true));
                            break;
                        case 'N':
                            pieceList.add(new Knight(col, row, true));
                            break;
                        case 'B':
                            pieceList.add(new Bishop(col, row, true));
                            break;
                        case 'Q':
                            pieceList.add(new Queen(col, row, true));
                            break;
                        case 'K':
                            pieceList.add(new King(col, row, true));
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
        if(colKingCheckMate != -1) {
            g2d.setColor(new Color(214,114,114,190));
            g2d.fillRect(colKingCheckMate * tileSize, rowKingCheckMate * tileSize, tileSize, tileSize);
        }
        if (selectedPiece != null) {
            for (int r = 0; r < rows; ++r) {
                for (int c = 0; c < cols; ++c) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setColor(new Color(90, 171, 93, 190));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
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
