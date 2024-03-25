package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelGardener extends ModelUnit{
    private Status status;
    public static final int SPEED = 5;
    private final int RADIUS = 100;
    private Direction direction;
    private int animationState;
    private ModelGame game;
    private int promoteState;
    private AStarPathfinder pathfinder;
    private ArrayList<Point> currentPath;
    public enum Status {
        IDLING("Idling"),
        MOVING("Moving");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public ModelGardener(int id, Point position, Point dest, ModelGame game) {
        super(id, position, dest);
        this.status = Status.IDLING;
        this.direction = new Direction();
        this.animationState = 0;
        this.game = game;
        this.promoteState = 0;
        this.pathfinder = game.getPathfinder();
        this.currentPath = new ArrayList<>();
    }
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRadius() {
        return this.RADIUS + this.promoteState * 25;
    }
    public int getSpeed() {
        return this.SPEED + this.promoteState;
    }
    public void setPromoteState(int n)  {
        this.promoteState = n;
    }
    public int getPromoteState() {
        return this.promoteState;
    }

    public int getDirection() {
        return this.direction.getDirection();
    }

    public int getAnimationState() {
        return this.animationState;
    }

    public void setAnimationState(int animationState) {
        this.animationState = animationState;
    }

    public void nextAnimationState() {
        this.animationState = 1 - this.animationState;
    }

    @Override
    public void setDest(Point dest) {
        HashMap<Integer, ModelObstacle> obs = this.game.getObstacles();
        for(ModelObstacle ob : obs.values()) {
            if(ob.getPosition().distance(dest) <= GridSystem.OBSTACLE_SIZE) {
                return;
            }
        }
        this.dest = dest;
        MusicPlayer.playMove();
        this.pathfinder.findPathAsync(this.position, this.dest).thenAccept(path -> {
            this.currentPath = path;
        });
    }

    public String getAnimation() {
        String helper = this.direction.getDirection() == 1 ? "right" : "left";
        if(this.status == Status.MOVING) {
            return "src/assets/maingame/animation/gardener/move" + helper + this.animationState + ".png";
        }else {
            return "src/assets/maingame/animation/gardener/idle" + this.animationState + ".png";
        }
    }

    public void move() {
        int dx = this.dest.x - this.position.x;
        int dy = this.dest.y - this.position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if(distance <= SPEED || this.currentPath.size() == 0) {
            this.position = new Point(this.dest);
            this.status = Status.IDLING;
            this.currentPath.clear();
        }else {
            Point helper = this.currentPath.get(0);
            helper = new Point(helper.x * GridSystem.CELL_SIZE, helper.y * GridSystem.CELL_SIZE);

            dx = helper.x - this.position.x;
            dy = helper.y - this.position.y;
            distance = Math.sqrt(dx * dx + dy * dy);
            if(distance <= SPEED) {
                this.position = new Point(helper);
                this.currentPath.remove(0);
            }else {
                double stepX = (dx / distance) * SPEED;
                double stepY = (dy / distance) * SPEED;
                this.direction.setDirection(this.position, helper);

                this.position = new Point((int) (this.position.x + stepX), (int) (this.position.y + stepY));
            }
            this.status = Status.MOVING;
        }
    }

    public void plant() {
        if(this.game.getMoney() >= 10) {
            this.game.setMoney(this.game.getMoney() - 10);
            this.game.addPlant(new ModelPlant(IdGen.generatePlantId(), this.position, ModelPlant.PlantType.randomType(), this.game));
        }
    }

    public ArrayList<Integer> plantsNear() {
        HashMap<Integer, ModelPlant> plants = this.game.getPlants();
        ArrayList<Integer> plantsToHarvest = this.game.getPlantsToHarvest();
        ArrayList<Integer> helper = new ArrayList<Integer>();

        for(int id : plantsToHarvest) {
            ModelPlant plant = plants.get(id);
            if (plant != null && this.position.distance(plant.getPosition()) <= this.getRadius()) {
                helper.add(plant.getId());
            }
        }
        return helper;
    }

    public void harvest() {
        ArrayList<Integer> helper = this.plantsNear();
        for(int id : helper) {
            ModelPlant plant = this.game.getPlants().get(id);
            if(plant != null) {
            //int money = plant.getMoney();
            //this.game.setMoney(this.game.getMoney() + money);
            //this.game.setScore(this.game.getScore() + money);
                ModelDrop drop = new ModelPlantDrop(IdGen.generateDropId(), plant.getPosition(), plant);
                this.game.addDrop(drop);
                this.game.removePlant(id);
            }
        }
        ArrayList<Integer> plantsToHarvest = this.game.getPlantsToHarvest();
        plantsToHarvest.removeAll(helper);
        this.game.setPlantsToHarvest(plantsToHarvest);
    }

    public void upgrade() {
        if(this.game.getMoney() >= 25 && this.getPromoteState() < 5) {
            this.game.setMoney(this.game.getMoney() - 25);
            this.setPromoteState(this.getPromoteState() + 1);
        }
    }
}
