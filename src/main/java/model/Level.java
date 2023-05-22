package model;

import javafx.scene.layout.AnchorPane;

public class Level {
    public final static int GAME_TIME = 2;
    public final static double LEVEL_X = 300;
    public final static double LEVEL_Y = 300;
    private final int difficulty;
    private final int numberOfBalls;
    private final int numberOfConnectedBalls;
    private final int numberOfPrimaryBalls;
    private final int mapNumber;
    private int icingMode;
    private boolean isInIcingMode;
    private AnchorPane lastGamePane;
    private int minutes;
    private int seconds;
    private int score;

    public Level(int difficulty, int numberOfBalls, int numberOfPrimaryBalls, int mapNumber) {
        this.difficulty = difficulty;
        this.numberOfBalls = numberOfBalls;
        this.numberOfConnectedBalls = 0;
        this.numberOfPrimaryBalls = numberOfPrimaryBalls;
        this.mapNumber = mapNumber;
        this.icingMode = 0;
        this.isInIcingMode = false;
        this.minutes = 0;
        this.seconds = 0;
        this.score = 0;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public int getNumberOfConnectedBalls() {
        return numberOfConnectedBalls;
    }

    public int getNumberOfPrimaryBalls() {
        return numberOfPrimaryBalls;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getIcingMode() {
        return icingMode;
    }

    public void setIcingMode(int icingMode) {
        this.icingMode = icingMode;
    }

    public boolean isInIcingMode() {
        return isInIcingMode;
    }

    public void setInIcingMode(boolean inIcingMode) {
        isInIcingMode = inIcingMode;
    }

    public AnchorPane getLastGamePane() {
        return lastGamePane;
    }

    public void setLastGamePane(AnchorPane lastGamePane) {
        this.lastGamePane = lastGamePane;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score += score;
    }
}
