package model;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class User {
    private String username;
    private String password;
    private ImagePattern avatar;
    private Level lastLevel;
    private int highScore;


    public User(String username, String password, ImagePattern avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.lastLevel = null;
        this.highScore = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.avatar = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImagePattern getAvatar() {
        return avatar;
    }

    public void setAvatar(ImagePattern avatar) {
        this.avatar = avatar;
    }

    public Level getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(Level lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getHighScore() {
        return highScore;
    }
}
