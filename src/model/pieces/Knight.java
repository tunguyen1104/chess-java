package model.pieces;
import java.awt.image.BufferedImage;

public class Knight extends Piece {

    public Knight(int col, int row, boolean isWhite) {
        super();
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
}
