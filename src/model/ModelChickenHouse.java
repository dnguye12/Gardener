package model;

import java.awt.*;

public class ModelChickenHouse extends ModelUnit{
    private ModelGame game;
    public ModelChickenHouse(int id, Point position, ModelGame game) {
        super(id, position, position);
        this.game = game;
    }

    public void spawnChicken() {
        ModelChicken chicken = new ModelChicken(IdGen.generateChickenId(), this.getPosition(), this.game);
        this.game.addChicken(chicken);
    }
}
