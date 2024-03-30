package control.algo;

import model.ModelGame;
import model.ModelObstacle;
import view.VueMainGame;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Crée une représentation de grille de jeu pour l'algo A*, permettant de déterminer les cases franchissables et les obstacles.
 */
public class GridSystem {
    private VueMainGame vueMainGame;
    public static final int CELL_SIZE = 30;
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
        int x,y;
        for(ModelObstacle obstacle : obstacles.values()) {
            x = obstacle.getPosition().x / CELL_SIZE;
            y = obstacle.getPosition().y / CELL_SIZE;
            walkable[x][y] = false;
            }

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
}
