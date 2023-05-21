package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Ball extends Circle {
    public static final int RADIUS = 20;
    public static final int STICK_WIDTH = 4;
    public static final int STICK_HEIGHT = 180;
    private final int number;
    private Label numberLabel;
    private Rectangle stick;

    public Ball(double x, double y, int number) {
        super(x, y, RADIUS);
        this.fillProperty().setValue(Color.BLACK);
        this.number = number;
        this.numberLabel = new Label(String.valueOf(number));
        this.numberLabel.setTextFill(Color.WHITE);
        this.numberLabel.setTextAlignment(TextAlignment.CENTER);
        this.numberLabel.setFont(new Font(numberLabel.getFont().getName(), 18));
        this.numberLabel.setPrefWidth(Ball.RADIUS * 1.6);
        this.numberLabel.setPrefHeight(Ball.RADIUS * 1.6);
        this.numberLabel.setLayoutX(this.getCenterX() - numberLabel.getPrefWidth() / 2);
        this.numberLabel.setLayoutY(this.getCenterY() - numberLabel.getPrefHeight() / 2);
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

    public Label getNumberLabel() {
        return numberLabel;
    }
}
