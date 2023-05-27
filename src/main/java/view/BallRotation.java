package view;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private final Ball ball;
    private Rotate rotate;

    public BallRotation(Ball ball, Rotate rotate) {
        this.ball = ball;
        this.rotate = rotate;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        ball.getTransforms().add(rotate);
        ball.getStick().getTransforms().add(rotate);
        if (ball.getNumberText() != null) ball.getNumberText().getTransforms().add(rotate);
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }
}