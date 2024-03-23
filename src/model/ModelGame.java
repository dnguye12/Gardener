package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;
import view.VueMainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModelGame {
    private ModelUnit selected;
    private String isBuying;
    private HashMap<Integer, ModelGardener> gardeners;
    private HashMap<Integer, ModelPlant> plants;
    private ArrayList<Integer> plantsToHarvest;
    private HashMap<Integer, ModelRabbit> rabbits;
    private HashMap<Integer, ModelObstacle> obstacles;

    private int money;
    private int score;
    private int timeLeft;
    private AStarPathfinder pathfinder;

    public ModelGame() {
        this.gardeners = new HashMap<>();

        this.plants = new HashMap<>();
        this.plantsToHarvest = new ArrayList<Integer>();

        this.rabbits = new HashMap<>();

        this.obstacles = new HashMap<>();
        this.initObstacles();

        this.selected = null;
        this.isBuying = "";

        this.money = 200;
        this.score = 0;
        this.timeLeft = 300;

        GridSystem grid = new GridSystem(this);
        this.pathfinder = new AStarPathfinder(grid.getWidth(), grid.getHeight(), grid.getWalkable());

        int helperx = (VueMainGame.LEFT_WIDTH - 50) / 2;
        int helpery = (VueMainGame.SCREEN_HEIGHT - 50) / 2;
        this.gardeners.put(0, new ModelGardener(0, new Point(helperx, helpery), new Point(helperx, helpery), this));
    }

    private void initObstacles() {
        Random rand = new Random();
        int helperx = VueMainGame.LEFT_WIDTH / GridSystem.OBSTACLE_SIZE - 2;
        int helpery = VueMainGame.SCREEN_HEIGHT / GridSystem.OBSTACLE_SIZE - 2;
        int x,y;
        for(int i = 1; i <= 20; i++) {
            while(true) {
                x = rand.nextInt(helperx) + 1;
                y = rand.nextInt(helpery) + 1;
                if(Math.abs(x - (VueMainGame.LEFT_WIDTH / GridSystem.OBSTACLE_SIZE) / 2) >= 5 && Math.abs(y - (VueMainGame.LEFT_WIDTH / GridSystem.OBSTACLE_SIZE) / 2) >= 5) {
                    break;
                }
            }
            this.obstacles.put(i, new ModelObstacle(i, new Point(x * GridSystem.OBSTACLE_SIZE, y * GridSystem.OBSTACLE_SIZE)));
        }
    }

    private void initObstacles2() {
        int helperx = (VueMainGame.LEFT_WIDTH - 50) / 2;
        int helpery = (VueMainGame.SCREEN_HEIGHT - 50) / 2 - 150;

        for(int i = 1; i <= 10; i++) {
            this.obstacles.put(i, new ModelObstacle(i, new Point(helperx + 50, helpery + i * 30)));
        }
    }

    public void setSelected(ModelUnit selected) {
        this.selected = selected;
    }

    public ModelUnit getSelected() {
        return this.selected;
    }

    public String getIsBuying() {
        return this.isBuying;
    }

    public void setIsBuying(String isBuying) {
        if(isBuying.equals("")) {
            this.isBuying = "";
        }else if(isBuying.equals("Gardener") &&  this.money >= 100)  {
            this.isBuying = isBuying;
        }
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
    public int getTimeLeft() {
        return this.timeLeft;
    }
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public AStarPathfinder getPathfinder() {
        return pathfinder;
    }

    public HashMap<Integer, ModelGardener> getGardeners() {
        return this.gardeners;
    }

    public void addGardener(ModelGardener g) {
        this.gardeners.put(g.getId(), g);
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
        int helperx = VueMainGame.LEFT_WIDTH - 50;
        int helpery = VueMainGame.SCREEN_HEIGHT - 50;

        // 0 = up, 1 = right, 2 = down, 3 = left
        int dir = rand.nextInt(4);

        int x,y;
        if(dir == 0) {
            x = rand.nextInt(helperx);
            y = 0;
        }else if(dir == 1) {
            x = helperx;
            y = rand.nextInt(helpery);
        }else if(dir == 2) {
            x = rand.nextInt(helperx);
            y = helpery;
        }else {
            x = 0;
            y = rand.nextInt(helpery);
        }
        Point point = new Point(x, y);
        int helper =  x <= 600 ? -1 : 1;
        int idr = IdGen.generateRabbitId();
        this.rabbits.put(idr, new ModelRabbit(idr, point, point, this, helper));
        MusicPlayer.playCow();
    }

    public HashMap<Integer, ModelObstacle> getObstacles() {
        return this.obstacles;
    }

    public void reset() {
        this.gardeners.clear();
        this.plants.clear();
        this.plantsToHarvest.clear();
        this.rabbits.clear();

        this.selected = null;
        this.isBuying = "";

        this.money = 10;
        this.score = 0;
        this.timeLeft = 300;
        int helperx = (VueMainGame.LEFT_WIDTH - 50) / 2;
        int helpery = (VueMainGame.SCREEN_HEIGHT - 50) / 2;
        this.gardeners.put(0, new ModelGardener(0, new Point(helperx, helpery), new Point(helperx, helpery), this));
    }
}
