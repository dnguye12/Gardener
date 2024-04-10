package model;

import java.util.Random;

public class ModelFieldCell {
    private boolean hasGrass;
    private int grassType;
    private ModelUnit content;


    public ModelFieldCell() {
        this.content = null;
        //this.hasGrass = true;
        Random rand = new Random();
        if(rand.nextInt(10) < 5) {
            this.hasGrass = false;
        }else {
            this.hasGrass = true;

            if (rand.nextInt(10) < 5) {
                this.grassType = 12;
            } else {
                this.grassType = rand.nextInt(12);
            }
        }
    }

    public ModelFieldCell(ModelUnit content) {
        this.content = content;
        this.hasGrass = false;
        Random rand = new Random();
        if(rand.nextInt(10) < 5) {
            this.grassType = 12;
        }else {
            this.grassType = rand.nextInt(12);
        }
    }

    public ModelUnit getContent() {
        return this.content;
    }

    public void setContent(ModelUnit content) {
        this.content = content;
    }

    public boolean hasGrass() {
        return this.hasGrass;
    }

    public void setGrass(boolean hasGrass) {
        this.hasGrass = hasGrass;
    }

    public int getGrassType() {
        return this.grassType;
    }
}
