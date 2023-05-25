package view;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.Game;
import model.Level;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class InGameMenu extends Application {
    private Controller controller;
    private Level level;
    private Ball currentBall;
    private Ball mainCircle, invisibleCircle;
    private Label usernameLabel, scoreLabel, ballCountLabel1, ballCountLabel2, timerLabel, difficultyLabel;
    private Label resultLabel, resultTimeLabel, resultScoreLabel;
    private Timeline timeline;
    private ProgressBar icingModeProgressBar;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        ArrayList<Ball> primaryBalls = new ArrayList<>();
        ArrayList<Ball> connectedBalls = new ArrayList<>();
        ArrayList<Transition> allAnimations = new ArrayList<>();
        AnchorPane inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        inGameMenuPane.setBackground(Background.fill(Color.WHITE));
        AnchorPane pauseMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/PauseMenu.fxml")).toExternalForm()));
        AnchorPane resultMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ResultMenu.fxml")).toExternalForm()));

        setPauseMenuPane(inGameMenuPane, pauseMenuPane, allAnimations);
        setResultMenuPane(inGameMenuPane, resultMenuPane);

        setCircles(inGameMenuPane);

        usernameLabel = (Label) inGameMenuPane.getChildren().get(4);
        usernameLabel.setText("Username : " + controller.getGame().getCurrentUser().getUsername());

        scoreLabel = (Label) inGameMenuPane.getChildren().get(5);
        scoreLabel.setText("Score : " + level.getScore());

        ballCountLabel1 = (Label) inGameMenuPane.getChildren().get(6);
        ballCountLabel1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));

        timerLabel = (Label) inGameMenuPane.getChildren().get(7);
        setTimerLabel(inGameMenuPane, resultMenuPane, allAnimations);

        difficultyLabel = (Label) inGameMenuPane.getChildren().get(8);
        difficultyLabel.setText("Difficulty : " + level.getDifficulty());

        setMainCircleCounter(inGameMenuPane);

        Button pauseButton = (Button) inGameMenuPane.getChildren().get(10);
        setPauseButton(pauseMenuPane, pauseButton, allAnimations);

        currentBall = createBall(inGameMenuPane, resultMenuPane, connectedBalls, allAnimations, 1);

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        currentBall.requestFocus();
        stage.show();
    }

    private void setCircles(AnchorPane inGameMenuPane) {
        invisibleCircle = new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        invisibleCircle.setRadius(180);
        invisibleCircle.setFill(Color.WHITE);
        invisibleCircle.setStroke(null);
        inGameMenuPane.getChildren().add(2, invisibleCircle);
        mainCircle = new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        mainCircle.setRadius(40);
        mainCircle.setFill(Color.BLACK);
        mainCircle.setStroke(null);
        inGameMenuPane.getChildren().add(3, mainCircle);
    }

    private void setPauseMenuPane(AnchorPane inGameMenuPane, AnchorPane pauseMenuPane, ArrayList<Transition> allAnimations) throws IOException {
        addPaneToPane(inGameMenuPane, pauseMenuPane, 0);
        AnchorPane keyboardMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/KeyboardMenu.fxml")).toExternalForm()));
        keyboardMenuPane.setBackground(Background.fill(Color.WHITE));
        AnchorPane musicMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MusicMenu.fxml")).toExternalForm()));
        musicMenuPane.setBackground(Background.fill(Color.WHITE));

        setKeyboardMenuPane(pauseMenuPane, keyboardMenuPane);
        setMusicMenuPane(pauseMenuPane, musicMenuPane);

        Button resumeButton = (Button) pauseMenuPane.getChildren().get(2);
        resumeButton.setOnMouseClicked(mouseEvent -> {
            pauseMenuPane.setVisible(false);
            for (Transition animation : allAnimations) {
                animation.play();
            }
            timeline.play();
            currentBall.requestFocus();
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
            controller.getGame().getCurrentUser().setLastLevel(level);
        });

        Button restartButton = (Button) pauseMenuPane.getChildren().get(6);
        restartButton.setOnMouseClicked(mouseEvent -> {
            try {
                this.setLevel(new Level(controller.getGame().getDifficulty(), controller.getGame().getNumberOfBalls(), controller.getGame().getNumberOfPrimaryBalls(),
                        controller.getGame().getMapNumber()));
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
        pauseMenuPane.getChildren().add(mutedCircle);
        mutedCircle.setOnMouseClicked(event -> {
            pauseMenuPane.getChildren().remove(mutedCircle);
            pauseMenuPane.getChildren().add(unMutedCircle);
        });
        unMutedCircle.setOnMouseClicked(event -> {
            pauseMenuPane.getChildren().remove(unMutedCircle);
            pauseMenuPane.getChildren().add(mutedCircle);
        });
    }

    private void setKeyboardMenuPane(AnchorPane pauseMenuPane, AnchorPane keyboardMenuPane) {
        addPaneToPane(pauseMenuPane, keyboardMenuPane, 0);

        Label firstPlayerShootLabel, secondPlayerShootLabel, freezeMode, moveRightLabel, moveLeftLabel;
        firstPlayerShootLabel = (Label) keyboardMenuPane.getChildren().get(0);
        firstPlayerShootLabel.setText("First Player Shoot : " + controller.getGame().getFirstPlayerShoot().getName());
        secondPlayerShootLabel = (Label) keyboardMenuPane.getChildren().get(1);
        secondPlayerShootLabel.setText("Second Player Shoot : " + controller.getGame().getSecondPlayerShoot().getName());
        freezeMode = (Label) keyboardMenuPane.getChildren().get(2);
        freezeMode.setText("Freeze Mode : " + controller.getGame().getFreezeMode().getName());
        moveRightLabel = (Label) keyboardMenuPane.getChildren().get(3);
        moveRightLabel.setText("Move Right : " + controller.getGame().getMoveRight().getName());
        moveLeftLabel = (Label) keyboardMenuPane.getChildren().get(4);
        moveLeftLabel.setText("Move Left : " + controller.getGame().getMoveLeft().getName());
        Button backButton = (Button) keyboardMenuPane.getChildren().get(5);
        backButton.setOnMouseClicked(mouseEvent -> {
            keyboardMenuPane.setVisible(false);
        });
    }

    private void setMusicMenuPane(AnchorPane pauseMenuPane, AnchorPane musicMenuPane) {
        addPaneToPane(pauseMenuPane, musicMenuPane, 1);
    }

    private void setResultMenuPane(AnchorPane inGameMenuPane, AnchorPane resultMenuPane) {
        resultMenuPane.setLayoutX(inGameMenuPane.getPrefWidth() / 2 - resultMenuPane.getPrefWidth() / 2);
        resultMenuPane.setLayoutY(inGameMenuPane.getPrefHeight() / 2 - resultMenuPane.getPrefHeight() / 2);
        resultMenuPane.setVisible(false);
        inGameMenuPane.getChildren().add(1, resultMenuPane);

        resultLabel = (Label) resultMenuPane.getChildren().get(0);
        resultLabel.setBackground(Background.fill(Color.WHITE));
        resultTimeLabel = (Label) resultMenuPane.getChildren().get(1);
        resultTimeLabel.setBackground(Background.fill(Color.WHITE));
        resultScoreLabel = (Label) resultMenuPane.getChildren().get(2);
        resultScoreLabel.setBackground(Background.fill(Color.WHITE));
        Button enterMainMenu = (Button) resultMenuPane.getChildren().get(3);
        enterMainMenu.setOnMouseClicked(mouseEvent -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addPaneToPane(Pane pane, Pane paneToAdd, int index) {
        paneToAdd.setLayoutX(pane.getPrefWidth() / 2 - paneToAdd.getPrefWidth() / 2);
        paneToAdd.setLayoutY(pane.getPrefHeight() / 2 - paneToAdd.getPrefHeight() / 2);
        paneToAdd.setVisible(false);
        pane.getChildren().add(index, paneToAdd);
    }

    private void setMainCircleCounter(AnchorPane inGameMenuPane) {
        ballCountLabel2 = new Label();
        ballCountLabel2.setPrefWidth(60);
        ballCountLabel2.setPrefHeight(60);
        ballCountLabel2.setLayoutX(mainCircle.getX() - ballCountLabel2.getPrefWidth() / 2);
        ballCountLabel2.setLayoutY(mainCircle.getY() - ballCountLabel2.getPrefHeight() / 2);
        ballCountLabel2.setFont(Font.font(ballCountLabel2.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 32));
        ballCountLabel2.setTextFill(Color.WHITE);
        ballCountLabel2.setText(String.valueOf((level.getNumberOfBalls() - level.getNumberOfConnectedBalls())));
        ballCountLabel2.setAlignment(Pos.CENTER);
//        ballCountLabel2.setTextAlignment(TextAlignment.CENTER);
        inGameMenuPane.getChildren().add(9, ballCountLabel2);
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

    private void shoot(AnchorPane anchorPane, AnchorPane resultMenuPane, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(anchorPane, resultMenuPane, level, currentBall, invisibleCircle, mainCircle,
                connectedBalls, allAnimations, ballCountLabel1, ballCountLabel2, scoreLabel, timeline, resultLabel, resultTimeLabel, resultScoreLabel);
        allAnimations.add(shootingAnimation);
        shootingAnimation.play();
        currentBall = createBall(anchorPane, resultMenuPane, connectedBalls, allAnimations, currentBall.getNumber() + 1);
    }

    private Ball createBall(AnchorPane anchorPane, AnchorPane resultMenuPane, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations, int number) {
        Ball ball = new Ball(anchorPane.getPrefWidth() / 2, anchorPane.getPrefHeight() - 2 * Ball.RADIUS - 20, number);
        anchorPane.getChildren().add(ball);
        anchorPane.getChildren().add(ball.getNumberText());
        ball.requestFocus();
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE))
                    shoot(anchorPane, resultMenuPane, connectedBalls, allAnimations);
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
