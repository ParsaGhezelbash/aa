package view;

import javafx.animation.Animation;
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
import javafx.util.Duration;
import model.Ball;
import model.Game;
import model.Level;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private final InGameMenu inGameMenu;
    private final Level level;
    private final Ball ball;

    public ShootingAnimation(InGameMenu inGameMenu, Ball ball) {
        this.inGameMenu = inGameMenu;
        this.level = inGameMenu.getLevel();
        this.ball = ball;

        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = ball.getY() + (ball.getPlayerNumber() != 2 ? -1 : 1) * 20;
        double x = ball.getX() + inGameMenu.getLevel().getWind();

        boolean failed = isConnectedToBalls();
        boolean isFinished = failed || (inGameMenu.getLevel().getNumberOfConnectedBalls1() == inGameMenu.getLevel().getNumberOfBalls()) ||
                (level.getNumberOfConnectedBalls2() == level.getNumberOfBalls() && !level.isSinglePlayer()) || (level.getMinutes() == Level.GAME_TIME);
        boolean lost = failed || (level.getMinutes() == Level.GAME_TIME);

        if (isConnectedToMainBall() && !failed) {
            inGameMenu.connectBall(ball);
            updateLevel();
            setLabels();
            updatePhase();

            isFinished = (level.getNumberOfConnectedBalls1() == level.getNumberOfBalls()) || (level.getNumberOfConnectedBalls2() == level.getNumberOfBalls()) || (level.getMinutes() == Level.GAME_TIME);

            inGameMenu.getAnimations().removeAnimation(this);
            this.stop();
        }

        if (isFinished) {
            inGameMenu.finishGame(!failed, ball.getPlayerNumber());
        }

        ball.setY(y);
        ball.setX(x);
        if (ball.getNumberText() != null) {
            ball.getNumberText().setY(y + ball.getNumberText().getLayoutBounds().getHeight() / 4);
            ball.getNumberText().setX(x - ball.getNumberText().getLayoutBounds().getWidth() / 2);
        }
    }

    public void updateLevel() {
        level.increaseScore();
        level.addConnectedBall(ball);
        level.setIcingMode(level.getIcingMode() + 0.4);
    }

    public void updatePhase() {
        int totalConnectedBalls = level.isSinglePlayer() ? level.getNumberOfConnectedBalls1() : Math.max(level.getNumberOfConnectedBalls1(), level.getNumberOfConnectedBalls2());
        double phase = (double) totalConnectedBalls / level.getNumberOfBalls();
        level.setPhase(phase);
        if (phase >= 0.25 && phase < 0.5 && !inGameMenu.getTimeLines().getChangeDirectionTimeLine().getStatus().equals(Animation.Status.RUNNING)) {
            inGameMenu.getTimeLines().getChangeDirectionTimeLine().play();
            inGameMenu.getTimeLines().getChangeBallSizeTimeLine().play();
        } else if (phase >= 0.5 && phase < 0.75 && !inGameMenu.getTimeLines().getBallsVisibilityTimeLine().getStatus().equals(Animation.Status.RUNNING)) {
            inGameMenu.getTimeLines().getBallsVisibilityTimeLine().play();
        } else if (phase >= 0.75 && phase <= 1 && !inGameMenu.getTimeLines().getWindTimeLine().getStatus().equals(Animation.Status.RUNNING)) {
            inGameMenu.getTimeLines().getWindTimeLine().play();
        }
    }

    private void setLabels() {
        if (level.isSinglePlayer()) {
            inGameMenu.getBallCountLabel1().setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
            inGameMenu.getBallCountLabel2().setText(String.valueOf(level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
        } else {
            inGameMenu.getBallCountLabel1().setText("Ball Count 1: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                    "\nBall Count 2: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
            inGameMenu.getBallCountLabel2().setText((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                    " | " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
        }
        inGameMenu.getIcingModeProgressBar().setProgress(level.getIcingMode());
    }

    private boolean isConnectedToMainBall() {
        double distance = Math.sqrt(Math.pow(ball.getCenterX() - inGameMenu.getMainCircle().getCenterX(), 2) + Math.pow(ball.getCenterY() - inGameMenu.getMainCircle().getCenterY(), 2));
        return distance <= (ball.getRadius() + Game.DISTANCE);
    }

    private boolean isConnectedToBalls() {
        for (Ball connectedBall : inGameMenu.getConnectedBalls()) {
            if (level.areConnected(ball, connectedBall)) {
                return true;
            }
        }
        return false;
    }
}