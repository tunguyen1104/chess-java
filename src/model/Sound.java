package model;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {
    Clip clip;
    String nameFile[] = new String[9];

    public Sound() {
        nameFile[0] = "resources/sound/StartGame.wav";
        nameFile[1] = "resources/sound/EndGame.wav";
        nameFile[2] = "resources/sound/MoveSelf.wav";
        nameFile[3] = "resources/sound/Capture.wav";
        nameFile[4] = "resources/sound/Castling.wav";
        nameFile[5] = "resources/sound/Promotion.wav";
        nameFile[6] = "resources/sound/OutOfBound.wav";
        nameFile[7] = "resources/sound/Check.wav";
        nameFile[8] = "resources/sound/BackGround.wav";
    }

    public Sound(int x) {
    }

    public void setFile(int i) {
        try {
            File file = new File(nameFile[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        } catch (Exception e) {

        }
    }

    public void play() {
        try {
            if (clip != null) { // Kiểm tra xem clip đã được khởi tạo chưa
                clip.start();
            } else {
                System.out.println("Clip is null. Cannot play.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if(this.clip != null)
            this.clip.stop();
    }
    public void close() {
        if (this.clip != null) {
            this.clip.stop();
            this.clip.close();
            this.clip = null;
        }
    }
    public void loop() {
        this.clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void playMusic(int i) {
        if(nameFile[0] == null || i == 10) return;
        setFile(i);
        play();
    }
    public void playBackGround() {
        if(this.clip == null) {
            setFile(8);
            play();
            loop();
        } else {
            play();
            loop();
        }
    }
}
