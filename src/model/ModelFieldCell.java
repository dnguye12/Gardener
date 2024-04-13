package model;

import java.util.Random;

/**
 * Classe représentant une cellule du terrain de jeu.
 */
public class ModelFieldCell {
    private boolean hasGrass;
    private int grassType;
    private ModelUnit content;


    public ModelFieldCell() {
        this.content = null;
        Random rand = new Random();
            this.hasGrass = true;
        // Variety de l'herbe
        if (rand.nextInt(10) < 5) {
            this.grassType = 12;
        } else {
            this.grassType = rand.nextInt(12);
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
