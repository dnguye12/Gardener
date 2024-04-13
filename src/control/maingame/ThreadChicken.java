package control.maingame;

import model.ModelChicken;
import model.ModelGame;

import java.util.HashMap;

/**
 * Gère les déplacements et les actions des poules.
 */
public class ThreadChicken extends Thread {
    private ModelGame game;
    private HashMap<Integer, ModelChicken> chickens;
    private static final int DELAY = 1000 / 60;

    public ThreadChicken(ModelGame game) {
        this.game = game;
        this.chickens = game.getChickens();
    }

    @Override
    public synchronized void run() {
        while (true) {
            for (ModelChicken chicken : this.chickens.values()) {
                chicken.move(); // Déplacer la poule
                chicken.layEgg(); // Pondre un oeuf si possible
            }
            this.game.removeChicken();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
