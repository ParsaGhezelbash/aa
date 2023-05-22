package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Ball extends Circle {
    public static final int RADIUS = 20;
    public static final int STICK_WIDTH = 4;
    public static final int STICK_HEIGHT = 180 - RADIUS;
    private final int number;
    private Text numberText;
    private Rectangle stick;

    public Ball(double x, double y, int number) {
        super(x, y, RADIUS);
        this.fillProperty().setValue(Color.BLACK);
        this.number = number;
        this.numberText = new Text(String.valueOf(number));
        this.numberText.setFont(Font.font(numberText.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 18));
        this.numberText.setFill(Color.WHITE);
        this.numberText.setWrappingWidth(Ball.RADIUS * 2);
        this.numberText.prefHeight(Ball.RADIUS * 2);
        this.numberText.setX(this.getCenterX() - Ball.RADIUS);
        this.numberText.setY(this.getCenterY() + 5);
        this.numberText.setTextAlignment(TextAlignment.CENTER);
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

    public Text getNumberText() {
        return numberText;
    }
}
