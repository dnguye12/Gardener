package model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ModelGame {
    private ModelUnit selected;
    private ArrayList<ModelGardener> gardeners;
    private ArrayList<ModelPlant> plants;
    private ArrayList<ModelPlant> plantsToHarvest;
    private ArrayList<ModelRabbit> rabbits;
    private int money;
    private int score;

    public ModelGame() {
        this.gardeners = new ArrayList<ModelGardener>();
        this.gardeners.add(new ModelGardener(1, new Point(100, 100), new Point(100, 100), this));
        this.gardeners.add(new ModelGardener(2, new Point(400, 400), new Point(400, 400), this));

        this.plants = new ArrayList<ModelPlant>();
        this.plantsToHarvest = new ArrayList<ModelPlant>();

        this.rabbits = new ArrayList<ModelRabbit>();

        this.selected = null;
        this.money = 10;
        this.score = 0;
    }

    public void setSelected(ModelUnit selected) {
        this.selected = selected;
    }

    public ModelUnit getSelected() {
        return this.selected;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<ModelGardener> getGardeners() {
        return this.gardeners;
    }

    public ArrayList<ModelPlant> getPlants() {
        return this.plants;
    }
    public void removePlant(int id) {
        for(ModelPlant p : this.plants) {
            if(p.getId() == id) {
                this.plants.remove(p);
                return;
            }
        }
    }
    public ArrayList<ModelPlant> getPlantsToHarvest() {
        return this.plantsToHarvest;
    }

    public void addPlantsToHarvest(ModelPlant plant) {
        int id = plant.getId();
        for(ModelPlant p : this.plantsToHarvest) {
            if(p.getId() == id) {
                return;
            }
        }
        this.plantsToHarvest.add(plant);
    }

    public void setPlantsToHarvest(ArrayList<ModelPlant> plantsToHarvest) {
        this.plantsToHarvest = plantsToHarvest;
    }

    public void addPlant(ModelPlant plant) {
        this.plants.add(plant);
    }

    public ArrayList<ModelRabbit> getRabbits() {
        return this.rabbits;
    }
    public void addRabbit() {
        Random rand = new Random();

        // 0 = up, 1 = right, 2 = down, 3 = left
        int dir = rand.nextInt(4);
        int x,y;
        if(dir == 0) {
            x = rand.nextInt(1200);
            y = 0;
        }else if(dir == 1) {
            x = 1200;
            y = rand.nextInt(900);
        }else if(dir == 2) {
            x = rand.nextInt(1200);
            y = 900;
        }else {
            x = 0;
            y = rand.nextInt(900);
        }
        Point point = new Point(x, y);
        this.rabbits.add(new ModelRabbit(IdGen.generateRabbitId(), point, point, this));
    }
}
