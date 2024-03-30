package control.maingame;

import model.ModelGame;
import model.ModelPlant;

import java.util.ArrayList;
import java.util.HashMap;


public class ThreadPlant extends Thread{
    private ModelGame game;
    private HashMap<Integer, ModelPlant> plants;
    private static final int DELAY = 1000 / 30;

    public ThreadPlant(ModelGame game) {
        this.game = game;
        this.plants = game.getPlants();
    }

    @Override
    public synchronized void run() {
        while (true) {
            ArrayList<Integer> toRemove = new ArrayList<>();
            for (ModelPlant plant : this.plants.values()) {
                plant.grow();
                if(!plant.isAlive()) {
                    toRemove.add(plant.getId());
                    if(plant.isSelected()) {
                        plant.setSelected(false);
                        this.game.setSelected(null);
                    }
                }
                else if(plant.canBeHarvested()) {
                    this.game.addPlantsToHarvest(plant);
                }
            }
            for (int id : toRemove) {
                this.game.removePlant(id);
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
