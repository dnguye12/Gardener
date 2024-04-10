package control.algo;

import model.ModelGame;
import model.ModelObstacle;
import view.VueMainGame;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Crée une représentation de grille de jeu pour l'algo A*, permettant de déterminer les cases franchissables et les obstacles.
 */
public class GridSystem {
    private VueMainGame vueMainGame;
    public static final int CELL_SIZE = 10; // Une cellule plus petite est plus précise mais prend plus de temps à calculer
    public static final int OBSTACLE_SIZE = 60;
    private int width;
    private int height;
    private boolean[][] walkable;

    public GridSystem(ModelGame game, VueMainGame vueMainGame) {
        this.vueMainGame = vueMainGame;
        this.width = this.vueMainGame.getLeft_width() / CELL_SIZE;
        this.height = this.vueMainGame.getScreen_height() / CELL_SIZE;
        this.walkable = new boolean[width][height];
        for (boolean[] row : walkable) {
            Arrays.fill(row, true);
        }

        HashMap<Integer, ModelObstacle> obstacles = game.getObstacles();
        for(ModelObstacle obstacle : obstacles.values()) {
            int x = obstacle.getPosition().x;
            int y = obstacle.getPosition().y;
            for(int i = x - OBSTACLE_SIZE / 2; i < x + OBSTACLE_SIZE /2; i += CELL_SIZE) {
                for(int j = y - OBSTACLE_SIZE / 2; j < y + OBSTACLE_SIZE / 2; j += CELL_SIZE) {
                        this.setPoint(new Point(i, j), false);
                }
            }
        }
    }

    public void initWalkable() {

    }

    public boolean[][] getWalkable() {
        return walkable;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCell(int lig, int col, boolean bool) {
        this.walkable[lig][col] = bool;
    }

    public boolean getPoint(Point point) {
        int x = point.x / CELL_SIZE;
        int y = point.y / CELL_SIZE;

        return this.walkable[x][y];
    }

    public void setPoint(Point point, boolean bool) {
        int x = point.x / CELL_SIZE;
        int y = point.y / CELL_SIZE;

        this.walkable[x + OBSTACLE_SIZE / CELL_SIZE / 2][y + OBSTACLE_SIZE / CELL_SIZE / 2] = bool;
    }
}
