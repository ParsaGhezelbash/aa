package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Ball;
import model.Level;

import java.security.Key;
import java.util.Random;

public class TimeLines {
    private InGameMenu inGameMenu;
    private Timeline changeDirectionTimeLine;
    private Timeline ballsVisibilityTimeLine;
    private Timeline windTimeLine;
    private Timeline changeBallSizeTimeLine;

    public TimeLines(InGameMenu inGameMenu) {
        this.inGameMenu = inGameMenu;
        changeDirectionTimeLine = new Timeline();
        refreshChangeDirectionTimeLine();
        changeDirectionTimeLine.setCycleCount(-1);

        ballsVisibilityTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            boolean areVisible = inGameMenu.getConnectedBalls().get(0).isVisible();
            for (Ball connectedBall : inGameMenu.getConnectedBalls()) {
                if (areVisible) {
                    connectedBall.setVisible(false);
                    connectedBall.getStick().setVisible(false);
                    System.out.println("vvv");
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
            System.out.println(111);
            inGameMenu.getAnimations().getRotation().setAngle(-inGameMenu.getAnimations().getRotation().getAngle());
            refreshChangeDirectionTimeLine();
        });
//        changeDirectionTimeLine.getKeyFrames().remove(0);
        changeDirectionTimeLine.getKeyFrames().add(keyFrame);
    }

    private void refreshWindTimeLine() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis((new Random().nextInt(6) + 4) * 1000), actionEvent -> {
            System.out.println(222);
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
                    System.out.println(inGameMenu.getConnectedBalls().get(i).getX());
                    System.out.println(inGameMenu.getConnectedBalls().get(j).getX());
                    return true;
                }
            }
        }
        return false;
    }

    public Timeline getChangeDirectionTimeLine() {
        return changeDirectionTimeLine;
    }

    public void stopChangeDirectionTimeLine() {
        changeDirectionTimeLine.stop();
    }

    public Timeline getBallsVisibilityTimeLine() {
        return ballsVisibilityTimeLine;
    }

    public void stopBallsVisibilityTimeLine() {
        ballsVisibilityTimeLine.stop();
    }


    public Timeline getWindTimeLine() {
        return windTimeLine;
    }

    public void stopGetWindTimeLine() {
        windTimeLine.stop();
    }

    public Timeline getChangeBallSizeTimeLine() {
        return changeBallSizeTimeLine;
    }

    public void stopChangeBallSizeTimeLine() {
        changeBallSizeTimeLine.stop();
    }

    public void stopAllTimeLines() {
        changeDirectionTimeLine.stop();
        ballsVisibilityTimeLine.stop();
        changeBallSizeTimeLine.stop();
        windTimeLine.stop();
    }
}