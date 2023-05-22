package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Ball extends Circle {
    public static final int RADIUS = 16;
    public static final int STICK_WIDTH = 4;
    public static final int STICK_HEIGHT = 180 - RADIUS;
    private final int number;
    private Text numberText;
    private Rectangle stick;

    public Ball(double x, double y, int number) {
        super(x, y, RADIUS);
        this.setFill(Color.BLACK);
        this.number = number;
        this.numberText = new Text(String.valueOf(number));
        this.numberText.setFont(Font.font(numberText.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 18));
        this.numberText.setFill(Color.WHITE);
        this.numberText.setWrappingWidth(Ball.RADIUS * 2);
        this.numberText.prefHeight(Ball.RADIUS * 2);
        this.numberText.setX(this.getX() - Ball.RADIUS);
        this.numberText.setY(this.getY() + 8);
        this.numberText.setTextAlignment(TextAlignment.CENTER);
    }

    public Ball(double x, double y) {
        super(x, y, RADIUS);
        this.setFill(Color.BLACK);
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

    public double getX() {
        return getLayoutX() + getCenterX();
    }

    public void setX(double x) {
//        setLayoutX(x - getCenterX());
        setCenterX(x - getLayoutX());
    }

    public double getY() {
        return getLayoutY() + getCenterY();
    }
    public void setY(double y) {
//        setLayoutY(y - getCenterY());
        setCenterY(y - getLayoutY());
    }
}