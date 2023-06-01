package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.*;

public class Ball extends Circle {
    public static final Color[] COLORS = {Color.BLACK, Color.DARKGRAY, Color.DARKGOLDENROD};
    public static final double RADIUS = 14;
    public static final double STICK_WIDTH = 4;
    public static final double STICK_HEIGHT = 180 - RADIUS;
    private final int number;
    private final int playerNumber;
    private Text numberText;
    private Line stick;
    private double angle;

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

    public Line getStick() {
        return stick;
    }

    public void setStick(Ball mainCircle) {
        double distance = Math.sqrt(Math.pow(this.getX() - mainCircle.getX(), 2) + Math.pow(this.getY() - mainCircle.getY(), 2));
        double startX = mainCircle.getX() + (this.getX() - mainCircle.getX()) * mainCircle.getRadius() / distance;
        double startY = mainCircle.getY() + (this.getY() - mainCircle.getY()) * mainCircle.getRadius() / distance;
        double endX = this.getX() + (mainCircle.getX() - this.getX()) * this.getRadius() / distance;
        double endY = this.getY() + (mainCircle.getY() - this.getY()) * this.getRadius() / distance;
        if (stick == null) {
            stick = new Line(startX, startY, endX, endY);
            stick.setFill(Color.BLACK);
        } else {
            stick.setStartX(startX);
            stick.setStartY(startY);
            stick.setEndX(endX);
            stick.setEndY(endY);
        }
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
        setCenterY(y - getLayoutY());
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle % 360;
    }

    public double getAngleFromCoordinate(double mainX, double mainY) {
        double x = getX() - mainX;
        double y = getY() - mainY;
        double z = Math.abs(x / y);
        if (x >= 0 && y >= 0) {
            return 360 - Math.toDegrees(Math.atan(z));
        }
        if (x <= 0 && y >= 0) {
            return Math.toDegrees(Math.atan(z));
        }
        if (x <= 0 && y <= 0) {
            return 180 - Math.toDegrees(Math.atan(z));
        } else {
            return 180 + Math.toDegrees(Math.atan(z));
        }
    }
}