package control.maingame;

import model.ModelGame;

public class FoxGen extends Thread {
    private ModelGame game;
    private static final int DELAY = 16000;

    public FoxGen(ModelGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            this.game.addFox();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
