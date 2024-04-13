package control.animation;

import model.ModelGame;
import model.ModelGardener;

import java.util.HashMap;

/**
 * Gère l'animation des jardiniers.
 */
public class AnimationGardener extends Thread{
    private static final int DELAY = 500;
    private HashMap<Integer, ModelGardener> gardeners;

    public AnimationGardener(ModelGame game) {
        this.gardeners = game.getGardeners();
    }

    @Override
    public void run() {
        while(true) {
            for (ModelGardener g : this.gardeners.values()) {
                g.nextAnimationState();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
