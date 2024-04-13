package control.animation;

import model.ModelFox;
import model.ModelGame;

import java.util.HashMap;

/**
 * Gère l'animation des renards.
 */
public class AnimationFox extends Thread {
    private static final int DELAY = 500;
    private HashMap<Integer, ModelFox> foxes;

    public AnimationFox(ModelGame game) {
        this.foxes = game.getFoxes();
    }

    @Override
    public void run() {
        while(true) {
            for (ModelFox fox : this.foxes.values()) {
                fox.nextAnimationState();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
