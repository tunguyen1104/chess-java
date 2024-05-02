package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.BoardReview;
import view.Menu;
import view.Review;

public class ListenerReview extends MouseAdapter{
    private Review review;
    private BoardReview board;
    public ListenerReview(Review review, BoardReview board) {
        this.review = review;
        this.board = board;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(review.getRotate())) {
            board.rotateBoard(true);
            review.repaint();
        } else if(e.getSource().equals(review.getFirst_normal_button())) {
            board.handle_first_button();
        } else if(e.getSource().equals(review.getNext_normal_button())) {
            board.handle_next_button();
        } else if(e.getSource().equals(review.getLast_normal_button())) {
            board.handle_last_button();
        } else if(e.getSource().equals(review.getPrevious_normal_button())) {
            board.handle_previous_button();
        } else if(e.getSource().equals(review.getBack_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "history");
        } else if(e.getSource().equals(review.getHome_normal_button())) {
            Menu.cardLayout.show(Menu.panelCardLayout, "menu");
        }
    }
}
