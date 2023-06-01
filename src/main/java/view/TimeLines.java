package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Ball;
import model.Level;

import java.util.ArrayList;
import java.util.Random;

public class TimeLines {
    private InGameMenu inGameMenu;
    private Timeline changeDirectionTimeLine;
    private Timeline ballsVisibilityTimeLine;
    private Timeline windTimeLine;
    private Timeline changeBallSizeTimeLine;
    private ArrayList<Timeline> palyingTimeLines;

    public TimeLines(InGameMenu inGameMenu) {
        this.inGameMenu = inGameMenu;
        this.palyingTimeLines = new ArrayList<>();
        changeDirectionTimeLine = new Timeline();
        refreshChangeDirectionTimeLine();
        changeDirectionTimeLine.setCycleCount(-1);

        ballsVisibilityTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            boolean areVisible = inGameMenu.getConnectedBalls().get(0).isVisible();
            for (Ball connectedBall : inGameMenu.getConnectedBalls()) {
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
        ballsVisibilityTimeLine.setCycleCount(-1);

        windTimeLine = new Timeline();
        refreshWindTimeLine();
        windTimeLine.setCycleCount(-1);

        changeBallSizeTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            for (Ball connectedBall : inGameMenu.getConnectedBalls()) {
                if (connectedBall.getRadius() > Ball.RADIUS) connectedBall.setRadius(Ball.RADIUS);
                else connectedBall.setRadius(connectedBall.getRadius() * (100 + new Random().nextInt(5, 10)) / 100);
                if (checkConnectedBalls()) {
                    inGameMenu.finishGame(false, 1);
                }
            }
        }));
        changeBallSizeTimeLine.setCycleCount(-1);
    }

    private void refreshChangeDirectionTimeLine() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis((new Random().nextInt(10) + 4) * 1000), actionEvent -> {
            inGameMenu.getAnimations().setAngleOfRotations(-inGameMenu.getAnimations().getAngleOfRotations());
            refreshChangeDirectionTimeLine();
        });
        changeDirectionTimeLine.getKeyFrames().add(keyFrame);
    }

    private void refreshWindTimeLine() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis((new Random().nextInt(6) + 4) * 1000), actionEvent -> {
            inGameMenu.getLevel().setWind(new Random().nextInt(-15, 15));
            refreshWindTimeLine();
        });
//        windTimeLine.getKeyFrames().remove(0);
        windTimeLine.getKeyFrames().add(keyFrame);
    }

    private boolean checkConnectedBalls() {
        for (int i = 0; i < inGameMenu.getConnectedBalls().size(); i++) {
            for (int j = i + 1; j < inGameMenu.getConnectedBalls().size(); j++) {
                if (Level.areConnected(inGameMenu.getConnectedBalls().get(i), inGameMenu.getConnectedBalls().get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public Timeline getChangeDirectionTimeLine() {
        return changeDirectionTimeLine;
    }

    public Timeline getBallsVisibilityTimeLine() {
        return ballsVisibilityTimeLine;
    }


    public Timeline getWindTimeLine() {
        return windTimeLine;
    }

    public Timeline getChangeBallSizeTimeLine() {
        return changeBallSizeTimeLine;
    }

    public void pauseRunningTimeLines() {
        if (changeDirectionTimeLine.getStatus().equals(Animation.Status.RUNNING))
            changeDirectionTimeLine.pause();
        if (ballsVisibilityTimeLine.getStatus().equals(Animation.Status.RUNNING))
            ballsVisibilityTimeLine.pause();
        if (changeBallSizeTimeLine.getStatus().equals(Animation.Status.RUNNING))
            changeBallSizeTimeLine.pause();
        if (windTimeLine.getStatus().equals(Animation.Status.RUNNING))
            windTimeLine.pause();
    }

    public void playPausedTimeLines() {
        if (changeDirectionTimeLine.getStatus().equals(Animation.Status.PAUSED))
            changeDirectionTimeLine.play();
        if (ballsVisibilityTimeLine.getStatus().equals(Animation.Status.PAUSED))
            ballsVisibilityTimeLine.play();
        if (changeBallSizeTimeLine.getStatus().equals(Animation.Status.PAUSED))
            changeBallSizeTimeLine.play();
        if (windTimeLine.getStatus().equals(Animation.Status.PAUSED))
            windTimeLine.play();
    }
}