package model;

import java.awt.*;

public class ModelSeedDrop extends ModelDrop {
    private ModelPlant.PlantType type;

    public ModelSeedDrop(int id, Point position, ModelPlant.PlantType type) {
        super(id, position);
        this.type = type;
    }

    public ModelPlant.PlantType getType() {
        return this.type;
    }
}
