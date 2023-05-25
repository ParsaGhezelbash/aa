package view;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Ball;
import model.Level;

import java.util.ArrayList;
import java.util.zip.ZipFile;

import static model.Ball.RADIUS;
import static model.Ball.STICK_WIDTH;

public class ShootingAnimation extends Transition {
    private final AnchorPane anchorPane, resultMenuPane;
    private final Level level;
    private final Ball invisibleCircle, mainCircle;
    private final ArrayList<Ball> connectedBalls;
    private final ArrayList<Transition> allAnimations;
    private final Ball ball;
    private final Label label1, label2, scoreLabel, resultLabel, resultScoreLabel, resultTimeLabel;
    private final Timeline timeline;

    public ShootingAnimation(AnchorPane anchorPane, AnchorPane resultMenuPane, Level level, Ball ball, Ball invisibleCircle, Ball mainCircle,
                             ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations, Label label1, Label label2,
                             Label scoreLabel, Timeline timeline, Label resultLabel, Label resultScoreLabel, Label resultTimeLabel) {
        this.anchorPane = anchorPane;
        this.resultMenuPane = resultMenuPane;
        this.level = level;
        this.ball = ball;
        this.invisibleCircle = invisibleCircle;
        this.mainCircle = mainCircle;
        this.connectedBalls = connectedBalls;
        this.allAnimations = allAnimations;
        this.label1 = label1;
        this.label2 = label2;
        this.scoreLabel = scoreLabel;
        this.timeline = timeline;
        this.resultLabel = resultLabel;
        this.resultScoreLabel = resultScoreLabel;
        this.resultTimeLabel = resultTimeLabel;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getY() - 20;
        boolean failed = isConnectedToBalls(connectedBalls);
        boolean isFinished = failed || (level.getNumberOfConnectedBalls() == level.getNumberOfBalls()) || (level.getMinutes() == Level.GAME_TIME);
        boolean lost = failed || (level.getMinutes() == Level.GAME_TIME);

        if (isConnectedToMainBall() && !failed) {
            level.setNumberOfConnectedBalls(level.getNumberOfConnectedBalls() + 1);
            label1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));
            label2.setText(String.valueOf(level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));
            isFinished = (level.getNumberOfConnectedBalls() == level.getNumberOfBalls()) || (level.getMinutes() == Level.GAME_TIME);
            connectedBalls.add(ball);

            Rectangle stick = new Rectangle(invisibleCircle.getX() - (double) (Ball.STICK_WIDTH / 2), mainCircle.getY() + mainCircle.getRadius(), Ball.STICK_WIDTH, ball.getY() - ball.getRadius() - mainCircle.getY() - mainCircle.getRadius() - 14);
            stick.setFill(Color.BLACK);
            anchorPane.getChildren().add(stick);
            ball.setStick(stick);

            BallRotation ballRotation = new BallRotation(invisibleCircle, 1, ball); // TODO
            allAnimations.add(ballRotation);
            ballRotation.play();
            allAnimations.remove(this);
            this.stop();
        }
        if (isFinished) {
            System.out.println("Finished");
            level.setFinished(true);
            level.setWinner(!failed);
            timeline.stop();
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
            if (lost) {
                anchorPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                invisibleCircle.setFill(Color.RED);
                resultLabel.setText("You Lost!");
                resultLabel.setTextFill(Color.RED);
            } else {
                anchorPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                invisibleCircle.setFill(Color.GREEN);
                resultLabel.setText("You Won!");
                resultLabel.setTextFill(Color.GREEN);
            }
            resultScoreLabel.setText("Score : " + level.getScore());
            resultTimeLabel.setText("Time : " + level.getMinutes() + " : " + level.getSeconds());
            resultMenuPane.setVisible(true);
            resultMenuPane.toFront();
            resultMenuPane.requestFocus();
        }

        ball.setY(y);
        ball.getNumberText().setY(y + ball.getNumberText().getLayoutBounds().getHeight() / 4);
    }

    private void setLabels() {
        level.setNumberOfConnectedBalls(level.getNumberOfConnectedBalls() + 1);
        level.increaseScore();
        label1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));
        label2.setText(String.valueOf(level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));
        scoreLabel.setText("Score : " + level.getScore());
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
