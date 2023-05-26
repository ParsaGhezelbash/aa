package view;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.Game;
import model.Level;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class InGameMenu extends Application {
    private Controller controller;
    private Level level;
    private Ball currentBall1, currentBall2;
    private Ball mainCircle, invisibleCircle;
    private Label usernameLabel, scoreLabel, ballCountLabel1, ballCountLabel2, timerLabel, difficultyLabel;
    private Label resultLabel, resultTimeLabel, resultScoreLabel;
    private Timeline timeline;
    private ProgressBar icingModeProgressBar;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
//        ArrayList<Ball> primaryBalls = new ArrayList<>();
        ArrayList<Ball> connectedBalls = level.getLastGamePane() != null ? level.getConnectedBalls() : new ArrayList<>();
        ArrayList<Transition> allAnimations = level.getLastGamePane() != null ? level.getAllAnimations() : new ArrayList<>();
        for (Transition animation : allAnimations) {
            animation.play();
        }
        AnchorPane inGameMenuPane = level.getLastGamePane() != null ? level.getLastGamePane() : FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        inGameMenuPane.setBackground(Background.fill(Color.WHITE));

        AnchorPane resultMenuPane = setResultMenuPane(inGameMenuPane);
        AnchorPane pauseMenuPane = setPauseMenuPane(inGameMenuPane, resultMenuPane, allAnimations, connectedBalls);

        setCircles(inGameMenuPane);

        usernameLabel = (Label) inGameMenuPane.getChildren().get(4);
        usernameLabel.setText("Username : " + controller.getGame().getCurrentUser().getUsername());

        scoreLabel = (Label) inGameMenuPane.getChildren().get(5);
        scoreLabel.setText("Score : " + level.getScore());

        ballCountLabel1 = (Label) inGameMenuPane.getChildren().get(6);
        if (level.isSinglePlayer()) {
            ballCountLabel1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
        } else {
            ballCountLabel1.setText("Ball Count 1: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                    "\nBall Count 2: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
        }

        timerLabel = (Label) inGameMenuPane.getChildren().get(7);
        setTimerLabel(inGameMenuPane, resultMenuPane, allAnimations);

        difficultyLabel = (Label) inGameMenuPane.getChildren().get(8);
        difficultyLabel.setText("Difficulty : " + level.getDifficulty());

        setMainCircleCounter(inGameMenuPane);

        Button pauseButton = (Button) inGameMenuPane.getChildren().get(10);
        setPauseButton(pauseMenuPane, pauseButton, allAnimations);

        if (level.isSinglePlayer()) {
            currentBall1 = createBall(0, inGameMenuPane, resultMenuPane, connectedBalls, allAnimations, 1);
        } else {
            currentBall1 = createBall(1, inGameMenuPane, resultMenuPane, connectedBalls, allAnimations, 1);
            currentBall2 = createBall(2, inGameMenuPane, resultMenuPane, connectedBalls, allAnimations, 1);
        }

        Scene scene = level.getLastGamePane() != null ? level.getLastScene() : new Scene(inGameMenuPane);
        stage.setScene(scene);
        currentBall1.requestFocus();
        stage.show();
    }

    private void setCircles(AnchorPane inGameMenuPane) {
        invisibleCircle = level.getLastGamePane() != null ? (Ball) level.getLastGamePane().getChildren().get(2) :
                new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        invisibleCircle.setRadius(180);
        invisibleCircle.setFill(Color.WHITE);
        invisibleCircle.setStroke(null);
        mainCircle = level.getLastGamePane() != null ? (Ball) level.getLastGamePane().getChildren().get(3) :
                new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        mainCircle.setRadius(40);
        mainCircle.setFill(Color.BLACK);
        mainCircle.setStroke(null);
        if (level.getLastGamePane() == null) {
            inGameMenuPane.getChildren().add(2, invisibleCircle);
            inGameMenuPane.getChildren().add(3, mainCircle);
        }
    }

    private AnchorPane setResultMenuPane(AnchorPane inGameMenuPane) throws IOException {
        AnchorPane resultMenuPane = level.getLastGamePane() != null ? (AnchorPane) level.getLastGamePane().getChildren().get(level.getResultIndex()) :
                FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ResultMenu.fxml")).toExternalForm()));

        inGameMenuPane.getChildren().remove(resultMenuPane);
        addPaneToPane(inGameMenuPane, resultMenuPane, 0);

        resultLabel = (Label) resultMenuPane.getChildren().get(0);
        resultLabel.setBackground(Background.fill(Color.WHITE));
        resultTimeLabel = (Label) resultMenuPane.getChildren().get(1);
        resultTimeLabel.setBackground(Background.fill(Color.WHITE));
        resultScoreLabel = (Label) resultMenuPane.getChildren().get(2);
        resultScoreLabel.setBackground(Background.fill(Color.WHITE));
        Button enterMainMenu = (Button) resultMenuPane.getChildren().get(3);
        enterMainMenu.setOnMouseClicked(mouseEvent -> {
            try {
                controller.getGame().getCurrentUser().setLastLevel(null);
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return resultMenuPane;
    }

    private AnchorPane setPauseMenuPane(AnchorPane inGameMenuPane, AnchorPane resultMenuPane, ArrayList<Transition> allAnimations, ArrayList<Ball> connectedBalls) throws IOException {
        AnchorPane pauseMenuPane = level.getLastGamePane() != null ? (AnchorPane) level.getLastGamePane().getChildren().get(level.getPauseIndex()) :
                FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/PauseMenu.fxml")).toExternalForm()));

        inGameMenuPane.getChildren().remove(pauseMenuPane);
        addPaneToPane(inGameMenuPane, pauseMenuPane, 1);

        AnchorPane keyboardMenuPane = setKeyboardMenuPane(pauseMenuPane);

        AnchorPane musicMenuPane = setMusicMenuPane(pauseMenuPane);

        Button resumeButton = (Button) pauseMenuPane.getChildren().get(2);
        resumeButton.setOnMouseClicked(mouseEvent -> {
            pauseMenuPane.setVisible(false);
            for (Transition animation : allAnimations) {
                animation.play();
            }
            timeline.play();
            currentBall1.requestFocus();
        });

        Button keyboardButton = (Button) pauseMenuPane.getChildren().get(3);
        keyboardButton.setOnMouseClicked(mouseEvent -> {
            keyboardMenuPane.setVisible(true);
            keyboardMenuPane.toFront();
        });

        Button changeMusicButton = (Button) pauseMenuPane.getChildren().get(4);
        changeMusicButton.setOnMouseClicked(mouseEvent -> {
            musicMenuPane.setVisible(true);
            musicMenuPane.toFront();
        });

        Button saveButton = (Button) pauseMenuPane.getChildren().get(5);
        saveButton.setOnMouseClicked(mouseEvent -> {
            level.setLastScene(stage.getScene());
            level.setAllAnimations(allAnimations);
            level.setConnectedBalls(connectedBalls);
            level.setLastGamePane(inGameMenuPane);
            level.setResultIndex(inGameMenuPane.getChildren().indexOf(resultMenuPane));
            level.setPauseIndex(inGameMenuPane.getChildren().indexOf(pauseMenuPane));
            level.setKeyboardIndex(pauseMenuPane.getChildren().indexOf(keyboardMenuPane));
            level.setMusicIndex(pauseMenuPane.getChildren().indexOf(musicMenuPane));
            controller.getGame().getCurrentUser().setLastLevel(level);
        });

        Button restartButton = (Button) pauseMenuPane.getChildren().get(6);
        restartButton.setOnMouseClicked(mouseEvent -> {
            try {
                this.setLevel(new Level(controller.getGame().getDifficulty(), controller.getGame().getNumberOfBalls(), controller.getGame().getNumberOfPrimaryBalls(),
                        controller.getGame().getMapNumber(), level.isSinglePlayer()));
                this.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button exitButton = (Button) pauseMenuPane.getChildren().get(7);
        exitButton.setOnMouseClicked(mouseEvent -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Circle mutedCircle = new Circle(pauseMenuPane.getPrefWidth() - 30, pauseMenuPane.getPrefHeight() - 30, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Mute.png").toExternalForm()).toExternalForm())));
        Circle unMutedCircle = new Circle(pauseMenuPane.getPrefWidth() - 30, pauseMenuPane.getPrefHeight() - 30, 30,
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

        return pauseMenuPane;
    }

    private AnchorPane setKeyboardMenuPane(AnchorPane pauseMenuPane) throws IOException {
        System.out.println(pauseMenuPane.getChildren().size());
        AnchorPane keyboardMenuPane = level.getLastGamePane() != null ? (AnchorPane) pauseMenuPane.getChildren().get(level.getKeyboardIndex()) :
                FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/KeyboardMenu.fxml")).toExternalForm()));

        pauseMenuPane.getChildren().remove(keyboardMenuPane);
        addPaneToPane(pauseMenuPane, keyboardMenuPane, 0);

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
        moveRightLabel.setText("Move Right : " + controller.getGame().getMoveRight().getName());
        moveRightLabel.setBackground(Background.fill(Color.WHITE));

        moveLeftLabel = (Label) keyboardMenuPane.getChildren().get(4);
        moveLeftLabel.setText("Move Left : " + controller.getGame().getMoveLeft().getName());
        moveLeftLabel.setBackground(Background.fill(Color.WHITE));

        Button backButton = (Button) keyboardMenuPane.getChildren().get(5);
        backButton.setOnMouseClicked(mouseEvent -> {
            keyboardMenuPane.setVisible(false);
        });

        return keyboardMenuPane;
    }

    private AnchorPane setMusicMenuPane(AnchorPane pauseMenuPane) throws IOException {
        AnchorPane musicMenuPane = level.getLastGamePane() != null ? (AnchorPane) pauseMenuPane.getChildren().get(level.getMusicIndex()) :
                FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MusicMenu.fxml")).toExternalForm()));

        pauseMenuPane.getChildren().remove(musicMenuPane);
        addPaneToPane(pauseMenuPane, musicMenuPane, 1);
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

    private void addPaneToPane(Pane pane, Pane paneToAdd, int index) {
        paneToAdd.setLayoutX(pane.getPrefWidth() / 2 - paneToAdd.getPrefWidth() / 2);
        paneToAdd.setLayoutY(pane.getPrefHeight() / 2 - paneToAdd.getPrefHeight() / 2);
        paneToAdd.setVisible(false);
        pane.getChildren().add(index, paneToAdd);
    }

    private void setMainCircleCounter(AnchorPane inGameMenuPane) {
        ballCountLabel2 = level.getLastGamePane() != null ? (Label) level.getLastGamePane().getChildren().get(9) : new Label();
        ballCountLabel2.setPrefWidth(60);
        ballCountLabel2.setPrefHeight(60);
        ballCountLabel2.setLayoutX(mainCircle.getX() - ballCountLabel2.getPrefWidth() / 2);
        ballCountLabel2.setLayoutY(mainCircle.getY() - ballCountLabel2.getPrefHeight() / 2);
        ballCountLabel2.setFont(Font.font(ballCountLabel2.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 32));
        ballCountLabel2.setTextFill(Color.WHITE);
        if (level.isSinglePlayer()) {
            ballCountLabel2.setText(String.valueOf((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1())));
        } else {

            ballCountLabel2.setText((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                            " | " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
        }
        ballCountLabel2.setAlignment(Pos.CENTER);
        if (level.getLastGamePane() == null) inGameMenuPane.getChildren().add(9, ballCountLabel2);
    }

    private void setPauseButton(AnchorPane pauseMenuPane, Button pauseButton, ArrayList<Transition> allAnimations) {
        pauseButton.setOnAction(actionEvent -> {
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
            timeline.stop();
            pauseMenuPane.setVisible(true);
            pauseMenuPane.toFront();
            pauseMenuPane.requestFocus();
        });
    }

    private void setTimerLabel(AnchorPane inGameMenuPane, AnchorPane resultMenuPane, ArrayList<Transition> allAnimations) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> {
                    if (level.getSeconds() == 59) {
                        level.setSeconds(0);
                        level.setMinutes(level.getMinutes() + 1);
                    } else {
                        level.setSeconds(level.getSeconds() + 1);
                    }
                    if (level.getMinutes() == Level.GAME_TIME - 1 && level.getSeconds() == 59) {
                        level.setFinished(true);
                        level.setWinner(false);
                        for (Transition allAnimation : allAnimations) {
                            allAnimation.stop();
                        }
                        inGameMenuPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        invisibleCircle.setFill(Color.RED);
                        resultLabel.setText("You Lost!");
                        resultLabel.setTextFill(Color.RED);
                        resultScoreLabel.setText("Score : " + level.getScore());
                        resultTimeLabel.setText("Time : " + level.getMinutes() + " : " + level.getSeconds());
                        resultMenuPane.setVisible(true);
                        resultMenuPane.toFront();
                        resultMenuPane.requestFocus();
                        try {
                            timeline.stop();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    timerLabel.setText((Level.GAME_TIME - level.getMinutes() - 1) + " : " + (60 - level.getSeconds()));
                }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void shoot(AnchorPane anchorPane, AnchorPane resultMenuPane, Ball shootingBall, ArrayList<Ball> connectedBalls,
                       ArrayList<Transition> allAnimations) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(anchorPane, resultMenuPane, level, shootingBall,
                invisibleCircle, mainCircle, connectedBalls, allAnimations, ballCountLabel1, ballCountLabel2, scoreLabel,
                timeline, resultLabel, resultTimeLabel, resultScoreLabel);
        allAnimations.add(shootingAnimation);
        shootingAnimation.play();
        if (shootingBall.getPlayerNumber() != 2) {
            currentBall1 = createBall(shootingBall.getPlayerNumber(), anchorPane, resultMenuPane, connectedBalls, allAnimations,
                    shootingBall.getNumber() + 1);
        } else {
            currentBall2 = createBall(shootingBall.getPlayerNumber(), anchorPane, resultMenuPane, connectedBalls, allAnimations,
                    shootingBall.getNumber() + 1);
        }
    }

    private Ball createBall(int playerNumber, AnchorPane anchorPane, AnchorPane resultMenuPane, ArrayList<Ball> connectedBalls,
                            ArrayList<Transition> allAnimations, int number) {
        Ball ball = new Ball(anchorPane.getPrefWidth() / 2, playerNumber != 2 ?
                anchorPane.getPrefHeight() - 2 * Ball.RADIUS - 20 : 2 * Ball.RADIUS + 20 , number, playerNumber);
        anchorPane.getChildren().add(ball);
        anchorPane.getChildren().add(ball.getNumberText());
//        ball.requestFocus();
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(playerNumber != 2 ? controller.getGame().getFirstPlayerShoot() :
                        controller.getGame().getSecondPlayerShoot()))
                    shoot(anchorPane, resultMenuPane, ball, connectedBalls, allAnimations);
            }
        });
        return ball;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}