package model;

import java.awt.*;

public class ModelGardener extends ModelUnit{
    private Status status;
    private final int SPEED = 5;

    public enum Status {
        IDLE,
        MOVING,
        PLANTING,
        HARVESTING,
        SELLING
    }

    public ModelGardener(int id, Point position, Point dest) {
        super(id, position, dest);
        this.status = Status.IDLE;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setDest(Point dest) {
        this.dest = dest;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void move() {
        int dx = this.dest.x - this.position.x;
        int dy = this.dest.y - this.position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance <= this.SPEED) {
            this.position = new Point(this.dest);
            this.status = Status.IDLE;
        }else {
            double stepX = (dx / distance) * this.SPEED;
            double stepY = (dy / distance) * this.SPEED;

            this.position = new Point((int) (this.position.x + stepX), (int) (this.position.y + stepY));
            this.status = Status.MOVING;
        }
    }
}
