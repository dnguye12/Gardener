package control.algo;

import model.ModelGame;
import model.ModelObstacle;
import view.VueMainGame;

import java.util.Arrays;
import java.util.HashMap;

public class GridSystem {
    public static final int CELL_SIZE = 30;
    private final int OBSTACLE_SIZE = 30;
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
            x = obstacle.getPosition().x;
            y = obstacle.getPosition().y;

            left = x - OBSTACLE_SIZE / 2;
            right = x + OBSTACLE_SIZE / 2;
            top = y - OBSTACLE_SIZE / 2;
            bottom = y + OBSTACLE_SIZE / 2;

            leftCell = left / CELL_SIZE;
            rightCell = (right + CELL_SIZE - 1) / CELL_SIZE;
            topCell = top / CELL_SIZE;
            bottomCell = (bottom + CELL_SIZE - 1) / CELL_SIZE;

            for(int i = Math.max(0, leftCell); i <= Math.min(width - 1, rightCell); i++) {
                    for(int j = Math.max(0, topCell); j <= Math.min(height - 1, bottomCell); j++) {
                            walkable[i][j] = false;
                        }
                    }
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
