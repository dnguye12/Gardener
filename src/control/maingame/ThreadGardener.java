package control.maingame;

import model.ModelGardener;

import java.util.HashMap;

// Thread pour faire bouger les jardiniers
public class ThreadGardener extends Thread {
    private HashMap<Integer, ModelGardener> gardeners;
    private static final int DELAY = 1000 / 60;

    public ThreadGardener(HashMap<Integer, ModelGardener> gardeners) {
        this.gardeners = gardeners;
    }

    @Override
    public void run() {
        while (true) {
            for (ModelGardener gardener : this.gardeners.values()) {
                gardener.move();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
