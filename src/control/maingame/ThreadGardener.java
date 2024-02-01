package control.maingame;

import model.ModelGardener;

import java.util.ArrayList;

public class ThreadGardener extends Thread {
    private ArrayList<ModelGardener> gardeners;
    private static final int DELAY = 1000 / 60;

    public ThreadGardener(ArrayList<ModelGardener> gardeners) {
        this.gardeners = gardeners;
    }

    @Override
    public void run() {
        while (true) {
            for (ModelGardener gardener : this.gardeners) {
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
