package model;

import java.util.HashMap;

public class SeedSystem {
    private HashMap<ModelPlant.PlantType, Boolean> foundSeed;

    public SeedSystem() {
        this.foundSeed = new HashMap<>();
        for (ModelPlant.PlantType type : ModelPlant.PlantType.values()) {
            this.foundSeed.put(type, false);
        }
    }

    public HashMap<ModelPlant.PlantType, Boolean> getFoundSeed() {
        return this.foundSeed;
    }

    public void findSeed(ModelPlant.PlantType type) {
        this.foundSeed.put(type, true);
    }

    public boolean hasSeed(ModelPlant.PlantType type) {
        return this.foundSeed.get(type);
    }

    public ModelPlant.PlantType getRandomFound() {
        ModelPlant.PlantType[] types = ModelPlant.PlantType.values();
        ModelPlant.PlantType type = types[(int) (Math.random() * types.length)];
        while (!this.hasSeed(type)) {
            type = types[(int) (Math.random() * types.length)];
        }
        return type;
    }

    public ModelPlant.PlantType getRandomNotFound() {
        ModelPlant.PlantType[] types = ModelPlant.PlantType.values();
        ModelPlant.PlantType type = types[(int) (Math.random() * types.length)];
        while (this.hasSeed(type)) {
            type = types[(int) (Math.random() * types.length)];
        }
        return type;
    }
}
