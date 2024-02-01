package model;

import java.awt.*;

public abstract class ModelUnit {
    protected int id;
    protected Point position;
    protected Point dest;

    public ModelUnit(int id, Point position, Point dest) {
        this.id = id;
        this.position = position;
        this.dest = dest;
    }

    public int getId() {
        return this.id;
    }

    public Point getPosition() {
        return this.position;
    }

    public Point getDest() {
        return this.dest;
    }
}
