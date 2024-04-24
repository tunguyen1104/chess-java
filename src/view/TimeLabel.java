package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TimeLabel extends JLabel {
    private int seconds;
    private int minutes;
    public String seconds_string;
    public String minutes_string;

    public TimeLabel(int minute, int x, int y) {
        seconds = 0;
        minutes = minute;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        this.setText(minutes_string + ":" + seconds_string);
        this.setBounds(x, y, 500, 200);
        try {
            this.setFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("resources/fonts/JetBrainsMono-Bold.ttf")).deriveFont(Font.BOLD, 40));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (seconds > 0) {
                seconds = seconds - 1;
            } else if (seconds == 0 && minutes > 0) {
                minutes = minutes - 1;
                seconds = 59;
            }
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            setText(minutes_string + ":" + seconds_string);
        }
    });

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
