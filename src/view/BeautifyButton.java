package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class BeautifyButton extends JButton {
    private Color color;
    private Color colorHover;

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public BeautifyButton(String title) {
        this.setText(title);
        setColor(new Color(79, 88, 104));
        colorHover = new Color(135, 146, 166);
        this.setContentAreaFilled(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
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