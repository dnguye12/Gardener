package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

/**
* Classe permettant de gérer la musique et les effets sonores dans le jeu.
*/
    public class MusicPlayer {
        private static final HashMap<String, Clip> soundClips = new HashMap<>();
        private static final Random rand = new Random();

    /**
     * Initialise les sons utilisés dans le jeu.
     */
        public static void initSound() {
            loadSound("music", "src/assets/sound/music.wav");
            loadSound("click", "src/assets/sound/click.wav");
            loadSound("plant", "src/assets/sound/plant.wav");
            loadSound("egg", "src/assets/sound/egg.wav");
            for (int i = 0; i <= 1; i++) {
                loadSound("upgrade" + i, "src/assets/sound/upgrade" + i + ".wav");
                loadSound("cow" + i, "src/assets/sound/cow" + i + ".wav");
            }
            for (int i = 0; i <= 3; i++) {
                loadSound("move" + i, "src/assets/sound/move" + i + ".wav");
            }
            for(int i = 0; i <= 2; i++) {
                loadSound("chicken" + i, "src/assets/sound/chicken" + i + ".wav");
            }
            loadSound("pickup", "src/assets/sound/pickup.wav");
            loadSound("chickendie", "src/assets/sound/chickendie.wav");
        }

    /**
     * Charge un son spécifique à partir d'un chemin donné.
     *
     * @param key  La clé associée au son.
     * @param path Le chemin d'accès au fichier son.
     */
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
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.start();
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
        public static void playPickup() {
            playSound("pickup");
        }
        public static void playChicken() {
            playSound("chicken" + rand.nextInt(3));
        }
        public static void playEgg() {
            playSound("egg");
        }
        public static void playChickenDie() {
            playSound("chickendie");
        }
    }
