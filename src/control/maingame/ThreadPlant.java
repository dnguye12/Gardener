package control.maingame;

import model.ModelPlant;

import java.util.ArrayList;

public class ThreadPlant extends Thread{
    private ArrayList<ModelPlant> plants;

    private static final int DELAY = 1000 / 60;

    public ThreadPlant(ArrayList<ModelPlant> plants) {
        this.plants = plants;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<ModelPlant> toRemove = new ArrayList<>();
            for (ModelPlant plant : this.plants) {
                plant.grow();
                if(!plant.isAlive()) {
                    toRemove.add(plant);
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
