package model;

import java.awt.*;
import java.util.Random;

public class ModelObstacle extends ModelUnit {
    private String imgLink;

    public ModelObstacle(int id, Point position) {
        super(id, position, position);

        Random rand = new Random();
        int n = rand.nextInt(4) + 1;
        imgLink = "src/assets/maingame/obstacle/" + 0 + ".png";
    }

    public String getImgLink() {
        return this.imgLink;
    }
}
