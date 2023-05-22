package view;

import javafx.animation.Transition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Ball;

import java.util.ArrayList;
import java.util.zip.ZipFile;

public class ShootingAnimation extends Transition {
    private final AnchorPane anchorPane;
    private final Circle invisibleCircle;
    private final ArrayList<Ball> connectedBalls;
    private final ArrayList<Transition> allAnimations;
    private final Ball ball;

    public ShootingAnimation(AnchorPane anchorPane, Ball ball, Circle mainCircle, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations) {
        this.anchorPane = anchorPane;
        this.ball = ball;
        this.invisibleCircle = mainCircle;
        this.connectedBalls = connectedBalls;
        this.allAnimations = allAnimations;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getCenterY() - 20;
        boolean lost = isConnectedToBalls(connectedBalls);

        if (isConnectedToMainBall() && !lost) {
            Rectangle stick = new Rectangle(invisibleCircle.getLayoutX() - (double) (Ball.STICK_WIDTH / 2), invisibleCircle.getLayoutY(), Ball.STICK_WIDTH, ball.getCenterY() - 2 * ball.getRadius() - invisibleCircle.getLayoutY() );
            stick.setFill(Color.BLACK);
            anchorPane.getChildren().add(stick);
            ball.setStick(stick);
            connectedBalls.add(ball);
            BallRotation ballRotation = new BallRotation(invisibleCircle, 1, ball);
            allAnimations.add(ballRotation);
            ballRotation.play();
            this.stop();
        }
        else if (lost) {
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
        }

        ball.setCenterY(y);
        ball.getNumberText().setY(y + 5);
    }

    private boolean isConnectedToMainBall() {
        return invisibleCircle.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private boolean isConnectedToBalls(ArrayList<Ball> connectedBalls) {
        for (Ball connectedBall : connectedBalls) {
            if (ball.getBoundsInParent().intersects(connectedBall.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}
