package control.maingame;

import model.ModelGame;

public class RabbitGen extends Thread{
    private ModelGame game;
    private static final int DELAY = 5000;

    public RabbitGen(ModelGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            this.game.addRabbit();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
