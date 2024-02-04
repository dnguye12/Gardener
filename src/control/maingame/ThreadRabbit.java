package control.maingame;

import model.ModelRabbit;

import java.util.HashMap;

public class ThreadRabbit extends Thread{
    private HashMap<Integer, ModelRabbit> rabbits;
    private static final int DELAY = 1000 / 60;

    public ThreadRabbit(HashMap<Integer, ModelRabbit> rabbits) {
        this.rabbits = rabbits;
    }

    @Override
    public void run() {
        while (true) {
            for (ModelRabbit rabbit : this.rabbits.values()) {
                rabbit.move();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
