package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class BeautifyButton extends JButton {
    private boolean check_color;//nếu dang hover vào button -> true
    private Color color;
    private Color colorHover;
    private Color colorClick;
    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }
    public BeautifyButton(String title) {
        this.setText(title);
        setColor(new Color(79,88,104));
        colorHover = new Color(47, 53, 62);
        colorClick = new Color(135, 146, 166);
        this.setContentAreaFilled(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorHover);
                check_color = true;
            }
            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                check_color = false;
            }
            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
            }
            @Override
            public void mouseReleased(MouseEvent me) {
                if (check_color) {
                    setBackground(colorHover);
                } else {
                    setBackground(color);
                }
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        super.paintComponent(g);
    }
}