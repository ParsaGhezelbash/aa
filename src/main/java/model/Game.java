package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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


    public Game() {
        this.users = new ArrayList<>();
        this.scoreBoard = new LinkedList<>();
        this.currentUser = null;
        this.difficulty = 2;
        this.numberOfBalls = 5;
        this.numberOfPrimaryBalls = 0;
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
}
