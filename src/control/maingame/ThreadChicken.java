package control.maingame;

import model.ModelChicken;
import model.ModelGame;

import java.util.HashMap;

public class ThreadChicken extends Thread {
    private ModelGame game;
    private HashMap<Integer, ModelChicken> chickens;
    private static final int DELAY = 1000 / 60;

    public ThreadChicken(ModelGame game) {
        this.game = game;
        this.chickens = game.getChickens();
    }

    @Override
    public synchronized  void run() {
        while (true) {
            for (ModelChicken chicken : this.chickens.values()) {
                chicken.move();
                chicken.layEgg();
                if(chicken.getStatus() == ModelChicken.Status.EATING) {
                    chicken.eat();
                }
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
