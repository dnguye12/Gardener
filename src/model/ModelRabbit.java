package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;
import view.VueMainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModelRabbit extends ModelUnit{
    private VueMainGame vueMainGame;
    private final int SPEED = 5;
    private final int MEMSPAN = 3000;
    private ModelGame game;
    private Status status;

    private long lastStateChangeTime;
    private long lastMoveTime;
    private int dieTime;
    private boolean foundPlant;
    private ModelPlant eatingPlant;
    private Direction direction;
    private int animationState;
    private AStarPathfinder pathfinder;
    private ArrayList<Point> currentPath;
    public enum Status {
        IDLING("Idling"),
        MOVING("Moving"),
        FLEEING("Fleeing"),
        QUITING("Quiting"),
        EATING("Eating");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public ModelRabbit(int id, Point position, Point dest, ModelGame game, VueMainGame vueMainGame) {
        super(id, position, dest);
        this.vueMainGame = vueMainGame;
        this.game = game;
        this.status = Status.IDLING;
        this.dieTime = 15000;

        this.lastMoveTime = System.currentTimeMillis();
        this.lastStateChangeTime = System.currentTimeMillis();

        this.foundPlant = false;
        this.eatingPlant = null;

        this.direction = new Direction();
        this.animationState = 0;

        this.pathfinder = game.getPathfinder();
        this.currentPath = new ArrayList<>();
    }

    public ModelRabbit(int id, Point position, Point dest, ModelGame game, int direction, VueMainGame vueMainGame) {
        this(id, position, dest, game, vueMainGame);
        this.direction = new Direction(direction);
        this.animationState = 0;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getDieTime() {
        return this.dieTime;
    }

    public void setDieTime(int dieTime) {
        this.dieTime = dieTime;
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
        this.pathfinder.findPathAsync(this.position, dest).thenAccept(path -> {
            this.currentPath = path;
        });
    }

    public String getAnimation() {
        String helper = this.direction.getDirection() == 1 ? "right" : "left";
        if(this.status == Status.MOVING || this.status == Status.FLEEING) {
            return "src/assets/maingame/animation/rabbit/move" + helper + this.animationState + ".png";
        }else {
            return "src/assets/maingame/animation/rabbit/idle" + helper + this.animationState + ".png";
        }
    }

    private ModelPlant findNearestPlant() {
        ModelPlant res = null;
        double minDistance = Double.MAX_VALUE;
        for(ModelPlant plant : this.game.getPlants().values()) {
            if(plant.isWithinLineOfSight()) {
                continue;
            }
            double dist = this.position.distance(plant.getPosition());
            if(dist < minDistance) {
                minDistance = dist;
                res = plant;
            }
        }
        return res;
    }

    private Point findNearestCorner() {
        int helperx = this.vueMainGame.getLeft_width() - 50;
        int helpery = this.vueMainGame.getScreen_height() - 50;
        Point[] corners = {
                new Point(0, 0),
                new Point(0, helpery),
                new Point(helperx, 0),
                new Point(helperx, helpery)
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
        if (this.dieTime > 0 && this.status != Status.QUITING) {
            if (isWithinLineOfSight() && this.status != Status.FLEEING) {
                this.status = Status.FLEEING;
                this.setDest(this.findNearestCorner());
                this.lastStateChangeTime = currentTime;
            }
            if (this.status == Status.FLEEING) {
                if (this.position.equals(this.dest) || currentTime - this.lastStateChangeTime > this.MEMSPAN) {
                    this.status = Status.IDLING;
                    this.lastStateChangeTime = currentTime;
                }
            } else if (status == Status.MOVING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
                this.status = Status.IDLING;
                this.lastStateChangeTime = currentTime;
            } else if (status == Status.IDLING && currentTime - this.lastStateChangeTime > this.MEMSPAN) {
                ModelPlant nearestPlant = this.findNearestPlant();
                if (nearestPlant != null) {
                    this.setDest(nearestPlant.getPosition());
                    this.foundPlant = true;
                    this.eatingPlant = nearestPlant;
                } else {
                    Random rand = new Random();
                    this.setDest(new Point(rand.nextInt(1150), rand.nextInt(850)));
                    this.foundPlant = false;
                }
                this.status = Status.MOVING;
                this.lastStateChangeTime = currentTime;
            }

        } else if (this.status != Status.QUITING) {
            this.status = Status.QUITING;
            this.setDest(this.findNearestCorner());
        }
        if (this.status != Status.IDLING) {
            int dx = dest.x - position.x;
            int dy = dest.y - position.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if(this.status == Status.MOVING && dist <= 25) {
                this.currentPath.clear();
                this.status = Status.EATING;
                this.lastStateChangeTime = currentTime;
            }
            else if (this.status != Status.EATING ) {
                Point helper = null;
                if(this.currentPath.size() > 0) {
                    helper = this.currentPath.get(0);
                    helper = new Point(helper.x * GridSystem.CELL_SIZE, helper.y * GridSystem.CELL_SIZE);
                    dx = helper.x - this.position.x;
                    dy = helper.y - this.position.y;
                }else {
                    dx = dest.x - position.x;
                    dy = dest.y - position.y;
                }
                dist = Math.sqrt(dx * dx + dy * dy);
                if(dist <= SPEED) {
                    if(helper != null) {
                        this.position = new Point(helper);
                        this.currentPath.remove(0);
                    }else {
                        this.position = new Point(this.dest);
                    }
                }else {
                    double stepX = (dx / dist) * SPEED;
                    double stepY = (dy / dist) * SPEED;
                    this.direction.setDirection(this.position, this.dest);
                    position = new Point((int) (position.x + stepX), (int) (position.y + stepY));
                }
            }
        }
        lastMoveTime = currentTime;
    }

    public boolean canBeRemoved() {
        if(this.status == Status.QUITING) {
            double dist = this.position.distance(this.dest);
            return dist <= 6;
        }
        return false;
    }

    public void eat() {
        if(this.eatingPlant == null) {
            this.status = Status.IDLING;
            this.lastStateChangeTime = System.currentTimeMillis();
            this.eatingPlant = null;
            this.foundPlant = false;
        } else if(this.status == Status.EATING) {
            this.eatingPlant.setHP(this.eatingPlant.getHP() - 1);
                this.dieTime += 1;
                if(!this.eatingPlant.isAlive()) {
                    this.status = Status.IDLING;
                    this.lastStateChangeTime = System.currentTimeMillis();
                    this.eatingPlant = null;
                    this.foundPlant = false;
                }
        }
    }
}
