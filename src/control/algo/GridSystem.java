package control.algo;

import model.ModelGame;
import model.ModelObstacle;
import view.VueMainGame;

import java.util.Arrays;
import java.util.HashMap;

public class GridSystem {
    public static final int CELL_SIZE = 30;
    public static final int OBSTACLE_SIZE = 30;
    private int width;
    private int height;
    private boolean[][] walkable;

    public GridSystem(ModelGame game) {
        this.width = VueMainGame.LEFT_WIDTH / CELL_SIZE;
        this.height = VueMainGame.SCREEN_HEIGHT / CELL_SIZE;
        this.walkable = new boolean[width][height];
        for (boolean[] row : walkable) {
            Arrays.fill(row, true);
        }

        HashMap<Integer, ModelObstacle> obstacles = game.getObstacles();
        int x,y, left, right, top, bottom, leftCell, rightCell, topCell, bottomCell;
        for(ModelObstacle obstacle : obstacles.values()) {
            x = obstacle.getPosition().x / CELL_SIZE;
            y = obstacle.getPosition().y / CELL_SIZE;
                        walkable[x][y] = false;
            }
        
    }

    public boolean[][] getWalkable() {
        return walkable;
    }

    public int getCELL_SIZE() {
        return CELL_SIZE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
