package model;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private final int[] rotationSpeed = {5, 10, 15};
    private final double[] windSpeed = {1.2, 1.5, 1.8};
    private final int[] icingTime = {7, 5, 3};
    private int difficulty;
    private int numberOfBalls;
    private int numberOfPrimaryBalls;
    private int mapNumber;
    private final ArrayList<User> users;
    private final LinkedList<User> scoreBoard;
    private User currentUser;
    private boolean isSoundMuted = false;
    private KeyCode firstPlayerShoot, secondPlayerShoot, freezeMode, moveRight, moveLeft;


    public Game() {
        this.users = new ArrayList<>();
        this.scoreBoard = new LinkedList<>();
        this.currentUser = null;
        this.difficulty = 2;
        this.numberOfBalls = 10;
        this.numberOfPrimaryBalls = 0;
        this.firstPlayerShoot = KeyCode.SPACE;
        this.secondPlayerShoot = KeyCode.ENTER;
        this.freezeMode = KeyCode.TAB;
        this.moveRight = KeyCode.RIGHT;
        this.moveLeft = KeyCode.LEFT;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }

    public int getNumberOfPrimaryBalls() {
        return numberOfPrimaryBalls;
    }

    public void setNumberOfPrimaryBalls(int numberOfPrimaryBalls) {
        this.numberOfPrimaryBalls = numberOfPrimaryBalls;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
        refreshScoreBoard(user);
    }

    private void refreshScoreBoard(User user) {
        if (scoreBoard.contains(user)) scoreBoard.remove(user);
        for (int i = 0; i < scoreBoard.size(); i++) {
            if (user.getHighScore() > scoreBoard.get(i).getHighScore()) {
                scoreBoard.add(i + 1, user);
                break;
            }
        }
        if (!scoreBoard.contains(user) && scoreBoard.size() < 10) scoreBoard.add(user);
        if (scoreBoard.size() > 10) scoreBoard.removeLast();
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public LinkedList<User> getScoreBoard() {
        return scoreBoard;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isSoundMuted() {
        return isSoundMuted;
    }

    public void setSoundMuted(boolean soundMuted) {
        isSoundMuted = soundMuted;
    }

    public KeyCode getFirstPlayerShoot() {
        return firstPlayerShoot;
    }

    public void setFirstPlayerShoot(KeyCode firstPlayerShoot) {
        this.firstPlayerShoot = firstPlayerShoot;
    }

    public KeyCode getSecondPlayerShoot() {
        return secondPlayerShoot;
    }

    public void setSecondPlayerShoot(KeyCode secondPlayerShoot) {
        this.secondPlayerShoot = secondPlayerShoot;
    }

    public KeyCode getFreezeMode() {
        return freezeMode;
    }

    public void setFreezeMode(KeyCode freezeMode) {
        this.freezeMode = freezeMode;
    }

    public KeyCode getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(KeyCode moveRight) {
        this.moveRight = moveRight;
    }

    public KeyCode getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(KeyCode moveLeft) {
        this.moveLeft = moveLeft;
    }
}
