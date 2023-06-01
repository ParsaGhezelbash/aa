package view;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private final Ball mainCircle, ball;
    private double angle;
    private final double distance;

    public BallRotation(Ball mainCircle, Ball ball, double angle) {
        this.ball = ball;
        this.mainCircle = mainCircle;
        this.distance = Math.sqrt(Math.pow(ball.getX() - mainCircle.getX(), 2) + Math.pow(ball.getY() - mainCircle.getY(), 2));
        this.angle = angle;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        ball.setAngle((ball.getAngle() + angle) % 360);
        ball.setX(mainCircle.getX() - distance * Math.sin(Math.toRadians(ball.getAngle())));
        ball.setY(mainCircle.getY() + distance * Math.cos(Math.toRadians(ball.getAngle())));
        ball.setStick(mainCircle);
        if (ball.getNumberText() != null) {
            ball.getNumberText().setY(ball.getY() + ball.getNumberText().getLayoutBounds().getHeight() / 4);
            ball.getNumberText().setX(ball.getX() - ball.getNumberText().getLayoutBounds().getWidth() / 2);
        }
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}