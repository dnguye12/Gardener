package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.Random;

public class MusicPlayer {
    private static Clip musicClip;
    private static Clip effectClip;
    private static Random rand = new Random();
    private static int helper;

    public static void playMusic() {
        try {
            File musicPath = new File("src/assets/sound/music.wav");
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playEffect(String path) {
        try {
            if(effectClip != null && effectClip.isRunning()) {
                effectClip.stop();
            }
            File effectPath = new File(path);
            if (effectPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(effectPath);
                effectClip = AudioSystem.getClip();
                effectClip.open(audioInput);
                effectClip.addLineListener(e -> {
                    if(e.getType() == LineEvent.Type.STOP) {
                        effectClip.close();
                    }
                });
                effectClip.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playClick() {
        playEffect("src/assets/sound/click.wav");
    }

    public static void playPlant() {
        playEffect("src/assets/sound/plant.wav");
    }

    public static void playUpgrade() {
        helper = rand.nextInt(2);
        playEffect("src/assets/sound/upgrade" + helper + ".wav");
    }

    public static void playMove() {
        helper = rand.nextInt(4);
        playEffect("src/assets/sound/move" + helper + ".wav");
    }

    public static void playCow() {
        helper = rand.nextInt(2);
        playEffect("src/assets/sound/cow" + helper + ".wav");
    }
}
