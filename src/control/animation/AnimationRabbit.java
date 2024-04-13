package control.animation;

import model.ModelGame;
import model.ModelRabbit;

import java.util.HashMap;

/**
 * Gère l'animation des lapins.
 */
public class AnimationRabbit extends Thread {
    private static final int DELAY = 500;
    private HashMap<Integer, ModelRabbit> rabbits;

    public AnimationRabbit(ModelGame game) {
        this.rabbits = game.getRabbits();
    }

    @Override
    public void run() {
        while(true) {
            for (ModelRabbit rabbit : this.rabbits.values()) {
                rabbit.nextAnimationState();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
