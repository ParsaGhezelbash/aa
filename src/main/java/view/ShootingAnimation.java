package view;

import javafx.animation.Transition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Ball;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private final AnchorPane anchorPane;
    private final Circle invisibleCircle;
    private final ArrayList<Ball> connectedBalls;
    private final Ball ball;

    public ShootingAnimation(AnchorPane anchorPane, Ball ball, Circle mainCircle, ArrayList<Ball> connectedBalls) {
        this.anchorPane = anchorPane;
        this.ball = ball;
        this.invisibleCircle = mainCircle;
        this.connectedBalls = connectedBalls;;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getCenterY() - 10;

        if (isConnectedToMainBall() && !isConnectedToBalls(connectedBalls)) {
            Rectangle stick = new Rectangle(invisibleCircle.getLayoutX() - (double) (Ball.STICK_WIDTH / 2), invisibleCircle.getLayoutY(), Ball.STICK_WIDTH, Ball.STICK_HEIGHT);
            stick.setFill(Color.BLACK);
            anchorPane.getChildren().add(stick);
            this.stop();
        }
            ball.setCenterY(y);
    }

    private boolean isConnectedToMainBall() {
        return invisibleCircle.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private boolean isConnectedToBalls(ArrayList<Ball> connectedBalls) {
        for (Ball connectedBall : connectedBalls) {
            if (connectedBall.getBoundsInParent().intersects(connectedBall.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}
