package control.maingame;

import model.ModelDrop;
import model.ModelGame;

import java.util.HashMap;

public class ThreadDrop extends Thread {
    private ModelGame game;
    private static final int DELAY = 1000 / 60;

    public ThreadDrop(ModelGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            HashMap<Integer, ModelDrop> drops = game.getDrops();
            for (ModelDrop drop : drops.values()) {
                if(drop.getHasPlayed2()) {
                    drop.setDieTime(drop.getDieTime() - DELAY);
                    continue;
                }
                drop.drop();

                if(drop.getDieTime() <= 0) {
                    this.game.removeDrop(drop.getId());
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
