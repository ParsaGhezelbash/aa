package view;

import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Ball;

import java.util.ArrayList;
import java.util.zip.ZipFile;

import static model.Ball.STICK_WIDTH;

public class ShootingAnimation extends Transition {
    private final AnchorPane anchorPane;
    private final Ball invisibleCircle;
    private final ArrayList<Ball> connectedBalls;
    private final ArrayList<Transition> allAnimations;
    private final Ball ball;

    public ShootingAnimation(AnchorPane anchorPane, Ball ball, Ball invisibleCircle, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations) {
        this.anchorPane = anchorPane;
        this.ball = ball;
        this.invisibleCircle = invisibleCircle;
        this.connectedBalls = connectedBalls;
        this.allAnimations = allAnimations;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getY() - 20;
        boolean lost = isConnectedToBalls(connectedBalls);

        if (isConnectedToMainBall() && !lost) {
            Rectangle stick = new Rectangle(invisibleCircle.getX() - (double) (Ball.STICK_WIDTH / 2), invisibleCircle.getY(), Ball.STICK_WIDTH, ball.getY() - 2 * ball.getRadius() - invisibleCircle.getY());
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
            anchorPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            invisibleCircle.setFill(Color.RED);
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
        }

        ball.setY(y);
        ball.getNumberText().setY(y + 5);
    }

    private boolean isConnectedToMainBall() {
        return areConnected(invisibleCircle, ball);
    }

    private boolean isConnectedToBalls(ArrayList<Ball> connectedBalls) {
        for (Ball connectedBall : connectedBalls) {
            if (connectedBall.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    private boolean areConnected(Ball ball1, Ball ball2) {
        double distance = Math.sqrt(Math.pow(ball1.getCenterX() - ball2.getCenterX(), 2) + Math.pow(ball1.getCenterY() - ball2.getCenterY(), 2));
        return distance <= (ball1.getRadius() + ball2.getRadius());
    }
}
