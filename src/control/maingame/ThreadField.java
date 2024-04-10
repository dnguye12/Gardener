package control.maingame;

import model.ModelField;
import model.ModelFieldCell;
import model.ModelGame;

public class ThreadField extends Thread{
    private ModelGame game;
    private ModelField field;
    private static final int DELAY = 30000;

    public ThreadField(ModelGame game) {
        this.game = game;
        this.field = this.game.getField();
    }

    @Override
    public void run() {
        while (true) {
            this.field.growGrass();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
