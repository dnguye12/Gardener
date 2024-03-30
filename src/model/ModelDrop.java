package model;

import java.awt.*;

/**
 * Classe abstraite représentant un objet "drop" dans le jeu. Les objets "drop" sont des éléments
 * qui peuvent être récupérés par les joueurs ou les personnages du jeu.
 */
public abstract class ModelDrop {
    protected int id;
    protected boolean hasPlayed1; // Indicateur si la première phase de l'animation de chute a été jouée.
    protected  boolean hasPlayed2; // Indicateur si la seconde phase de l'animation de chute a été jouée.
    protected  int dieTime; // Temps après lequel le drop disparaît.
    protected  static final int DROP_SPEED = 6; // Vitesse de chute du drop.
    protected Point position;
    protected Point height; // Hauteur actuelle du drop par rapport à sa position initiale.
    protected Point maxHeight; // Hauteur maximale de la chute.

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
    public void setDieTime(int dieTime) {
        this.dieTime = dieTime;
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

    /**
     * Gère la chute du drop, modifiant sa hauteur actuelle et mettant à jour les indicateurs d'animation.
     */
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
