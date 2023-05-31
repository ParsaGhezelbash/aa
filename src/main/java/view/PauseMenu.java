package view;

import controller.Controller;
import javafx.animation.Transition;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Game;
import model.Level;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class PauseMenu {
    private final Controller controller;
    private InGameMenu inGameMenu;
    private AnchorPane pauseMenuPane, keyboardMenuPane, musicMenuPane;
    private Button resumeButton, keyboardButton, changeMusicButton, saveButton, restartButton, exitButton;
    private Circle mutedCircle, unMutedCircle;

    public PauseMenu(Controller controller, InGameMenu inGameMenu) throws IOException {
        this.controller = controller;
        this.inGameMenu = inGameMenu;

        pauseMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/PauseMenu.fxml")).toExternalForm()));

        inGameMenu.getInGameMenuPane().getChildren().remove(pauseMenuPane);
        inGameMenu.addPaneToPane(inGameMenu.getInGameMenuPane(), pauseMenuPane, 1);

        keyboardMenuPane = setKeyboardMenuPane(pauseMenuPane);

        musicMenuPane = setMusicMenuPane(pauseMenuPane);

        resumeButton = (Button) pauseMenuPane.getChildren().get(2);
        resumeButton.setOnMouseClicked(mouseEvent -> {
            pauseMenuPane.setVisible(false);
            for (Transition animation : inGameMenu.getAnimations().getAllAnimations()) {
                animation.play();
            }
            inGameMenu.getTimeline().play();
            inGameMenu.getCurrentBall1().requestFocus();
        });

        keyboardButton = (Button) pauseMenuPane.getChildren().get(3);
        keyboardButton.setOnMouseClicked(mouseEvent -> {
            keyboardMenuPane.setVisible(true);
            keyboardMenuPane.toFront();
        });

        changeMusicButton = (Button) pauseMenuPane.getChildren().get(4);
        changeMusicButton.setOnMouseClicked(mouseEvent -> {
            musicMenuPane.setVisible(true);
            musicMenuPane.toFront();
        });

        saveButton = (Button) pauseMenuPane.getChildren().get(5);
        saveButton.setOnMouseClicked(mouseEvent -> {
            inGameMenu.getLevel().setResultIndex(inGameMenu.getInGameMenuPane().getChildren().indexOf(inGameMenu.getResultPane()));
            inGameMenu.getLevel().setPauseIndex(inGameMenu.getInGameMenuPane().getChildren().indexOf(pauseMenuPane));
            inGameMenu.getLevel().setKeyboardIndex(pauseMenuPane.getChildren().indexOf(keyboardMenuPane));
            inGameMenu.getLevel().setMusicIndex(pauseMenuPane.getChildren().indexOf(musicMenuPane));
            controller.getGame().getCurrentUser().setLastLevel(inGameMenu.getLevel());
        });

        restartButton = (Button) pauseMenuPane.getChildren().get(6);
        restartButton.setOnMouseClicked(mouseEvent -> {
            try {
                inGameMenu.setLevel(new Level(controller.getGame().getDifficulty(), controller.getGame().getNumberOfBalls(), controller.getGame().getNumberOfPrimaryBalls(),
                        controller.getGame().getMapNumber(), inGameMenu.getLevel().isSinglePlayer()));
                inGameMenu.start(inGameMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        exitButton = (Button) pauseMenuPane.getChildren().get(7);
        exitButton.setOnMouseClicked(mouseEvent -> {
            try {
                controller.getMainMenuController().getMainMenu().start(inGameMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        mutedCircle = new Circle(pauseMenuPane.getPrefWidth() - 30, pauseMenuPane.getPrefHeight() - 30, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Mute.png").toExternalForm()).toExternalForm())));
        unMutedCircle = new Circle(pauseMenuPane.getPrefWidth() - 30, pauseMenuPane.getPrefHeight() - 30, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Unmute.png").toExternalForm()).toExternalForm())));
        if (controller.getMusicController().getMediaPlayer().isMute()) {
            pauseMenuPane.getChildren().add(mutedCircle);
        } else {
            pauseMenuPane.getChildren().add(unMutedCircle);
        }
        mutedCircle.setOnMouseClicked(event -> {
            pauseMenuPane.getChildren().remove(mutedCircle);
            pauseMenuPane.getChildren().add(unMutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(false);
        });
        unMutedCircle.setOnMouseClicked(event -> {
            pauseMenuPane.getChildren().remove(unMutedCircle);
            pauseMenuPane.getChildren().add(mutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(true);
        });
    }

    private AnchorPane setKeyboardMenuPane(AnchorPane pauseMenuPane) throws IOException {
        System.out.println(pauseMenuPane.getChildren().size());
        AnchorPane keyboardMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/KeyboardMenu.fxml")).toExternalForm()));

        pauseMenuPane.getChildren().remove(keyboardMenuPane);
        inGameMenu.addPaneToPane(pauseMenuPane, keyboardMenuPane, 0);

        Label firstPlayerShootLabel, secondPlayerShootLabel, freezeMode, moveRightLabel, moveLeftLabel;
        firstPlayerShootLabel = (Label) keyboardMenuPane.getChildren().get(0);
        firstPlayerShootLabel.setText("First Player Shoot : " + controller.getGame().getFirstPlayerShoot().getName());
        firstPlayerShootLabel.setBackground(Background.fill(Color.WHITE));

        secondPlayerShootLabel = (Label) keyboardMenuPane.getChildren().get(1);
        secondPlayerShootLabel.setText("Second Player Shoot : " + controller.getGame().getSecondPlayerShoot().getName());
        secondPlayerShootLabel.setBackground(Background.fill(Color.WHITE));

        freezeMode = (Label) keyboardMenuPane.getChildren().get(2);
        freezeMode.setText("Freeze Mode : " + controller.getGame().getFreezeMode().getName());
        freezeMode.setBackground(Background.fill(Color.WHITE));

        moveRightLabel = (Label) keyboardMenuPane.getChildren().get(3);
        moveRightLabel.setText("Move Right : " + controller.getGame().getMoveRight1().getName());
        moveRightLabel.setBackground(Background.fill(Color.WHITE));

        moveLeftLabel = (Label) keyboardMenuPane.getChildren().get(4);
        moveLeftLabel.setText("Move Left : " + controller.getGame().getMoveLeft1().getName());
        moveLeftLabel.setBackground(Background.fill(Color.WHITE));

        Button backButton = (Button) keyboardMenuPane.getChildren().get(5);
        backButton.setOnMouseClicked(mouseEvent -> {
            keyboardMenuPane.setVisible(false);
        });

        return keyboardMenuPane;
    }

    private AnchorPane setMusicMenuPane(AnchorPane pauseMenuPane) throws IOException {
        AnchorPane musicMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MusicMenu.fxml")).toExternalForm()));

        pauseMenuPane.getChildren().remove(musicMenuPane);
        inGameMenu.addPaneToPane(pauseMenuPane, musicMenuPane, 1);
        Button musicButton1 = (Button) musicMenuPane.getChildren().get(0);
        musicButton1.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(1);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton2 = (Button) musicMenuPane.getChildren().get(1);
        musicButton2.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(2);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton3 = (Button) musicMenuPane.getChildren().get(2);
        musicButton3.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(3);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button backButton = (Button) musicMenuPane.getChildren().get(3);
        backButton.setOnMouseClicked(mouseEvent -> {
            musicMenuPane.setVisible(false);
        });

        return musicMenuPane;
    }

    public AnchorPane getPauseMenuPane() {
        return pauseMenuPane;
    }
}
