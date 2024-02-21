package control.maingame;

import model.ModelGame;

public class ThreadTime extends Thread {
    private ModelGame game;
    private static final int DELAY = 1000;

    public ThreadTime(ModelGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            this.game.setTimeLeft(this.game.getTimeLeft() - 1);
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}