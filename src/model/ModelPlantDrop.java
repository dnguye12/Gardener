package model;

import java.awt.*;

public class ModelPlantDrop extends ModelDrop{
    private ModelPlant.PlantType type;

    public ModelPlantDrop(int id, Point position, ModelPlant plant) {
        super(id, position);
        this.type = plant.getType();
    }

    public ModelPlant.PlantType getType() {
        return this.type;
    }
}
