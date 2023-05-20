package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    private final ArrayList<User> users;
    private final LinkedList<User> scoreBoard;
    private User currentUser;

    private boolean isSoundMuted = false;

    public Game() {
        this.users = new ArrayList<>();
        scoreBoard = new LinkedList<>();
        this.currentUser = null;
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
