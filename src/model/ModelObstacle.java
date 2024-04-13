package model;

import java.awt.*;
import java.util.Random;

/**
 * ModelObstacle est une classe qui hérite de ModelUnit
 * Elle représente un obstacle dans le jeu
 */
public class ModelObstacle extends ModelUnit {
    private String imgLink;

    public ModelObstacle(int id, Point position) {
        super(id, position, position);

        Random rand = new Random();
        int n = rand.nextInt(4) + 1;
        imgLink = "src/assets/maingame/obstacle/" + n + ".png";
    }

    public String getImgLink() {
        return this.imgLink;
    }
}
