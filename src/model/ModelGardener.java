package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Modèle représentant un jardinier dans le jeu.
 * Hérite de ModelUnit pour sa position, sa destination, et son identifiant.
 */
public class ModelGardener extends ModelUnit{
    private Status status; // Statut actuel du jardinier (Idle, Moving).
    public static final int SPEED = 5; // Vitesse de base du jardinier.
    private final int RADIUS = 100; // Rayon d'action du jardinier.
    private Direction direction; // Direction du jardinier.
    private int animationState;
    private ModelGame game;
    private int promoteState; // Niveau de promotion du jardinier.
    private AStarPathfinder pathfinder; // Calculateur de chemin A*.
    private ArrayList<Point> currentPath; // Chemin actuel calculé par A*
    private boolean initPath; // Indique si le chemin initial a été défini.
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
        this.initPath = false;
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

    /**
     * Définit la destination du jardinier et initie le calcul du chemin.
     * Si la destination est un obstacle, la méthode retourne sans effectuer d'action.
     * @param dest La destination cible en Point.
     */
    @Override
    public void setDest(Point dest) {
        if(!this.game.getGridSystem().getPoint(dest)) {
            return;
        }
        this.dest = dest;
        MusicPlayer.playMove();
        this.pathfinder.findPathAsync(this.position, this.dest).thenAccept(path -> this.currentPath = path);
    }

    public String getAnimation() {
        String helper = this.direction.getDirection() == 1 ? "right" : "left";
        if(this.status == Status.MOVING) {
            return "src/assets/maingame/animation/gardener/move" + helper + this.animationState + ".png";
        }else {
            return "src/assets/maingame/animation/gardener/idle" + this.animationState + ".png";
        }
    }

    /**
     * Déplace le jardinier vers sa destination en suivant le chemin calculé avec A*.
     * Gère également l'initialisation du chemin et son nettoyage une fois atteint.
     */
    public void move() {
        int dx = this.dest.x - this.position.x;
        int dy = this.dest.y - this.position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if(distance <= SPEED || this.currentPath.size() == 0) {
            if(this.initPath) {
                this.position = new Point(this.dest);
            }
            this.status = Status.IDLING;
            this.currentPath.clear();
        }else {
            Point helper = this.currentPath.get(0);
            if(!this.initPath) {
                this.initPath = true;
            }
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
        pickUpDrop();
    }

    public void plant() {
        if(this.canPlant()) {
            this.game.setMoney(this.game.getMoney() - 10);
            this.game.addPlant(new ModelPlant(IdGen.generatePlantId(), this.position, ModelPlant.PlantType.randomType(), this.game));
        }
    }

    // Vérifie si le jardinier peut planter des plantes à proximité.
    public boolean canPlant() {
        if(this.game.getMoney() < 10) {
            return false;
        }
        for(Integer id : this.game.getPlants().keySet()) {
            ModelPlant plant = this.game.getPlants().get(id);
            if(plant != null && this.position.distance(plant.getPosition()) <= 30) {
                return false;
            }
        }
        return true;
    }

    /**
     * Identifie les plantes prêtes à être récoltées à proximité du jardinier.
     * @return Liste des ID des plantes prêtes à être récoltées.
     */
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

    /**
     * Identifie les drops à proximité du jardinier.
     * @return Liste des ID des gouttes à proximité.
     */
    public ArrayList<Integer> dropsNear() {
        HashMap<Integer, ModelDrop> drops = this.game.getDrops();
        ArrayList<Integer> helper = new ArrayList<Integer>();

        for(ModelDrop drop : drops.values()) {
            if(this.position.distance(drop.getPosition()) <= 30) {
                helper.add(drop.getId());
            }
        }
        return helper;
    }

    /**
     * Récolte les plantes prêtes à proximité, génère des drops en conséquence.
     */
    public void harvest() {
        ArrayList<Integer> helper = this.plantsNear();
        for(int id : helper) {
            ModelPlant plant = this.game.getPlants().get(id);
            if(plant != null) {
                ModelDrop drop = new ModelPlantDrop(IdGen.generateDropId(), plant.getPosition(), plant);
                this.game.addDrop(drop);
                this.game.removePlant(id);
            }
        }
        ArrayList<Integer> plantsToHarvest = this.game.getPlantsToHarvest();
        plantsToHarvest.removeAll(helper);
        this.game.setPlantsToHarvest(plantsToHarvest);
    }

    /**
     * Ramasse les drops à proximité du jardinier, augmentant le score et l'argent.
     */
    public void pickUpDrop() {
        ArrayList<Integer> helper = this.dropsNear();
        for(int id : helper) {
            ModelDrop drop = this.game.getDrops().get(id);
            if(drop != null) {
                if(drop instanceof ModelPlantDrop plantDrop) {
                    int money = plantDrop.getType().getMoney();
                    this.game.setMoney(this.game.getMoney() + money);
                    this.game.setScore(this.game.getScore() + money);
                    this.game.removeDrop(id);
                    MusicPlayer.playPickup();
                }else if(drop instanceof  ModelEggDrop) {
                    this.game.removeDrop(id);
                    this.game.setMoney(this.game.getMoney() + 25);
                    this.game.setScore(this.game.getScore() + 25);
                    MusicPlayer.playPickup();
                }
            }
        }
    }

    /**
     * Améliore le jardinier s'il a suffisamment d'argent et n'a pas atteint le niveau de promotion maximum.
     */
    public void upgrade() {
        if(this.game.getMoney() >= 25 && this.getPromoteState() < 5) {
            this.game.setMoney(this.game.getMoney() - 25);
            this.setPromoteState(this.getPromoteState() + 1);
        }
    }
}
