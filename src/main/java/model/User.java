package model;


import javafx.scene.image.Image;

public class User {
    private String username;
    private String password;
    private Image avatar;
    private Level lastLevel;
    private int highScore;


    public User(String username, String password, Image avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        lastLevel = null;
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

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Level getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(Level lastLevel) {
        this.lastLevel = lastLevel;
    }
}
