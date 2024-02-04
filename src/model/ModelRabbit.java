package model;

import java.awt.*;
import java.util.Random;

public class ModelRabbit extends ModelUnit{
    private int timeLeft;
    private final int SPEED = 5;
    private final int MEMSPAN = 1500;
    private ModelGame game;
    private Status status;

    private long lastStateChangeTime;
    private long lastMoveTime;

    public enum Status {
        IDLING("Idling"),
        MOVING("Moving"),
        FLEEING("Fleeing");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public ModelRabbit(int id, Point position, Point dest, ModelGame game) {
        super(id, position, dest);
        this.timeLeft = 0;
        this.game = game;
        this.status = Status.IDLING;

        this.lastMoveTime = System.currentTimeMillis();
        this.lastStateChangeTime = System.currentTimeMillis();
    }

    public Status getStatus() {
        return this.status;
    }

    private ModelPlant findNearestPlant() {
        ModelPlant res = null;
        double minDistance = Double.MAX_VALUE;
        for(ModelPlant plant : this.game.getPlants().values()) {
            double dist = this.position.distance(plant.getPosition());
            if(dist < minDistance) {
                minDistance = dist;
                res = plant;
            }
        }
        return res;
    }

    private Point findNearestCorner() {
        Point[] corners = {
                new Point(0, 0),
                new Point(0, 850),
                new Point(1150, 0),
                new Point(1150, 850)
        };

        Point res = corners[0];
        double minDistance = this.position.distance(res);
        for(int i = 1; i < corners.length; i++) {
            double dist = this.position.distance(corners[i]);
            if(dist < minDistance) {
                minDistance = dist;
                res = corners[i];
            }
        }
        return res;
    }

    private boolean isWithinLineOfSight() {
        for(ModelGardener gardener : this.game.getGardeners().values()) {
            if(position.distance(gardener.getPosition()) <= gardener.getRadius()) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        long currentTime = System.currentTimeMillis();
        if(isWithinLineOfSight()) {
            this.status = Status.FLEEING;
            this.dest= this.findNearestCorner();
            this.lastStateChangeTime = currentTime;
        }
        if(this.status == Status.FLEEING) {
            if(this.position.equals(this.dest) || currentTime - this.lastStateChangeTime > this.MEMSPAN) {
                this.status = Status.IDLING;
                this.lastStateChangeTime = currentTime;
            }
        }else if(status == Status.MOVING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
            this.status = Status.IDLING;
            this.lastStateChangeTime = currentTime;
        }else if(status == Status.IDLING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
            ModelPlant nearestPlant = this.findNearestPlant();
            if (nearestPlant != null) {
                this.dest = nearestPlant.getPosition();
            } else {
                Random rand = new Random();
                this.dest = new Point(rand.nextInt(1150) , rand.nextInt(850));
            }
            this.status = Status.MOVING;
            this.lastStateChangeTime = currentTime;
        }
            if (status != Status.IDLING) {
                int dx = dest.x - position.x;
                int dy = dest.y - position.y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist > SPEED) {
                    double stepX = (dx / dist) * SPEED;
                    double stepY = (dy / dist) * SPEED;
                    position = new Point((int) (position.x + stepX), (int) (position.y + stepY));
                }
            }
            lastMoveTime = currentTime;
    }
}
