package model;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.net.URL;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String profileAddress;
//    private ImagePattern avatar;
    private Level lastLevel;
    private int sec1, sec2, sec3;

    public User(String username, String password, String profileAddress) {
        this.username = username;
        this.password = password;
        this.profileAddress = profileAddress;
//        this.avatar = avatar;
        this.lastLevel = null;
        this.sec1 = 0;
        this.sec2 = 0;
        this.sec3 = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profileAddress = null;
//        this.avatar = null;
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
        return new ImagePattern(new Image(profileAddress));
    }

    public void setAvatar(String profileAddress) {
        this.profileAddress = profileAddress;
    }

    public Level getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(Level lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getSec1() {
        return sec1;
    }

    public void setSec1(int sec1) {
        this.sec1 = sec1;
    }

    public int getSec2() {
        return sec2;
    }

    public void setSec2(int sec2) {
        this.sec2 = sec2;
    }

    public int getSec3() {
        return sec3;
    }

    public void setSec3(int sec3) {
        this.sec3 = sec3;
    }
}
