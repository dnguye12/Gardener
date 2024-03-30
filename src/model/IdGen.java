package model;

/**
 * IdGen classe pour générer des identifiants uniques pour les objets du jeu.
 */
public class IdGen {
    private static int gardenerId = 1;
    private static int plantId = 0;
    private static int rabbitId = 0;
    private static int dropId = 0;

    public static int generateGardenerId() {
        return gardenerId++;
    }

    public static int generatePlantId() {
        return plantId++;
    }

    public static int generateRabbitId() {
        return rabbitId++;
    }

    public static int generateDropId() {
        return dropId++;
    }
}
