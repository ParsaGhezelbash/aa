package view;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

public class BallRotation extends Transition {
    private Circle mainCircle;
    private int speed;
    private Ball ball;
    private Rotate rotate;

    public BallRotation(Circle mainCircle, int speed, Ball ball) {
        this.mainCircle = mainCircle;
        this.speed = speed;
        this.ball = ball;
        this.rotate = new Rotate(speed * 5, mainCircle.getLayoutX(), mainCircle.getLayoutY());
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