package model;

import java.awt.*;

/**
 * Classe abstraite représentant une unité de modèle dans le jeu.
 * Elle sert de base pour différents éléments mobiles ou les obstacles.
 */
public abstract class ModelUnit {
    protected int id;
    protected Point position; // Position actuelle de l'unité sur le terrain.
    protected Point dest; // Destination vers laquelle l'unité se dirige.

    protected boolean isSelected; // Indique si l'unité est actuellement sélectionnée par l'utilisateur.

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
