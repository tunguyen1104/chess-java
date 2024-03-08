package model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pieces.*;

import javax.swing.*;

public class Listener extends MouseAdapter {
    private GamePVP board;
    public Sound sound = new Sound();
    private boolean isTurn = true;// mặc định quân trắng đi trước
    public boolean isEnd = false;// end because King die
    public Listener(GamePVP board) {
        this.board = board;
    }
    @Override
    public void mousePressed(MouseEvent e) {//Được triệu hồi khi nút chuột đã được nhấn trên một thành phần.
        if(isEnd) return;
        if ("00:00".equals(board.timeLabelWhite.getText())) {
            sound.playMusic(1);
            board.stop_white();
            board.stop_black();
            noti_end_game("White");
        } else if ("00:00".equals(board.timeLabelBlack.getText())) {
            sound.playMusic(1);
            board.stop_white();
            board.stop_black();
            noti_end_game("Black");
        }
        if(board.selectedPiece != null) {
            if(isTurn != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
                return;
            }
        }
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) { //khi người dùng giữ nút chuột và di chuyển chuột trên màn hình.
        if(isEnd) return;
        if(board.selectedPiece != null) {
            if(isTurn != board.selectedPiece.isWhite) {
                board.selectedPiece = null;
            }
            else {
                //đặt tọa độ x của đối tượng selectedPiece để nằm chính giữa ô được kéo
                board.selectedPiece.xPos =  e.getX() - board.tileSize / 2;
                board.selectedPiece.yPos =  e.getY() - board.tileSize / 2;
                board.repaint();//load lại
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {//trieu hoi khi nút chuột đã được giải phóng trên một thành phần.
        int col = e.getX() / board.tileSize;//tính toán ra cột và hàng mà chuột được nhả
        int row = e.getY() / board.tileSize;
        if(board.selectedPiece != null){
            Move move = new Move(board,board.selectedPiece,col,row);// lưu vị trí quân cơ mới cho move.capture và cũ cho move.piece
            if(board.isValidMove(move)){
                board.makeMove(move);// nếu ví trí mới có cờ khác màu thì cập nhật move.piece và xoá quân cờ move.capture đi
                if(isTurn == true) {
                    board.start_white();
                    board.stop_black();
                    isTurn = false;
                }
                else{
                    board.start_black();
                    board.stop_white();
                    isTurn = true;
                }
                board.paint_old_new(move.getOldCol(),move.getOldRow(),move.getNewCol(),move.getNewRow());
                sound.playMusic(2);
            }
            else {// nếu 2 quân cờ cùng màu thì sửa lại xPos,yPos về vị trí ban đầu (đã thay đổi ở dòng 26,27)
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;//để hủy bỏ việc chọn quân cờ
        board.repaint();//vẽ lại bảng sau khi đã di chuyển đối tượng selectedPiece để cập nhật giao diện.
        if (board.findKing(true) == null) {
            isEnd = true;
            sound.playMusic(1);
            board.stop_white();
            board.stop_black();
            noti_end_game("Black");
        }
        else if (board.findKing(false) == null) {
            isEnd = true;
            sound.playMusic(1);
            board.stop_white();
            board.stop_black();
            noti_end_game("White");
        }
    }
    public void noti_end_game(String name_win) {
        Object[] options = { "New Game", "Home", "Review"};
        int select = JOptionPane.showOptionDialog(null,name_win + " Win","Notification",JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch(select) {
            case 0:
                board.frame.dispose();
                new GameOptions();
                break;
            case 1:
                board.frame.dispose();
                new Menu();
                break;
            case 2:
                board.frame.dispose();
                break;
        }
    }
}

