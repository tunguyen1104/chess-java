package model.pieces;

import model.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Queen";
        this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale,BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return ((Math.abs(this.col - col) == Math.abs(this.row - row)) || (this.col == col || this.row == row)) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    @Override
    public boolean moveCollidesWithPiece(int col,int row) {
        if(this.col == col || this.row == row) {
            //up
            if(this.row > row) {
                for(int r = this.row - 1;r > row; --r){
                    if(board.getPiece(this.col,r) != null) {
                        return true;
                    }
                }
            }
            //down
            if(this.row < row){
                for(int r = this.row + 1;r < row; ++r){
                    if(board.getPiece(this.col,r) != null) {
                        return true;
                    }
                }
            }
            //left
            if(this.col > col) {
                for(int c = this.col - 1; c > col; --c) {
                    if(board.getPiece(c,this.row) != null) {
                        return true;
                    }
                }
            }
            //right
            if(this.col < col) {
                for(int c = this.col + 1; c < col; ++c) {
                    if(board.getPiece(c,this.row) != null) {
                        return true;
                    }
                }
            }
        }
        else {
            //up left
            if(this.col > col && this.row > row) {
                for(int i = 1;i < Math.abs(this.col - col); ++i) {
                    if(board.getPiece(this.col - i,this.row - i) != null) return true;
                }
            }
            //up right
            if(this.col < col && this.row > row) {
                for(int i = 1;i < Math.abs(this.col - col); ++i) {
                    if(board.getPiece(this.col + i,this.row - i) != null) return true;
                }
            }
            //down left
            if(this.col > col && this.row < row) {
                for(int i = 1;i < Math.abs(this.col - col); ++i) {
                    if(board.getPiece(this.col - i,this.row + i) != null) return true;
                }
            }
            //down right
            if(this.col < col && this.row < row) {
                for(int i = 1;i < Math.abs(this.col - col); ++i) {
                    if(board.getPiece(this.col + i,this.row + i) != null) return true;
                }
            }
        }
        return false;
    }
}
