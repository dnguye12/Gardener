package model;

import java.awt.*;
import java.util.ArrayList;

public class ModelGame {
    private ModelUnit selected;
    private ArrayList<ModelGardener> gardeners;

    public ModelGame() {
        this.gardeners = new ArrayList<ModelGardener>();
        this.gardeners.add(new ModelGardener(1, new Point(100, 100), new Point(100, 100)));
        this.gardeners.add(new ModelGardener(2, new Point(400, 400), new Point(400, 400)));
        this.selected = null;
    }

    public void setSelected(ModelUnit selected) {
        this.selected = selected;
    }

    public ModelUnit getSelected() {
        return this.selected;
    }
    public ArrayList<ModelGardener> getGardeners() {
        return this.gardeners;
    }
}
