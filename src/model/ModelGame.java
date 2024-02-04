package model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModelGame {
    private ModelUnit selected;
    private HashMap<Integer, ModelGardener> gardeners;
    private HashMap<Integer, ModelPlant> plants;
    private ArrayList<Integer> plantsToHarvest;
    private HashMap<Integer, ModelRabbit> rabbits;
    private int money;
    private int score;

    public ModelGame() {
        this.gardeners = new HashMap<>();
        this.gardeners.put(1, new ModelGardener(1, new Point(100, 100), new Point(100, 100), this));
        //this.gardeners.put(2, new ModelGardener(2, new Point(400, 400), new Point(400, 400), this));

        this.plants = new HashMap<>();
        this.plantsToHarvest = new ArrayList<Integer>();

        this.rabbits = new HashMap<>();

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

    public HashMap<Integer, ModelGardener> getGardeners() {
        return this.gardeners;
    }

    public HashMap<Integer, ModelPlant> getPlants() {
        return this.plants;
    }
    public void removePlant(int id) {
        this.plants.remove(id);
    }
    public ArrayList<Integer> getPlantsToHarvest() {
        return this.plantsToHarvest;
    }

    public void addPlantsToHarvest(ModelPlant plant) {
        int id = plant.getId();
        if(this.plantsToHarvest.contains(id)) {
            return;
        }
        this.plantsToHarvest.add(id);
    }

    public void setPlantsToHarvest(ArrayList<Integer> plantsToHarvest) {
        this.plantsToHarvest = plantsToHarvest;
    }

    public void addPlant(ModelPlant plant) {
        this.plants.putIfAbsent(plant.getId(), plant);
    }

    public HashMap<Integer, ModelRabbit> getRabbits() {
        return this.rabbits;
    }
    public void addRabbit() {
        Random rand = new Random();

        // 0 = up, 1 = right, 2 = down, 3 = left
        int dir = rand.nextInt(4);
        int x,y;
        if(dir == 0) {
            x = rand.nextInt(1150);
            y = 0;
        }else if(dir == 1) {
            x = 1150;
            y = rand.nextInt(850);
        }else if(dir == 2) {
            x = rand.nextInt(1150);
            y = 850;
        }else {
            x = 0;
            y = rand.nextInt(850);
        }
        Point point = new Point(x, y);
        int idr = IdGen.generateRabbitId();
        this.rabbits.put(idr, new ModelRabbit(idr, point, point, this));
    }
}
