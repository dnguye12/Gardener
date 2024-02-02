package model;

import java.awt.*;

public abstract class ModelUnit {
    protected int id;
    protected Point position;
    protected Point dest;

    protected boolean isSelected;

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

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getDest() {
        return this.dest;
    }

    public void setDest(Point dest) {
        this.dest = dest;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
