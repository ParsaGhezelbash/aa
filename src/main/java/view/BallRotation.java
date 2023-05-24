package view;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private final int speed;
    private final Ball ball;
    private final Rotate rotate;

    public BallRotation(Ball invisibleCircle, int speed, Ball ball) {
        this.speed = speed;
        this.ball = ball;
        this.rotate = new Rotate(speed * 5, invisibleCircle.getX(), invisibleCircle.getY());
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        ball.getTransforms().add(rotate);
        ball.getStick().getTransforms().add(rotate);
        ball.getNumberText().getTransforms().add(rotate);
    }
}