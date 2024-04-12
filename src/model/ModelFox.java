package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;
import view.VueMainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ModelFox extends ModelUnit{
    private VueMainGame vueMainGame;
    private final int SPEED = 5;
    private final int MEMSPAN = 3000; // Temps pendant lequel le lapin se souvient de sa dernière action.
    private ModelGame game;
    private Status status; // Statut actuel du lapin (IDLE, MOVING, etc.).

    private long lastStateChangeTime; // Dernier moment où le statut a changé.
    private long lastMoveTime; // Dernier moment où le lapin s'est déplacé.
    private int dieTime; // Temps avant que le lapin sortir de faim.
    private boolean foundFood; // Indique si le lapin a trouvé une plante à manger.
    private ModelDrop eatingDrop;
    private Direction direction; // Direction du lapin.
    private int animationState;
    private AStarPathfinder pathfinder; // Algorithme de recherche de chemin pour le déplacement.
    private ArrayList<Point> currentPath; // Chemin actuel suivi par le lapin.
    /**
     * Énumération des différents statuts possibles pour un lapin.
     */
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

    public ModelFox(int id, Point position, Point dest, ModelGame game, VueMainGame vueMainGame) {
        super(id, position, dest);
        this.vueMainGame = vueMainGame;
        this.game = game;
        this.status = Status.IDLING;
        this.dieTime = 15000;

        this.lastMoveTime = System.currentTimeMillis();
        this.lastStateChangeTime = System.currentTimeMillis();

        this.foundFood = false;
        this.eatingDrop = null;

        this.direction = new Direction();
        this.animationState = 0;

        this.pathfinder = game.getPathfinder();
        this.currentPath = new ArrayList<>();
    }

    public ModelFox(int id, Point position, Point dest, ModelGame game, int direction, VueMainGame vueMainGame) {
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

    /**
     * Détermine et définit la destination du lapin, en prenant en compte les obstacles.
     * @param dest Nouvelle destination du lapin.
     */
    @Override
    public void setDest(Point dest) {
        if(!this.game.getGridSystem().getPoint(dest)) {
            return;
        }
        this.dest = dest;
        this.pathfinder.findPathAsync(this.position, dest).thenAccept(path -> {
            this.currentPath = path;
        });
    }

    public String getAnimation() {
        String helper = this.direction.getDirection() == 1 ? "right" : "left";
        if(this.status == Status.MOVING || this.status == Status.FLEEING) {
            return "src/assets/maingame/animation/fox/move" + helper + this.animationState + ".png";
        }else {
            return "src/assets/maingame/animation/fox/idle" + helper + this.animationState + ".png";
        }
    }

    /**
     * Trouve la plante la plus proche que le lapin peut manger (eviler les jardiniers).
     * @return La plante la plus proche ou null si aucune plante n'est trouvée.
     */
    private ModelDrop findNearestFood() {
        ModelDrop res = null;
        double minDistance = Double.MAX_VALUE;

        boolean helper = false;
        for(ModelDrop d : this.game.getDrops().values()) {
            for(ModelGardener gardener : this.game.getGardeners().values()) {
                if(d.getPosition().distance(gardener.getPosition()) <= gardener.getRadius()) {
                    helper = true;
                    break;
                }
            }
            if(helper) {
                helper = false;
                continue;
            }else {
                double dist = this.position.distance(d.getPosition());
                if(dist < minDistance) {
                    minDistance = dist;
                    res= d;
                }
            }
        }
        return res;
    }

    /**
     * Trouve le coin le plus proche pour que le lapin puisse s'enfuir.
     * @return Le point représentant le coin le plus proche.
     */
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

    /**
     * Vérifie si le lapin est dans le champ de vision d'un jardinier.
     * @return Vrai si le lapin est visible par au moins un jardinier, faux sinon.
     */
    private boolean isWithinLineOfSight() {
        for(ModelGardener gardener : this.game.getGardeners().values()) {
            Point gardenerPos = gardener.getPosition();
            gardenerPos = new Point(gardenerPos.x - 21, gardenerPos.y - 24);
            if(position.distance(gardenerPos) <= gardener.getRadius()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Déplace le lapin selon son statut actuel et met à jour sa position.
     */
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
                ModelDrop nearestDrop = this.findNearestFood();

                if(nearestDrop != null) {
                    this.setDest(nearestDrop.getPosition());
                    this.foundFood = true;
                    this.eatingDrop = nearestDrop;
                }else {
                    Random rand = new Random();
                    this.setDest(new Point(rand.nextInt(1150), rand.nextInt(850)));
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
        if(this.eatingDrop == null) {
            this.status = Status.IDLING;
            this.lastStateChangeTime = System.currentTimeMillis();
            this.foundFood = false;
        }else if(this.status == Status.EATING) {
            this.dieTime += 100;
            this.game.removeDrop(this.eatingDrop.getId());
            this.eatingDrop = null;
            this.foundFood = false;
            this.status = Status.IDLING;
            this.lastStateChangeTime = System.currentTimeMillis();
        }
    }
}
