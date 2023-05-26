package model;

import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Level {
    public final static int GAME_TIME = 2;
    public final static double LEVEL_X = 300;
    public final static double LEVEL_Y = 300;
    private final int difficulty;
    private final int numberOfBalls;
    private int numberOfConnectedBalls1;
    private int numberOfConnectedBalls2;
    private final int numberOfPrimaryBalls;
    private final int mapNumber;
    private final boolean isSinglePlayer;
    private int icingMode;
    private boolean isInIcingMode;
    private Scene lastScene;
    private AnchorPane lastGamePane;
    private ArrayList<Transition> allAnimations;
    private ArrayList<Ball> connectedBalls;
    private int resultIndex;
    private int pauseIndex;
    private int keyboardIndex;
    private int musicIndex;
    private int minutes;
    private int seconds;
    private int score;
    private boolean isFinished;
    private boolean isWinner;
    private final Rotate rotate;

    public Level(int difficulty, int numberOfBalls, int numberOfPrimaryBalls, int mapNumber, boolean isSinglePlayer, double rotationX, double rotationY) {
        this.difficulty = difficulty;
        this.numberOfBalls = numberOfBalls;
        this.numberOfConnectedBalls1 = 0;
        this.numberOfConnectedBalls2 = 0;
        this.numberOfPrimaryBalls = numberOfPrimaryBalls;
        this.mapNumber = mapNumber;
        this.isSinglePlayer = isSinglePlayer;
        this.icingMode = 0;
        this.isInIcingMode = false;
        this.lastGamePane = null;
        this.minutes = 0;
        this.seconds = 0;
        this.score = 0;
        this.isFinished = false;
        this.isWinner = false;
        this.rotate = new Rotate(difficulty, rotationY, rotationY);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public int getNumberOfConnectedBalls1() {
        return numberOfConnectedBalls1;
    }

    public void setNumberOfConnectedBalls1(int numberOfConnectedBalls1) {
        this.numberOfConnectedBalls1 = numberOfConnectedBalls1;
    }

    public int getNumberOfConnectedBalls2() {
        return numberOfConnectedBalls2;
    }

    public void setNumberOfConnectedBalls2(int numberOfConnectedBalls2) {
        this.numberOfConnectedBalls2 = numberOfConnectedBalls2;
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
        // TODO
        this.score += 10;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public int getPauseIndex() {
        return pauseIndex;
    }

    public void setPauseIndex(int pauseIndex) {
        this.pauseIndex = pauseIndex;
    }

    public int getKeyboardIndex() {
        return keyboardIndex;
    }

    public void setKeyboardIndex(int keyboardIndex) {
        this.keyboardIndex = keyboardIndex;
    }

    public int getMusicIndex() {
        return musicIndex;
    }

    public void setMusicIndex(int musicIndex) {
        this.musicIndex = musicIndex;
    }

    public Scene getLastScene() {
        return lastScene;
    }

    public void setLastScene(Scene lastScene) {
        this.lastScene = lastScene;
    }

    public ArrayList<Transition> getAllAnimations() {
        return allAnimations;
    }

    public void setAllAnimations(ArrayList<Transition> allAnimations) {
        this.allAnimations = allAnimations;
    }

    public ArrayList<Ball> getConnectedBalls() {
        return connectedBalls;
    }

    public void setConnectedBalls(ArrayList<Ball> connectedBalls) {
        this.connectedBalls = connectedBalls;
    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public Rotate getRotate() {
        return rotate;
    }
}
