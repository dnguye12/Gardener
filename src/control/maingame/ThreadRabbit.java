package control.maingame;

import model.ModelGame;
import model.ModelRabbit;

import java.util.ArrayList;
import java.util.HashMap;

public class ThreadRabbit extends Thread{
    private ModelGame game;
    private HashMap<Integer, ModelRabbit> rabbits;
    private static final int DELAY = 1000 / 60;

    public ThreadRabbit(ModelGame game) {
        this.game = game;
        this.rabbits = game.getRabbits();
    }

    @Override
    public synchronized void run() {
        while (true) {
            ArrayList<Integer> toRemove = new ArrayList<>();
            for (ModelRabbit rabbit : this.rabbits.values()) {
                rabbit.move();
                rabbit.drop();
                if(rabbit.getStatus() == ModelRabbit.Status.EATING) {
                    rabbit.eat();
                }else {
                    rabbit.setDieTime(rabbit.getDieTime() - DELAY);
                }
                if(rabbit.getDieTime() <= 0 && rabbit.canBeRemoved()) {
                    toRemove.add(rabbit.getId());
                    if(rabbit.isSelected()) {
                        rabbit.setSelected(false);
                        this.game.setSelected(null);
                    }
                }
            }
            for (int id : toRemove) {
                this.rabbits.remove(id);
            }
            toRemove.clear();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
