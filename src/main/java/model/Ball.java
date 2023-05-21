package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball extends Circle {
    public static final int RADIUS = 20;
    public static final int STICK_WIDTH = 4;
    public static final int STICK_HEIGHT = 180;
    private final int number;
    private Rectangle stick;

    public Ball(double x, double y, int number) {
        super(x, y, RADIUS);
        this.fillProperty().setValue(Color.BLACK);
        this.number = number;
    }

    public Ball(double x, double y) {
        super(x, y, RADIUS);
        this.fillProperty().setValue(Color.BLACK);
        this.number = -1;
    }

    public int getNumber() {
        return number;
    }

    public Rectangle getStick() {
        return stick;
    }

    public void setStick(Rectangle stick) {
        this.stick = stick;
    }
}
