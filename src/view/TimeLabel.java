package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimeLabel extends JLabel {
    private int seconds;
    private int minutes;
    public String seconds_string;
    public String minutes_string;

    public TimeLabel(int minute) {
        seconds = 0;
        minutes = minute;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        this.setText(minutes_string + ":" + seconds_string);
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
