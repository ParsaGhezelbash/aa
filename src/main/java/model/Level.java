package model;

import java.util.ArrayList;

public class Level {
    public final static int GAME_TIME = 2;
    public final static double LEVEL_X = 300;
    public final static double LEVEL_Y = 300;
    private final int difficulty;
    private final int numberOfBalls;
    private int numberOfConnectedBalls1;
    private int numberOfConnectedBalls2;
    private final ArrayList<double[]> connectedBallsDetails;
    private final int numberOfPrimaryBalls;
    private final int mapNumber;
    private final boolean isSinglePlayer;
    private double icingMode;
    private boolean isInIcingMode;
    private int resultIndex;
    private int pauseIndex;
    private int keyboardIndex;
    private int musicIndex;
    private int minutes;
    private int seconds;
    private int score;
    private boolean isFinished;
    private boolean isWinner;
    private double phase;
    private int wind;

    public Level(int difficulty, int numberOfBalls, int numberOfPrimaryBalls, int mapNumber, boolean isSinglePlayer) {
        this.difficulty = difficulty;
        this.numberOfBalls = numberOfBalls;
        this.numberOfConnectedBalls1 = 0;
        this.numberOfConnectedBalls2 = 0;
        this.numberOfPrimaryBalls = numberOfPrimaryBalls;
        this.connectedBallsDetails = new ArrayList<>();
        this.mapNumber = mapNumber;
        this.isSinglePlayer = isSinglePlayer;
        this.icingMode = 0;
        this.isInIcingMode = false;
        this.minutes = 0;
        this.seconds = 0;
        this.score = 0;
        this.isFinished = false;
        this.isWinner = false;
        this.phase = 0;
        this.wind = 0;
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

    public double getIcingMode() {
        return icingMode;
    }

    public void setIcingMode(double icingMode) {
        this.icingMode = icingMode;
    }

    public boolean isInIcingMode() {
        return isInIcingMode;
    }

    public void setInIcingMode(boolean inIcingMode) {
        isInIcingMode = inIcingMode;
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

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public static boolean areConnected(Ball ball1, Ball ball2) {
        double distance = Math.sqrt(Math.pow(ball1.getCenterX() - ball2.getCenterX(), 2) + Math.pow(ball1.getCenterY() - ball2.getCenterY(), 2));
        return distance <= (ball1.getRadius() + ball2.getRadius());
    }

    public void addConnectedBall(Ball ball) {
        if (ball.getPlayerNumber() == 1) numberOfConnectedBalls1++;
        if (ball.getPlayerNumber() == 2) numberOfConnectedBalls2++;
        connectedBallsDetails.add(new double[]{ball.getX(), ball.getY(), ball.getNumber(), ball.getPlayerNumber()});
    }

    public ArrayList<double[]> getConnectedBallsDetails() {
        return connectedBallsDetails;
    }
}
