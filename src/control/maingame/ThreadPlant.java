package control.maingame;

import model.ModelGame;
import model.ModelPlant;

import java.util.ArrayList;

public class ThreadPlant extends Thread{
    private ModelGame game;
    private ArrayList<ModelPlant> plants;
    private static final int DELAY = 1000 / 60;

    public ThreadPlant(ModelGame game) {
        this.game = game;
        this.plants = game.getPlants();
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<ModelPlant> toRemove = new ArrayList<>();
            for (ModelPlant plant : this.plants) {
                plant.grow();
                if(!plant.isAlive()) {
                    toRemove.add(plant);
                    if(plant.isSelected()) {
                        plant.setSelected(false);
                        this.game.setSelected(null);
                    }
                }
                else if(plant.canBeHarvested()) {
                    this.game.addPlantsToHarvest(plant);
                }
            }
            this.plants.removeAll(toRemove);
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
