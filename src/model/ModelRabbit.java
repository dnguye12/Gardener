package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;
import view.VueMainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Représente un lapin dans le jeu, héritant de ModelUnit. Chaque lapin a un statut,
 * une vitesse, une vision et peut interagir avec les plantes du jeu.
 */
public class ModelRabbit extends ModelUnit{
    private VueMainGame vueMainGame;
    private final int SPEED = 5;
    private final int MEMSPAN = 3000; // Temps pendant lequel le lapin se souvient de sa dernière action.
    private ModelGame game;
    private Status status; // Statut actuel du lapin (IDLE, MOVING, etc.).

    private long lastStateChangeTime; // Dernier moment où le statut a changé.
    private long lastMoveTime; // Dernier moment où le lapin s'est déplacé.
    private int dieTime; // Temps avant que le lapin sortir de faim.
    private boolean foundPlant; // Indique si le lapin a trouvé une plante à manger.
    private ModelPlant eatingPlant; // Plante que le lapin est en train de manger.
    private boolean hasEaten; // Indique si le lapin a mangé une plante.
    private Direction direction; // Direction du lapin.
    private int animationState;
    private AStarPathfinder pathfinder; // Algorithme de recherche de chemin pour le déplacement.
    private ArrayList<Point> currentPath; // Chemin actuel suivi par le lapin.
    private final int MILK_RATE = 2; // Taux de chance de laisser tomber du lait.
    private final int SEED_RATE = 1; // Taux de chance de laisser tomber une graine.
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
            return "src/assets/maingame/animation/rabbit/move" + helper + this.animationState + ".png";
        }else {
            return "src/assets/maingame/animation/rabbit/idle" + helper + this.animationState + ".png";
        }
    }

    /**
     * Trouve la plante la plus proche que le lapin peut manger (eviler les jardiniers).
     * @return La plante la plus proche ou null si aucune plante n'est trouvée.
     */
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
        // Si le lapin n'est pas affamé, il peut se déplacer.
        if (this.dieTime > 0 && this.status != Status.QUITING) {
            // Si le lapin est visible par un jardinier, il doit s'enfuir.
            if (isWithinLineOfSight() && this.status != Status.FLEEING) {
                this.status = Status.FLEEING;
                this.setDest(this.findNearestCorner());
                this.lastStateChangeTime = currentTime;
            }
            // Si le lapin est en train de fuir, il doit arrêter de fuir s'il a atteint sa destination ou si le temps écoulé est supérieur à MEMSPAN.
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

    /**
     * Vérifie si le lapin peut être retiré du jeu.
     * @return Vrai si le lapin peut être retiré, faux sinon.
     */
    public boolean canBeRemoved() {
        if(this.status == Status.QUITING) {
            double dist = this.position.distance(this.dest);
            return dist <= 6;
        }
        return false;
    }

    /**
     * Fait manger le lapin, en diminuant la santé de la plante.
     */
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
                    this.hasEaten = true;
                }
        }
    }

    /**
     * Fait tomber un drop après que le lapin a mangé.
     */
    public void drop() {
        Random random = new Random();
        if(random.nextInt(10) < 5 ) {
            if (this.hasEaten) {
                Random rand = new Random();
                int drop = rand.nextInt(10) + 1;
                ModelDrop dr0p;
                if (drop < this.MILK_RATE) {
                    dr0p = new ModelMilkDrop(IdGen.generateDropId(), this.position);
                } else {
                    ModelPlant.PlantType helper = this.game.getSeedSystem().getRandomNotFound();
                    if (helper != null && drop < this.MILK_RATE + this.SEED_RATE) {
                        dr0p = new ModelSeedDrop(IdGen.generateDropId(), this.position, helper);
                    } else {
                        dr0p = new ModelPoopDrop(IdGen.generateDropId(), this.position);
                    }
                }
                this.game.addDrop(dr0p);
            }
        }
        this.hasEaten = false;
    }

}
