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
    public static final int OBSTACLE_SIZE = 30;
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
            for(int i = x - OBSTACLE_SIZE ; i < x + OBSTACLE_SIZE ; i += CELL_SIZE) {
                for(int j = y - OBSTACLE_SIZE; j < y + OBSTACLE_SIZE; j += CELL_SIZE) {
                        this.setPoint(new Point(i, j), false);
                }
            }
        }
        /*
        int x,y;
        int diff = OBSTACLE_SIZE / CELL_SIZE;
        for(ModelObstacle obstacle : obstacles.values()) {
            x = obstacle.getPosition().x / CELL_SIZE;
            y = obstacle.getPosition().y / CELL_SIZE;
            if(diff == 0) {
                walkable[x][y] = false;
            }else {
                if (diff % 2 == 0) {
                    for (int i = x - diff + 1; i < x + diff; i++) {
                        for (int j = y - diff + 1; j < y + diff; j++) {
                            if (i >= 0 && i < width && j >= 0 && j < height) {
                                walkable[i][j] = false;
                            }
                        }
                    }
                } else {
                    for (int i = x - diff; i < x + diff; i++) {
                        for (int j = y - diff; j < y + diff; j++) {
                            if (i >= 0 && i < width && j >= 0 && j < height) {
                                walkable[i][j] = false;
                            }
                        }
                    }
                }
            }
            }*/
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

        this.walkable[x + 2][y + 2] = bool;
    }
}
