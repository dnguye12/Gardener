package control.maingame;

import model.ModelFox;
import model.ModelGame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Thread pour gérer les renards
 */
public class ThreadFox extends Thread{
    private ModelGame game;
    private HashMap<Integer, ModelFox> foxes;

    private static final int DELAY = 1000 / 60;

    public ThreadFox(ModelGame game) {
        this.game = game;
        this.foxes = game.getFoxes();
    }

    @Override
    public synchronized void run() {
        while(true) {
            ArrayList<Integer> toRemove = new ArrayList<>();
            for(ModelFox fox : this.foxes.values()) {
                fox.move();
                if(fox.getStatus() == ModelFox.Status.EATING) {
                    fox.eat();
                }else {
                    fox.setDieTime(fox.getDieTime() - DELAY);
                }
                if(fox.getDieTime() <= 0 && fox.canBeRemoved()) {
                    toRemove.add(fox.getId());
                    if(fox.isSelected()) {
                        fox.setSelected(false);
                        this.game.setSelected(null);
                    }
                }
            }
            for(int id : toRemove) {
                this.foxes.remove(id);
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
