package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

    public class MusicPlayer {
        private static final HashMap<String, Clip> soundClips = new HashMap<>();
        private static final Random rand = new Random();

        public static void initSound() {
            loadSound("music", "src/assets/sound/music.wav");
            loadSound("click", "src/assets/sound/click.wav");
            loadSound("plant", "src/assets/sound/plant.wav");
            for (int i = 0; i <= 1; i++) {
                loadSound("upgrade" + i, "src/assets/sound/upgrade" + i + ".wav");
                loadSound("cow" + i, "src/assets/sound/cow" + i + ".wav");
            }
            for (int i = 0; i <= 3; i++) {
                loadSound("move" + i, "src/assets/sound/move" + i + ".wav");
            }
        }

        private static void loadSound(String key, String path) {
            try {
                File soundPath = new File(path);
                if (soundPath.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    soundClips.put(key, clip);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void playMusic() {
            playSound("music");
        }

        private static void playSound(String key) {
            Clip clip = soundClips.get(key);
            if (clip != null) {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the clip if it's currently running
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.start(); // Start playing
            }
        }

        public static void playClick() {
            playSound("click");
        }

        public static void playPlant() {
            playSound("plant");
        }

        public static void playUpgrade() {
            playSound("upgrade" + rand.nextInt(2));
        }

        public static void playMove() {
            playSound("move" + rand.nextInt(4));
        }

        public static void playCow() {
            playSound("cow" + rand.nextInt(2));
        }
    }
