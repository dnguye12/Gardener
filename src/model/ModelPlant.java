package model;

import java.awt.*;

/**
 * Représente une plante dans le jeu. Chaque plante a un type, des étapes de croissance,
 * une santé (HP) et est associée à une partie du jeu.
 */
public class ModelPlant extends ModelUnit{
    /**
     * Énumère les types de plantes disponibles dans le jeu, avec leur nom,
     * santé maximale, nombre d'étapes de croissance, et vitesse de croissance.
     */
    public enum PlantType {
        MUSHROOM("mushroom", 200, 2, 300),
        WHEAT("wheat" ,500, 4, 500),
        SUNFLOWER("soliel", 300, 4, 300),
        TOMATO("tomato", 300, 4, 300);
        private final String name;
        private final int maxHP;
        private final int stageCount;
        private final int growspeed;
        PlantType(String name, int maxHP , int stageCount, int growspeed) {
            this.name = name;
            this.maxHP = maxHP;
            this.stageCount = stageCount;
            this.growspeed = growspeed;
        }
        public String getName() {
            return this.name;
        }
        public int getMaxHP() {
            return this.maxHP;
        }
        public int getStageCount() {
            return this.stageCount;
        }
        public int getGrowspeed() {
            return this.growspeed;
        }
        public String getPlantImage(int stage) {
            return "src/assets/maingame/plant/" + this.name + stage + ".png";
        }

        public String getStateIcon(int stage) {
            return "src/assets/maingame/plant/state" + (4 - stageCount + stage) + ".png";
        }

        public int getMoney() {
            return this.stageCount * 5;
        }
    }
    private final PlantType type;
    private int stage; // L'étape actuelle de croissance de la plante.
    private int currentStage; // Compteur pour le passage à l'étape suivante.
    private int hp;
    private int growspeed; // La vitesse à laquelle la plante grandit.
    private ModelGame game;
    public ModelPlant(int id, Point position, PlantType type, ModelGame game) {
        super(id, position, position);
        this.stage = 0;
        this.currentStage = 0;
        this.type = type;
        this.hp = this.type.getMaxHP();
        this.growspeed = this.type.getGrowspeed();
        this.game = game;
    }

    /**
     * Fait croître la plante d'une étape, éventuellement en augmentant son stade de croissance.
     */
    public void grow() {
        this.currentStage++;
        if(this.currentStage >= this.growspeed) {
            this.currentStage = 0;
            this.stage++;
        }
    }

    public int getStage() {
        return this.stage;
    }

    public int getCurrentStage() {
        return this.currentStage;
    }

    public PlantType getType() {
        return this.type;
    }

    public boolean isAlive() {
        return this.hp > 0 && this.stage < this.type.getStageCount();
    }

    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getGrowspeed() {
        return this.growspeed;
    }

    public boolean canBeHarvested() {
        return this.stage == this.type.getStageCount() - 1;
    }
    /**
     * Vérifie si la plante est dans le rayon de récolter d'un jardinier.
     * @return Vrai si au moins un jardinier peut récolter la plante, faux sinon.
     */
    public boolean isWithinLineOfSight() {
        for(ModelGardener gardener : this.game.getGardeners().values()) {
            if(this.position.distance(gardener.getPosition()) <= gardener.getRadius()) {
                return true;
            }
        }
        return false;
    }
}
