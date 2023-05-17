package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    private final ArrayList<User> users;
    private final LinkedList<User> scoreBoard;
    private User currentUser;
    private final ArrayList<Image> images;

    private boolean isSoundMuted = false;

    public Game() {
        this.users = new ArrayList<>();
        scoreBoard = new LinkedList<>();
        this.currentUser = null;
        images = new ArrayList<>();
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
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Image getRandomImage() {
        return images.get(new Random().nextInt(images.size()));
    }

    public boolean isSoundMuted() {
        return isSoundMuted;
    }

    public void setSoundMuted(boolean soundMuted) {
        isSoundMuted = soundMuted;
    }
}
