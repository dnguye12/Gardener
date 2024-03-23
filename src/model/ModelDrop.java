package model;

public abstract class ModelDrop {
    protected int id;
    protected int dieTime;

    public ModelDrop(int id, int dieTime) {
        this.id = id;
        this.dieTime = dieTime;
    }

    public int getId() {
        return this.id;
    }

    public int getDieTime() {
        return this.dieTime;
    }

    public void setDieTime(int dieTime) {
        this.dieTime = dieTime;
    }
}
