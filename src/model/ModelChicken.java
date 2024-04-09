package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModelChicken extends ModelUnit{
    private final int SPEED = 3;
    private final int MEMSPAN = 3000;
    private ModelGame game;
    private long lastStateChangeTime;
    private long lastMoveTime;
    private boolean foundPlant;
    private ModelPlant eatingPlant;
    private Direction direction;
    private int animationState;
    private int idleState;
    private int moveState;
    private AStarPathfinder pathfinder;
    private ArrayList<Point> currentPath;
    private Status status;
    public enum Status {
        IDLING("Idling"),
        MOVING("Moving"),
        EATING("Eating");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public ModelChicken(int id, Point position, Point dest, ModelGame game) {
        super(id, position, dest);
        this.game = game;
        this.status = Status.IDLING;

        this.lastMoveTime = System.currentTimeMillis();
        this.lastStateChangeTime = System.currentTimeMillis();

        this.foundPlant = false;
        this.eatingPlant = null;

        this.direction = new Direction();
        this.animationState = 0;
        this.idleState = 0;
        this.moveState = 0;

        this.pathfinder = this.game.getPathfinder();
        this.currentPath = new ArrayList<>();
    }
    public void nextAnimationState() {
        if(this.status == Status.IDLING) {
            this.idleState = 1 - this.idleState;
            this.moveState = 0;
        }else {
            if(this.moveState == 3) {
                this.moveState = 0;
            }else {
                this.moveState++;
            }
            this.idleState = 0;
        }
    }

    public String getAnimation() {
        String helper = this.direction.getDirection() == 1 ? "right" : "left";
        if(this.status == Status.MOVING) {
            return "src/assets/maingame/animation/chicken/move" + helper + this.moveState + ".png";
        }else {
            return "src/assets/maingame/animation/chicken/idle" + helper + this.idleState + ".png";
        }
    }

    public Status getStatus() {
        return status;
    }

    private ModelPlant findNearestPlant() {
        ModelPlant res = null;
        double minDist = Double.MAX_VALUE;
        for(ModelPlant plant : this.game.getPlants().values()) {
            if(!plant.canBeHarvested()) {
                continue;
            }
            double dist = this.position.distance(plant.getPosition());
            if(dist < minDist) {
                minDist = dist;
                res = plant;
            }
        }
        return res;
    }

    @Override
    public void setDest(Point dest) {
        if(!this.game.getGridSystem().getPoint(dest)) {
            return;
        }
        this.dest = new Point(dest);
        this.pathfinder.findPathAsync(this.position, dest).thenAccept(path -> {
            this.currentPath = path;
        });
    }

    public ArrayList<Point> getCurrentPath() {
        return currentPath;
    }

    public void move() {
        long currentTime = System.currentTimeMillis();
        if(status == Status.MOVING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
            this.status = Status.IDLING;
            this.lastStateChangeTime = currentTime;
            this.currentPath.clear();
        }else if(status == Status.IDLING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
            Random rand = new Random();
            this.setDest(new Point(rand.nextInt(1150), rand.nextInt(850)));
            this.status = Status.MOVING;
            this.lastStateChangeTime = currentTime;
        }

        if(this.status == Status.MOVING) {
            Point helper = null;
            int dx, dy;
            if(this.currentPath.size() > 0) {
                helper = this.currentPath.get(0);
                helper = new Point(helper.x * GridSystem.CELL_SIZE, helper.y * GridSystem.CELL_SIZE);
                dx = helper.x - this.position.x;
                dy = helper.y - this.position.y;
            }else {
                dx = dest.x - position.x;
                dy = dest.y - position.y;
            }
            double dist = Math.sqrt(dx * dx + dy * dy);
            if(dist <= this.SPEED) {
                if(helper != null) {
                    this.position = new Point(helper);
                    this.currentPath.remove(0);
                }else {
                    this.position = new Point(this.dest);
                    this.status = Status.IDLING;
                }
            }else {
                double stepX = (dx / dist) * this.SPEED;
                double stepY = (dy / dist) * this.SPEED;
                position = new Point((int) (position.x + stepX), (int) (position.y + stepY));
                this.direction.setDirection(this.position, this.dest);
            }
        }
    }
}