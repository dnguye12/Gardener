package control.maingame;

import model.ModelGame;

// Ajouter un lapin chaque 8 secondes
public class RabbitGen extends Thread{
    private ModelGame game;
    private static final int DELAY = 8000;

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
