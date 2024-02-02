package model;

public class IdGen {
    private static int gardenerId = 0;
    private static int plantId = 0;

    public static int generateGardenerId() {
        return gardenerId++;
    }

    public static int generatePlantId() {
        return plantId++;
    }
}
