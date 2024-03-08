package model;

import javax.sound.sampled.*;
import java.io.File;
public class Sound {
    Clip clip;
    String nameFile[] = new String[8];
    public Sound() {
        nameFile[0] = "src/res/sound/game-start.wav";
        nameFile[1] = "src/res/sound/game-end.wav";
        nameFile[2] = "src/res/sound/move-self.wav";
        nameFile[3] = "src/res/sound/capture.wav";
        nameFile[4] = "src/res/sound/Castling.wav";
        nameFile[5] = "src/res/sound/Promotion.wav";
        nameFile[6] = "src/res/sound/OutOfBound.wav";
        nameFile[7] = "src/res/sound/Check.wav";
    }
    public void setFile(int i) {
        try {
            File file = new File(nameFile[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        }catch (Exception e) {

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
        this.clip.stop();
    }
    public void loop() {
        this.clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void playMusic(int i) {
        setFile(i);
        play();
    }
}
