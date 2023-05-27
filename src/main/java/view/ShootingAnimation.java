package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Ball;
import model.Level;

import java.util.ArrayList;
import java.util.Random;

public class ShootingAnimation extends Transition {
    private final AnchorPane anchorPane, resultMenuPane;
    private final Level level;
    private final Ball invisibleCircle, mainCircle;
    private final ArrayList<Ball> connectedBalls;
    private final ArrayList<Transition> allAnimations;
    private final Ball ball;
    private final Label label1, label2, scoreLabel, resultLabel, resultScoreLabel, resultTimeLabel;
    private static Timeline changeDirectionTimeLine, changeBallSizeTimeLine, ballsVisiblityTimeLine, windTimeLine;
    private final Timeline ballCountTimeline;
    private final ProgressBar icingModeProgressBar;
    private static double windEffect;

    public ShootingAnimation(AnchorPane anchorPane, AnchorPane resultMenuPane, Level level, Ball ball, Ball invisibleCircle, Ball mainCircle,
                             ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations, Label label1, Label label2,
                             Label scoreLabel, Timeline timeline, Label resultLabel, Label resultScoreLabel, Label resultTimeLabel,
                             ProgressBar icingModeProgressBar) {
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
        this.ballCountTimeline = timeline;
        this.resultLabel = resultLabel;
        this.resultScoreLabel = resultScoreLabel;
        this.resultTimeLabel = resultTimeLabel;
        this.icingModeProgressBar = icingModeProgressBar;

        if (changeDirectionTimeLine == null) {
            changeDirectionTimeLine = new Timeline(new KeyFrame(Duration.millis(4000), actionEvent -> {
                level.getRotate().setAngle(-level.getRotate().getAngle());
                this.setCycleDuration(Duration.millis((new Random().nextInt(10) + 4) * 1000));
            }));
            changeDirectionTimeLine.setCycleCount(-1);

            ballsVisiblityTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
                boolean areVisible = connectedBalls.get(0).isVisible();
                for (Ball connectedBall : connectedBalls) {
                    if (areVisible) {
                        connectedBall.setVisible(false);
                        connectedBall.getStick().setVisible(false);
                        if (connectedBall.getNumberText() != null) connectedBall.getNumberText().setVisible(false);
                    } else {
                        connectedBall.setVisible(true);
                        connectedBall.getStick().setVisible(true);
                        if (connectedBall.getNumberText() != null) connectedBall.getNumberText().setVisible(true);
                    }
                }
            }));
            ballsVisiblityTimeLine.setCycleCount(-1);

            windTimeLine = new Timeline(new KeyFrame(Duration.millis(5000), actionEvent -> {
                level.setWind(new Random().nextInt(-15, 15));
                this.setCycleDuration(Duration.millis((new Random().nextInt(6) + 4) * 1000));
            }));
            windTimeLine.setCycleCount(-1);

            changeBallSizeTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
                for (Ball connectedBall : connectedBalls) {
                    if (connectedBall.getRadius() > Ball.RADIUS) connectedBall.setRadius(Ball.RADIUS);
                    else connectedBall.setRadius(connectedBall.getRadius() * (100 + new Random().nextInt(5, 10)) / 100);
                    if (checkConnectedBalls()) {
                        changeDirectionTimeLine.stop();
                        changeDirectionTimeLine = null;
                        ballsVisiblityTimeLine.stop();
                        ballsVisiblityTimeLine = null;
                        stopChangeBallSizeTimeLine();
                        changeBallSizeTimeLine = null;
                        windTimeLine.stop();
                        windTimeLine = null;
                        level.setFinished(true);
                        level.setWinner(false);
                        ballCountTimeline.stop();
                        for (Transition allAnimation : allAnimations) {
                            allAnimation.stop();
                        }
                        anchorPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        invisibleCircle.setFill(Color.RED);
                        resultLabel.setText(ball.getPlayerNumber() != 2 ? "You Lost!" : "GuestPlayer Lost!");
                        resultLabel.setTextFill(Color.RED);
                        resultScoreLabel.setText("Score : " + level.getScore());
                        resultTimeLabel.setText("Time : " + level.getMinutes() + " : " + level.getSeconds());
                        resultMenuPane.setVisible(true);
                        resultMenuPane.toFront();
                        resultMenuPane.requestFocus();
                    }
                }
            }));
            changeBallSizeTimeLine.setCycleCount(-1);
        }

        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getY() + (ball.getPlayerNumber() != 2 ? -1 : 1) * 20;
        double x = ball.getX() + level.getWind();
        System.out.println("windeffect" + windEffect);

        boolean failed = isConnectedToBalls(connectedBalls);
        boolean isFinished = failed || (level.getNumberOfConnectedBalls1() == level.getNumberOfBalls()) ||
                (level.getNumberOfConnectedBalls2() == level.getNumberOfBalls() && !level.isSinglePlayer()) || (level.getMinutes() == Level.GAME_TIME);
        boolean lost = failed || (level.getMinutes() == Level.GAME_TIME);
        if (isConnectedToMainBall() && !failed) {
            updatePhase();
            level.setIcingMode(level.getIcingMode() + 0.4);
            icingModeProgressBar.setProgress(level.getIcingMode());
            System.out.println("icing mode" + level.getIcingMode());
            if (ball.getPlayerNumber() != 2) {
                level.setNumberOfConnectedBalls1(level.getNumberOfConnectedBalls1() + 1);
            } else {
                level.setNumberOfConnectedBalls2(level.getNumberOfConnectedBalls2() + 1);
            }

            if (level.isSinglePlayer()) {
                label1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
                label2.setText(String.valueOf(level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
            } else {
                label1.setText("Ball Count 1: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                        "\nBall Count 2: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
                label2.setText((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                        " | " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
            }

            isFinished = (level.getNumberOfConnectedBalls1() == level.getNumberOfBalls()) || (level.getNumberOfConnectedBalls2() == level.getNumberOfBalls()) || (level.getMinutes() == Level.GAME_TIME);

            connectedBalls.add(ball);

            Rectangle stick;
            if (ball.getPlayerNumber() != 2) {
                stick = new Rectangle(invisibleCircle.getX() - Ball.STICK_WIDTH / 2,
                        mainCircle.getY() + mainCircle.getRadius(), Ball.STICK_WIDTH, ball.getY() - ball.getRadius() - mainCircle.getY() - mainCircle.getRadius() - 14);
            } else {
                stick = new Rectangle(invisibleCircle.getX() - Ball.STICK_WIDTH / 2,
                        ball.getY() + ball.getRadius() + 14, Ball.STICK_WIDTH, mainCircle.getY() - mainCircle.getRadius() - ball.getY() - ball.getRadius() - 14);
            }
            stick.setFill(ball.getFill());
            anchorPane.getChildren().add(stick);
            ball.setStick(stick);

            BallRotation ballRotation = new BallRotation(ball, level.getRotate());
            allAnimations.add(ballRotation);
            ballRotation.play();
            allAnimations.remove(this);
            this.stop();
        }

        if (isFinished) {
            System.out.println(failed + " " + (level.getNumberOfConnectedBalls1() == level.getNumberOfBalls()) + " " + (level.getMinutes() == Level.GAME_TIME));
            System.out.println("Finished");
            level.setFinished(true);
            level.setWinner(!failed);
            ballCountTimeline.stop();
            changeDirectionTimeLine.stop();
            changeDirectionTimeLine = null;
            changeBallSizeTimeLine.stop();
            changeBallSizeTimeLine = null;
            ballsVisiblityTimeLine.stop();
            ballsVisiblityTimeLine = null;
            windTimeLine.stop();
            windTimeLine = null;
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
            if (lost) {
                anchorPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                invisibleCircle.setFill(Color.RED);
                resultLabel.setText(ball.getPlayerNumber() != 2 ? "You Lost!" : "GuestPlayer Lost!");
                resultLabel.setTextFill(Color.RED);
            } else {
                anchorPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                invisibleCircle.setFill(Color.GREEN);
                resultLabel.setText(ball.getPlayerNumber() != 2 ? "You Won!" : "GuestPlayer Won!");
                resultLabel.setTextFill(Color.GREEN);
                level.setWinner(true);
            }
            resultScoreLabel.setText("Score : " + level.getScore());
            resultTimeLabel.setText("Time : " + level.getMinutes() + " : " + level.getSeconds());
            resultMenuPane.setVisible(true);
            resultMenuPane.toFront();
            resultMenuPane.requestFocus();
        }

        ball.setY(y);
        ball.setX(x);
        if (ball.getNumberText() != null) {
            ball.getNumberText().setY(y + ball.getNumberText().getLayoutBounds().getHeight() / 4);
            ball.getNumberText().setX(x - ball.getNumberText().getLayoutBounds().getWidth() / 2);
        }
    }

    public void stopChangeBallSizeTimeLine() {
        changeBallSizeTimeLine.stop();
    }

    public void updatePhase() {
        int totalConnectedBalls = level.isSinglePlayer() ? level.getNumberOfConnectedBalls1() : Math.max(level.getNumberOfConnectedBalls1(), level.getNumberOfConnectedBalls2());
        double phase = (double) totalConnectedBalls / level.getNumberOfBalls();
        System.out.println("phase " + phase);
        level.setPhase(phase);
        if (phase >= 0.25 && phase < 0.5 && !changeDirectionTimeLine.getStatus().equals(Animation.Status.RUNNING)) {
            System.out.println("change direction");
            changeDirectionTimeLine.play();
            changeBallSizeTimeLine.play();
        } else if (phase >= 0.5 && phase < 0.75 && !ballsVisiblityTimeLine.getStatus().equals(Animation.Status.RUNNING)) {
            ballsVisiblityTimeLine.play();
        } else if (phase >= 0.75 && phase <= 1 && !windTimeLine.getStatus().equals(Animation.Status.RUNNING)) {
            System.out.println("wind");
            windTimeLine.play();
        }
    }

    private void setLabels() {
        level.setNumberOfConnectedBalls1(level.getNumberOfConnectedBalls1() + 1);
        level.increaseScore();
        label1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
        label2.setText(String.valueOf(level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
        scoreLabel.setText("Score : " + level.getScore());
    }

    private boolean isConnectedToMainBall() {
        return areConnected(invisibleCircle, ball);
    }

    private boolean isConnectedToBalls(ArrayList<Ball> connectedBalls) {
        System.out.println("aaa " + connectedBalls.size());
        for (Ball connectedBall : connectedBalls) {
            if (ball.getBoundsInParent().intersects(connectedBall.getBoundsInParent())) {
                System.out.println(ball.getCenterX() + " " + ball.getCenterY() + " " + connectedBall.getCenterX() + " " + connectedBall.getCenterY());
                System.out.println(connectedBall.getNumber() + " " + ball.getNumber());
                return true;
            }
        }
        return false;
    }

    private boolean areConnected(Ball ball1, Ball ball2) {
        double distance = Math.sqrt(Math.pow(ball1.getCenterX() - ball2.getCenterX(), 2) + Math.pow(ball1.getCenterY() - ball2.getCenterY(), 2));
        return distance <= (ball1.getRadius() + ball2.getRadius());
    }

    private boolean checkConnectedBalls() {
        for (int i = 0; i < connectedBalls.size(); i++) {
            for (int j = i + 1; j < connectedBalls.size(); j++) {
                if (connectedBalls.get(i).getBoundsInParent().intersects(connectedBalls.get(j).getBoundsInParent())) {
                    System.out.println(connectedBalls.get(i).getNumber() + " " + connectedBalls.get(j).getNumber());
                    return true;
                }
            }
        }
        return false;
    }
}