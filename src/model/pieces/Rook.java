package model.pieces;

import model.Board;
import model.BoardV2;

import java.awt.image.BufferedImage;
public class Rook extends Piece {

    public Rook(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "Rook";
        this.sprite = sheet.getSubimage(4 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return (this.col == col || this.row == row) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    @Override
    public boolean moveCollidesWithPiece(Board board, int col, int row){
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

        return false;
    }

    private int Positional_board[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    public Rook(boolean iswhite)
    {
        this.isWhite=iswhite;
        this.id="r";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=4;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=4;
        }
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public Rook(boolean iswhite, int row, int col)
    {
        this.isWhite=iswhite;
        this.row=row;
        this.col=col;
        this.id="r";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=4;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=4;
        }
    }
    public int getLct_in_image_X() {
        return lct_in_image_X;
    }
    public int getLct_in_image_Y() {
        return lct_in_image_Y;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public String getId() {
        return this.id;
    }
    public boolean isWhite() {
        return this.isWhite;
    }
    public String move(BoardV2 a) {
        String list="";
        Piece enemy_piece=new Piece();
        int[] row_rook = {0,-1,0,1};
        int[] col_rook = {1,0,-1,0};
        for(int i=0;i<4;i++)
        {
            int cnt=1;
            try
            {
                while(a.board1[this.row+row_rook[i]*cnt][this.col+col_rook[i]*cnt]==null)
                {
                    a.setRook(this.isWhite,this.row+row_rook[i]*cnt,this.col+col_rook[i]*cnt);
                    a.board1[this.row][this.col]=null;
                    if(a.is_kingsafe(this.isWhite))
                    {
                        list+=""+this.row+this.col+(this.row+row_rook[i]*cnt)+(this.col+col_rook[i]*cnt)+" ";
                    }
                    a.setRook(this.isWhite,this.row,this.col);
                    a.board1[this.row+row_rook[i]*cnt][this.col+col_rook[i]*cnt] = null;
                    cnt++;
                }
                if(a.board1[this.row+row_rook[i]*cnt][this.col+col_rook[i]*cnt].isWhite()!=this.isWhite)
                {
                    enemy_piece=a.board1[this.row+row_rook[i]*cnt][this.col+col_rook[i]*cnt];
                    a.setRook(this.isWhite,this.row+row_rook[i]*cnt,this.col+col_rook[i]*cnt);
                    a.board1[this.row][this.col]=null;
                    if(a.is_kingsafe(this.isWhite))
                    {
                        list+=""+this.row+this.col+(this.row+row_rook[i]*cnt)+(this.col+col_rook[i]*cnt)+enemy_piece.getId();
                    }
                    a.setRook(this.isWhite,this.row,this.col);
                    a.board1[this.row+row_rook[i]*cnt][this.col+col_rook[i]*cnt] = enemy_piece;
                }
            }
            catch(Exception e) {};
        }
        return list;
    }
    public int rating_positional()
    {
        if(this.isWhite==true)
            return this.Positional_board[this.row][this.col];
        else
            return this.Positional_board[7-this.row][7-this.col];
    }
    public int rating_attacked(BoardV2 a)
    {
        int temp=0;
        int kqua=0;
        if(this.isWhite==true) temp=a.getKing_location_w();
        else temp=a.getKing_location_b();
        int my_lct=this.row*8+this.col;
        a.setKing_location(my_lct, this.isWhite);
        if(!a.is_kingsafe(this.isWhite)) kqua=-500;
        a.setKing_location(temp, this.isWhite);
        return kqua/2;
    }
}
