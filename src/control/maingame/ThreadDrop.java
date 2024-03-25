package control.maingame;

import model.ModelDrop;

import java.util.HashMap;

public class ThreadDrop extends Thread {
    private HashMap<Integer, ModelDrop> drops;
    private static final int DELAY = 1000 / 60;

    public ThreadDrop(HashMap<Integer, ModelDrop> drops) {
        this.drops = drops;
    }

    @Override
    public void run() {
        while (true) {
            for (ModelDrop drop : this.drops.values()) {
                if(drop.getHasPlayed2()) {
                    continue;
                }
                drop.drop();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
