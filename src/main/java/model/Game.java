package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;

import java.io.*;
import java.util.*;

public class Game {
    public static final int DISTANCE = 160;
    private int difficulty;
    private int numberOfBalls;
    private int numberOfPrimaryBalls;
    private int mapNumber;
    private final ArrayList<User> users;
    private final LinkedList<User> scoreBoard1, scoreBoard2, scoreBoard3;
    private User currentUser;
    private boolean isSoundMuted = false;
    private KeyCode firstPlayerShoot, secondPlayerShoot, freezeMode, moveRight1, moveLeft1, moveRight2, moveLeft2;
    private final MediaPlayer music1, music2, music3, shootSound;


    public Game() throws IOException {
        File usersFile = new File("Users.json");
        if (usersFile.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(usersFile));
            users = new Gson().fromJson(fileReader, new TypeToken<ArrayList<User>>() {
            }.getType());
            fileReader.close();
        } else {
            users = new ArrayList<>();
            FileWriter fileWriter = new FileWriter(usersFile);
            fileWriter.close();
        }

        this.scoreBoard1 = new LinkedList<>();
        this.scoreBoard2 = new LinkedList<>();
        this.scoreBoard3 = new LinkedList<>();
        this.currentUser = null;
        this.difficulty = 2;
        this.numberOfBalls = 12;
        this.numberOfPrimaryBalls = 1;
        this.firstPlayerShoot = KeyCode.SPACE;
        this.secondPlayerShoot = KeyCode.ENTER;
        this.freezeMode = KeyCode.M;
        this.moveRight1 = KeyCode.V;
        this.moveLeft1 = KeyCode.Z;
        this.moveRight2 = KeyCode.D;
        this.moveLeft2 = KeyCode.A;
        this.music1 = new MediaPlayer(new Media(getClass().getResource("/sound/Music1.mp3").toString()));
        music1.setCycleCount(MediaPlayer.INDEFINITE);
        music1.setVolume(0.1);
        this.music2 = new MediaPlayer(new Media(getClass().getResource("/sound/Music2.mp3").toString()));
        music2.setCycleCount(MediaPlayer.INDEFINITE);
        music2.setVolume(0.1);
        this.music3 = new MediaPlayer(new Media(getClass().getResource("/sound/Music3.mp3").toString()));
        music3.setCycleCount(MediaPlayer.INDEFINITE);
        music3.setVolume(0.1);
        this.shootSound = new MediaPlayer(new Media(getClass().getResource("/sound/Shoot.mp3").toString()));
        music3.setVolume(0.7);
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

    public void addUser(User user) throws IOException {
        users.add(user);
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter("Users.json");
        fileWriter.write(gson.toJson(users));
        fileWriter.close();
        refreshScoreBoard(scoreBoard1, user);
        refreshScoreBoard(scoreBoard2, user);
        refreshScoreBoard(scoreBoard3, user);
    }

    public void refreshScoreBoard(LinkedList<User> scoreBoard, User user) {
        if (scoreBoard.contains(user)) scoreBoard.remove(user);
        for (int i = 0; i < scoreBoard.size(); i++) {
            if (user.getSec1() < scoreBoard.get(i).getSec1()) {
                scoreBoard.add(i + 1, user);
                break;
            }
        }
        if (!scoreBoard.contains(user) && scoreBoard.size() < 10) scoreBoard.add(user);
        if (scoreBoard.size() > 10) scoreBoard.removeLast();
    }

    public void removeUser(User user) {
        users.remove(user);
        scoreBoard1.remove(user);
        scoreBoard2.remove(user);
        scoreBoard3.remove(user);
    }

    public LinkedList<User> getScoreBoard1() {
        return scoreBoard1;
    }

    public LinkedList<User> getScoreBoard2() {
        return scoreBoard2;
    }

    public LinkedList<User> getScoreBoard3() {
        return scoreBoard3;
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

    public KeyCode getMoveRight1() {
        return moveRight1;
    }

    public void setMoveRight1(KeyCode moveRight1) {
        this.moveRight1 = moveRight1;
    }

    public KeyCode getMoveLeft1() {
        return moveLeft1;
    }

    public void setMoveLeft1(KeyCode moveLeft1) {
        this.moveLeft1 = moveLeft1;
    }

    public KeyCode getMoveRight2() {
        return moveRight2;
    }

    public void setMoveRight2(KeyCode moveRight2) {
        this.moveRight2 = moveRight2;
    }

    public KeyCode getMoveLeft2() {
        return moveLeft2;
    }

    public void setMoveLeft2(KeyCode moveLeft2) {
        this.moveLeft2 = moveLeft2;
    }

    public MediaPlayer getMusic1() {
        return music1;
    }

    public MediaPlayer getMusic2() {
        return music2;
    }

    public MediaPlayer getMusic3() {
        return music3;
    }

    public MediaPlayer getShootSound() {
        return shootSound;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
