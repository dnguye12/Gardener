package model;
import view.VueMainGame;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class ModelField {
    private VueMainGame vueMainGame;
    private ModelGame game;
    private final int CELL_SIZE = 30;
    private ModelFieldCell[][] grid;
    private int width;
    private int height;

    public ModelField(ModelGame game, VueMainGame vueMainGame ) {
        this.vueMainGame = vueMainGame;
        this.game = game;

        this.width = this.vueMainGame.getLeft_width() / CELL_SIZE;
        this.height = this.vueMainGame.getScreen_height() / CELL_SIZE;
        this.grid = new ModelFieldCell[this.width][this.height];

        for(int i = 0; i < this.width; i++) {
            for(int j = 0; j < this.height; j++) {
                this.grid[i][j] = new ModelFieldCell();
            }
        }
        this.initObstacles();
    }

    private void initObstacles() {
        HashMap<Integer, ModelObstacle> obs = this.game.getObstacles();
        for(ModelObstacle o : obs.values()) {
            int x = o.getPosition().x / CELL_SIZE;
            int y = o.getPosition().y / CELL_SIZE;
            this.grid[x][y].setContent(o);
            this.grid[x][y].setGrass(false);
            this.grid[x + 1][y].setContent(o);
            this.grid[x + 1][y].setGrass(false);
            this.grid[x][y + 1].setContent(o);
            this.grid[x][y + 1].setGrass(false);
            this.grid[x + 1][y + 1].setContent(o);
            this.grid[x + 1][y + 1].setGrass(false);
        }
    }

    public ModelFieldCell[][] getGrid() {
        return this.grid;
    }
}
