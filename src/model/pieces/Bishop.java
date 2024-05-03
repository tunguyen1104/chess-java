package model.pieces;

import model.Board;
import model.BoardV2;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {

    public Bishop(int col, int row, boolean isWhite) {
        super();
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "Bishop";
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row) && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }
    @Override
    public boolean moveCollidesWithPiece(Board board, int col,int row) {
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
        return false;
    }

    private int Positional_board[][]={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    public Bishop(boolean iswhite)
    {
        this.isWhite=iswhite;
        this.id="b";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=2;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=2;
        }
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public Bishop(boolean iswhite, int row, int col)
    {
        this.isWhite=iswhite;
        this.row=row;
        this.col=col;
        this.id="b";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=2;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=2;
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
    public String move(BoardV2 a)
    {
        String list="";
        Piece enemy_piece=new Piece();
        int bishop_row[] = {-1,1,1,-1};
        int bishop_col[] = {1,1,-1,-1};
        for(int i=0;i<4;i++)
        {
            int cnt=1;
            try
            {
                while(a.board1[this.row+bishop_row[i]*cnt][this.col+bishop_col[i]*cnt]==null)
                {
                    a.setBishop(this.isWhite,this.row+bishop_row[i]*cnt,this.col+bishop_col[i]*cnt);
                    a.board1[this.row][this.col]=null;
                    if(a.is_kingsafe(this.isWhite))
                    {
                        list+=""+this.row+this.col+(this.row+bishop_row[i]*cnt)+(this.col+bishop_col[i]*cnt)+" ";
                    }
                    a.setBishop(this.isWhite,this.row,this.col);
                    a.board1[this.row+bishop_row[i]*cnt][this.col+bishop_col[i]*cnt] = null;
                    cnt++;
                }
                if(a.board1[this.row+bishop_row[i]*cnt][this.col+bishop_col[i]*cnt].isWhite()!=this.isWhite)
                {
                    enemy_piece=a.board1[this.row+bishop_row[i]*cnt][this.col+bishop_col[i]*cnt];
                    a.setBishop(this.isWhite,this.row+bishop_row[i]*cnt,this.col+bishop_col[i]*cnt);
                    a.board1[this.row][this.col]=null;
                    if(a.is_kingsafe(this.isWhite))
                    {
                        list+=""+this.row+this.col+(this.row+bishop_row[i]*cnt)+(this.col+bishop_col[i]*cnt)+enemy_piece.getId();
                    }
                    a.setBishop(this.isWhite,this.row,this.col);
                    a.board1[this.row+bishop_row[i]*cnt][this.col+bishop_col[i]*cnt] = enemy_piece;
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
        if(!a.is_kingsafe(this.isWhite)) kqua=-300;
        a.setKing_location(temp, this.isWhite);
        return kqua/2;
    }
}
