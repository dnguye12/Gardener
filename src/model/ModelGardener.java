package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelGardener extends ModelUnit{
    private Status status;
    private final int SPEED = 5;
    private final int RADIUS = 100;
    private ModelGame game;
    public enum Status {
        IDLING("Idling"),
        MOVING("Moving");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public ModelGardener(int id, Point position, Point dest, ModelGame game) {
        super(id, position, dest);
        this.status = Status.IDLING;
        this.game = game;
    }
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRadius() {
        return this.RADIUS;
    }

    public void move() {
        int dx = this.dest.x - this.position.x;
        int dy = this.dest.y - this.position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance <= this.SPEED) {
            this.position = new Point(this.dest);
            this.status = Status.IDLING;
        }else {
            double stepX = (dx / distance) * this.SPEED;
            double stepY = (dy / distance) * this.SPEED;

            this.position = new Point((int) (this.position.x + stepX), (int) (this.position.y + stepY));
            this.status = Status.MOVING;
        }
    }

    public void plant() {
        if(this.game.getMoney() >= 10) {
            this.game.setMoney(this.game.getMoney() - 10);
            this.game.addPlant(new ModelPlant(IdGen.generatePlantId(), this.position, ModelPlant.PlantType.WHEAT));
        }
    }

    public ArrayList<Integer> plantsNear() {
        HashMap<Integer, ModelPlant> plants = this.game.getPlants();
        ArrayList<Integer> plantsToHarvest = this.game.getPlantsToHarvest();
        ArrayList<Integer> helper = new ArrayList<Integer>();

        for(int id : plantsToHarvest) {
            ModelPlant plant = plants.get(id);
            if (this.position.distance(plant.getPosition()) <= this.RADIUS) {
                helper.add(plant.getId());
            }
        }
        return helper;
    }

    public void harvest() {
        ArrayList<Integer> helper = this.plantsNear();
        for(int id : helper) {
            ModelPlant plant = this.game.getPlants().get(id);
            int money = plant.getMoney();
            this.game.setMoney(this.game.getMoney() + money);
            this.game.setScore(this.game.getScore() + money);
            this.game.removePlant(id);
        }
        ArrayList<Integer> plantsToHarvest = this.game.getPlantsToHarvest();
        plantsToHarvest.removeAll(helper);
        this.game.setPlantsToHarvest(plantsToHarvest);
    }
}
