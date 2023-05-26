package view;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private final int speed;
    private final Ball ball;
    private final Rotate rotate;

    public BallRotation(Ball invisibleCircle, int speed, Ball ball, Rotate rotate) {
        this.speed = speed;
        this.ball = ball;
        this.rotate = rotate;
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