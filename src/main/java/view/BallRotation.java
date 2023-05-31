package view;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private final Ball mainCircle, ball;
    private int angle;
    private Rotate rotate;
    private final double distance;

    public BallRotation(Ball mainCircle, Ball ball, int angle) {
        this.ball = ball;
        this.mainCircle = mainCircle;
        this.distance = Math.sqrt(Math.pow(ball.getCenterX() - mainCircle.getCenterX(), 2) + Math.pow(ball.getCenterY() - mainCircle.getCenterY(), 2));
        this.angle = angle;
        this.rotate = new Rotate(angle, mainCircle.getCenterX(), mainCircle.getCenterY());
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        ball.setAngle(ball.getAngle() + angle);
        ball.setX(mainCircle.getX() - distance * Math.sin(Math.toRadians(ball.getAngle())));
        ball.setY(mainCircle.getY() + distance * Math.cos(Math.toRadians(ball.getAngle())));
        ball.getNumberText().getTransforms().add(new Rotate(angle, mainCircle.getCenterX(), mainCircle.getCenterY()));
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }
}