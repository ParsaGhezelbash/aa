package model;

import javafx.scene.layout.AnchorPane;

import java.util.Timer;

public class Level {
    private final int difficulty;
    private final int numberOfSticks;
    private final int numberOfPrimarySticks;
    private int icingMode;
    private boolean isInIcingMode;
    private AnchorPane lastGamePane;
    private Timer timer;

    public Level(int difficulty, int numberOfSticks, int numberOfPrimarySticks) {
        this.difficulty = difficulty;
        this.numberOfSticks = numberOfSticks;
        this.numberOfPrimarySticks = numberOfPrimarySticks;
        this.icingMode = 0;
        this.isInIcingMode = false;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfSticks() {
        return numberOfSticks;
    }

    public int getNumberOfPrimarySticks() {
        return numberOfPrimarySticks;
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
}
