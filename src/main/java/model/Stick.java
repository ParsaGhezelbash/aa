package model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Stick extends Shape {
    private final Circle circle;
    private final Rectangle rectangle;
    private final int number;

    public Stick(int number) {
        this.number = number;
        this.circle = new Circle(10);
        this.circle.fillProperty().setValue(javafx.scene.paint.Color.BLACK);
        this.circle.setVisible(true);
        this.rectangle = new Rectangle(6, 70);
        this.rectangle.fillProperty().setValue(javafx.scene.paint.Color.BLACK);
        this.rectangle.setVisible(false);
    }

    public Stick(double x, double y) {
        this.number = -1;
        this.circle = new Circle(x, y, 10);
        this.circle.fillProperty().setValue(javafx.scene.paint.Color.BLACK);
        this.rectangle = new Rectangle(x - 3, y - 70 - circle.getRadius(), 5, 70);
        this.rectangle.fillProperty().setValue(javafx.scene.paint.Color.BLACK);
        this.rectangle.setVisible(false);
    }

    public Circle getCircle() {
        return circle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getNumber() {
        return number;
    }

    public void connect() {
        this.rectangle.setVisible(true);
    }

    public double getX() {
        return circle.getCenterX();
    }

    public void setX(double x) {
        this.circle.setCenterX(x);
        this.rectangle.setX(x - 3);
    }

    public double getY() {
        return this.circle.getCenterY();
    }

    public void setY(double y) {
        this.circle.setCenterY(y);
        this.rectangle.setY(y - 70 - circle.getRadius());
    }
}
