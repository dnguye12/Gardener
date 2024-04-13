package model;

import java.awt.*;

/**
 * Classe représentant un poulailler dans le jeu.
 */
public class ModelChickenHouse extends ModelUnit{
    private ModelGame game;
    private int chickenCount;
    public ModelChickenHouse(int id, Point position, ModelGame game) {
        super(id, position, position);
        this.game = game;
        this.chickenCount = 0;
    }
    public int getChickenCount() {
        return this.chickenCount;
    }
    public void setChickenCount(int chickenCount) {
        this.chickenCount = chickenCount;
    }

    // Fonction pour faire apparaître un poulet
    public void spawnChicken() {
        int money = this.game.getMoney();
        if(money >= 50) {
            this.game.setMoney(money - 50);
            ModelChicken chicken = new ModelChicken(IdGen.generateChickenId(), this.getPosition(), this.game);
            this.game.addChicken(chicken);
            this.chickenCount++;
            MusicPlayer.playChicken();
        }
    }
}
