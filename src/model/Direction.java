package model;

import java.awt.*;

//Class direction pour gérer la direction des objets pour animation
public class Direction {
    private int direction; // -1 left, 1 right
    public Direction(int direction) {
        this.direction = direction;
    }

    public Direction() {
        this.direction = 1;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setDirection(Point from, Point to) {
        if (from.x < to.x) {
            this.direction = 1;
        } else {
            this.direction = -1;
        }
    }
}
