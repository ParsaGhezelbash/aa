package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Ball extends Circle {
    public static final Color[] COLORS = {Color.BLACK, Color.DARKGRAY, Color.DARKGOLDENROD};
    public static final double RADIUS = 14;
    public static final double STICK_WIDTH = 4;
    public static final double STICK_HEIGHT = 180 - RADIUS;
    private final int number;
    private final int playerNumber;
    private Text numberText;
    private Rectangle stick;

    public Ball(double x, double y, int number, int playerNumber) {
        super(x, y, RADIUS);
        this.setFill(COLORS[playerNumber]);
        this.number = number;
        this.playerNumber = playerNumber;
        this.numberText = new Text(String.valueOf(number));
        this.numberText.setFont(Font.font(numberText.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 18));
        this.numberText.setX(x - numberText.getLayoutBounds().getWidth() / 2);
        this.numberText.setY(y + numberText.getLayoutBounds().getHeight() / 4);
        this.numberText.setFill(Color.WHITE);
        this.numberText.setTextAlignment(TextAlignment.CENTER);
    }

    public Ball(double x, double y) {
        super(x, y, RADIUS);
        this.setFill(Color.BLACK);
        this.number = -1;
        this.playerNumber = -1;
    }

    public int getNumber() {
        return number;
    }

    public int getPlayerNumber() {
        return playerNumber;
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