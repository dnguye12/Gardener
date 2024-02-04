package model;

import java.awt.*;

public class ModelRabbit extends ModelUnit{
    private int timeLeft;
    private final int SPEED = 5;
    private ModelGame game;
    private Status status;

    public enum Status {
        IDLING("Idling"),
        MOVING("Moving"),
        FLEEING("Fleeing");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public ModelRabbit(int id, Point position, Point dest, ModelGame game) {
        super(id, position, dest);
        this.timeLeft = 0;
        this.game = game;
        this.status = Status.IDLING;
    }
}
