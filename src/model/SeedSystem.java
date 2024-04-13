package model;

import java.util.HashMap;

/**
 * SeedSystem est une classe qui gère les graines trouvées par le joueur
 */
public class SeedSystem {
    private HashMap<ModelPlant.PlantType, Boolean> foundSeed; // Dictionnaire des graines trouvées

    public SeedSystem() {
        this.foundSeed = new HashMap<>();
        for (ModelPlant.PlantType type : ModelPlant.PlantType.values()) {
            this.foundSeed.put(type, false);
        }
    }

    /**
     * Retourne le dictionnaire des graines trouvées
     * @return HashMap<ModelPlant.PlantType, Boolean>
     */
    public HashMap<ModelPlant.PlantType, Boolean> getFoundSeed() {
        return this.foundSeed;
    }

    // Met à jour le dictionnaire des graines trouvées avec une nouvelle graine
    public void findSeed(ModelPlant.PlantType type) {
        this.foundSeed.put(type, true);
    }

    // Retourne si le joueur a trouvé une graine de type donné
    public boolean hasSeed(ModelPlant.PlantType type) {
        return this.foundSeed.get(type);
    }

    // Retourne une graine trouvée aléatoire
    public ModelPlant.PlantType getRandomFound() {
        ModelPlant.PlantType[] types = ModelPlant.PlantType.values();
        ModelPlant.PlantType type = types[(int) (Math.random() * types.length)];
        while (!this.hasSeed(type)) {
            type = types[(int) (Math.random() * types.length)];
        }
        return type;
    }

    // Retourne une graine non trouvée aléatoire
    public ModelPlant.PlantType getRandomNotFound() {
        ModelPlant.PlantType[] types = ModelPlant.PlantType.values();
        ModelPlant.PlantType type = types[(int) (Math.random() * types.length)];
        while (this.hasSeed(type)) {
            type = types[(int) (Math.random() * types.length)];
        }
        return type;
    }
}
