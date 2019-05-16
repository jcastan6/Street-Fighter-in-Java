package GameFiles;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound extends Thread{
    private String filename;
    private boolean done;
    Clip clip;
    public Sound(String filename){
        this.filename = filename;
        done = false;
    }

    public synchronized void run() {
        try {
            File file = new File("FightingGame/Resources/Sounds/"+ filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run(int times){
        try {
            File file = new File("FightingGame/Resources/Sounds/"+ filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-3.0f);
            clip.start();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void stopMusic(){
        clip.stop();
    }

    public boolean isDone(){
        return false;
    }

}
