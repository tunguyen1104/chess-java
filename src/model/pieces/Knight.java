package model.pieces;
import model.BoardV2;

import java.awt.image.BufferedImage;

public class Knight extends Piece {

    public Knight(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.xPos = col * 80;
        this.yPos = row * 80;
        this.isWhite = isWhite;
        this.name = "Knight";
        this.sprite = sheet.getSubimage(3 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(sheetScale, sheetScale, BufferedImage.SCALE_SMOOTH);
        //(x,y,w,h)
        // getSubimage : cắt hình từ vị trí y = 3 * sheetScale, x = 0 || 1, ứng với tỉ lệ width = 1, height = 1
        //getScaledInstance là một phương thức chuyển đổi hình ảnh theo tỉ lệ mới ứng với hằng số co giãn mềm
    }
    @Override
    public boolean check_the_valid_moves_of_the_chess_pieces(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2 && (col < 8 && col >= 0 && row < 8 && row >= 0);
    }

    private int Positional_board[][]={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    public Knight(boolean iswhite)
    {
        this.isWhite=iswhite;
        this.id="k";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=3;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=3;
        }
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public Knight(boolean iswhite, int row, int col)
    {
        this.isWhite=iswhite;
        this.row=row;
        this.col=col;
        this.id="k";
        if(this.isWhite)
        {
            this.lct_in_image_Y=0;
            this.lct_in_image_X=3;
        }
        else
        {
            this.lct_in_image_Y=1;
            this.lct_in_image_X=3;
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
        int[] row_knight={-2,-2,-1,1,2,2,1,-1};
        int[] col_knight={-1,1,2,2,1,-1,-2,-2};
        for(int i=0;i<8;i++)
        {
            try {
                if(a.board1[this.row+row_knight[i]][this.col+col_knight[i]]==null||a.board1[this.row+row_knight[i]][this.col+col_knight[i]].isWhite()!=this.isWhite)
                {
                    enemy_piece=a.board1[this.row+row_knight[i]][this.col+col_knight[i]];
                    a.setKnight(this.isWhite,this.row+row_knight[i],this.col+col_knight[i]);;
                    a.board1[this.row][this.col]=null;
                    if(a.is_kingsafe(this.isWhite))
                    {
                        if(enemy_piece!=null)
                        {
                            list+=""+this.row+this.col+(this.row+row_knight[i])+(this.col+col_knight[i])+enemy_piece.getId();
                        }
                        else
                        {
                            list+=""+this.row+this.col+(this.row+row_knight[i])+(this.col+col_knight[i])+" ";
                        }
                    }
                    a.board1[this.row+row_knight[i]][this.col+col_knight[i]]=enemy_piece;
                    a.setKnight(this.isWhite,this.row,this.col);
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
