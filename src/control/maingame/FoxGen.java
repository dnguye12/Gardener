package control.maingame;

import model.ModelGame;

/**
 * FoxGen est une classe qui hérite de Thread
 * Elle permet de générer des renards à intervalle régulier
 */
public class FoxGen extends Thread {
    private ModelGame game;
    private static final int DELAY = 16000;

    public FoxGen(ModelGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            // Ajoute un renard
            this.game.addFox();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
