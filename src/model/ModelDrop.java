package model;

import java.awt.*;

public abstract class ModelDrop {
    protected int id;
    protected boolean hasPlayed1;
    protected  boolean hasPlayed2;
    protected final int dieTime;
    protected  static final int DROP_SPEED = 6;
    protected Point position;
    protected Point height;
    protected Point maxHeight;

    public ModelDrop(int id, Point position) {
        this.id = id;
        this.hasPlayed1 = false;
        this.hasPlayed2 = false;
        this.dieTime = 15000;
        this.position = position;
        this.height = new Point(position);
        this.maxHeight = new Point(this.position.x, this.height.y - 60);
    }

    public int getId() {
        return this.id;
    }

    public int getDieTime() {
        return this.dieTime;
    }

    public boolean getHasPlayed1() {
        return this.hasPlayed1;
    }

    public boolean getHasPlayed2() {
        return this.hasPlayed2;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getHeight() {
        return height;
    }

    public void setHeight(Point height) {
        this.height = height;
    }

    public Point getMaxHeight() {
        return maxHeight;
    }

    public void drop() {
        if(!this.hasPlayed1) {
            this.height.y -= DROP_SPEED;
            if(this.height.y == this.maxHeight.y) {
                this.hasPlayed1 = true;
            }
        }else {
            this.height.y += DROP_SPEED;
            if(this.height.y == this.position.y) {
                this.hasPlayed2 = true;
            }
        }
    }
}
