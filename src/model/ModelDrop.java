package model;

public abstract class ModelDrop {
    protected int id;
    protected int dieTime;
    protected int value;

    public ModelDrop(int id, int dieTime, int value) {
        this.id = id;
        this.dieTime = dieTime;
        this.value = value;
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

    public int getValue() {
        return this.value;
    }
}
