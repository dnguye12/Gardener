package control.algo;

import model.ModelGame;
import model.ModelGardener;
import model.ModelObstacle;
import view.VueMainGame;

import java.util.Arrays;
import java.util.HashMap;

public class GridSystem {
    public static final int CELL_SIZE = 30;
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
        int x,y, x2, y2;
        for(ModelObstacle obstacle : obstacles.values()) {
            x = obstacle.getPosition().x;
            y = obstacle.getPosition().y;
            x2 = (int) (x / CELL_SIZE);
            y2 = (int) (y / CELL_SIZE);
            walkable[x2][y2] = false;
            if(x % CELL_SIZE != 0 && y%CELL_SIZE != 0) {
                walkable[x2 + 1][y2] = false;
                walkable[x2][y2 + 1] = false;
                walkable[x2 + 1][y2 + 1] = false;
            }else if(x % CELL_SIZE != 0) {
                walkable[x2 + 1][y2] = false;
            }else if(y % CELL_SIZE != 0) {
                walkable[x2][y2 + 1] = false;
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
