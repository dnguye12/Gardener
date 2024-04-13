package control.animation;

import model.ModelChicken;
import model.ModelGame;

import java.util.HashMap;

/**
 * Gère l'animation des poules.
 */
public class AnimationChicken extends Thread{
    private static final int DELAY = 300;
    private HashMap<Integer, ModelChicken> chickens;

    public AnimationChicken(ModelGame game) {
        this.chickens = game.getChickens();
    }

    @Override
    public void run() {
        while(true) {
            for (ModelChicken chicken : this.chickens.values()) {
                chicken.nextAnimationState();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
