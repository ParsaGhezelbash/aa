package view;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private Timeline timeline;
    private ProgressBar icingModeProgressBar;

    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        ArrayList<Ball> primaryBalls = new ArrayList<>();
        ArrayList<Ball> connectedBalls = new ArrayList<>();
        ArrayList<Transition> allAnimations = new ArrayList<>();
        AnchorPane pauseMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/PauseMenu.fxml")).toExternalForm()));
        AnchorPane inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        inGameMenuPane.getChildren().add(0, pauseMenuPane);
        setPauseMenuPane(pauseMenuPane);
        pauseMenuPane.setLayoutX(inGameMenuPane.getPrefWidth() / 2 - pauseMenuPane.getPrefWidth() / 2);
        pauseMenuPane.setLayoutY(inGameMenuPane.getPrefHeight() / 2 - pauseMenuPane.getPrefHeight() / 2);
        pauseMenuPane.setVisible(false);

        setCircles(inGameMenuPane);

        usernameLabel = (Label) inGameMenuPane.getChildren().get(3);
        usernameLabel.setText("Username : " + controller.getGame().getCurrentUser().getUsername());

        scoreLabel = (Label) inGameMenuPane.getChildren().get(4);
        scoreLabel.setText("Score : " + level.getScore());

        ballCountLabel1 = (Label) inGameMenuPane.getChildren().get(5);
        ballCountLabel1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls()));

        timerLabel = (Label) inGameMenuPane.getChildren().get(6);
        setTimerLabel();

        difficultyLabel = (Label) inGameMenuPane.getChildren().get(7);
        difficultyLabel.setText("Difficulty : " + level.getDifficulty());

        setMainCircleCounter(inGameMenuPane);

        Button pauseButton = (Button) inGameMenuPane.getChildren().get(9);
        setPauseButton(pauseMenuPane, pauseButton, allAnimations);

        currentBall = createBall(inGameMenuPane, connectedBalls, allAnimations, 1);

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        currentBall.requestFocus();
        stage.show();
    }

    private void setCircles(AnchorPane inGameMenuPane) {
        invisibleCircle = new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        invisibleCircle.setRadius(180);
        invisibleCircle.setFill(Color.web("#f4f4f4"));
        invisibleCircle.setStroke(null);
        inGameMenuPane.getChildren().add(1, invisibleCircle);
        mainCircle = new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        mainCircle.setRadius(40);
        mainCircle.setFill(Color.BLACK);
        mainCircle.setStroke(null);
        inGameMenuPane.getChildren().add(2, mainCircle);
    }

    private void setPauseMenuPane(AnchorPane pauseMenuPane) {
        Button resumeButton = (Button) pauseMenuPane.getChildren().get(0);
        resumeButton.setOnMouseClicked(mouseEvent -> {
            pauseMenuPane.setVisible(false);
            currentBall.requestFocus();
        });
        Button KeyboardButton = (Button) pauseMenuPane.getChildren().get(1);
        // TODO: 6/30/2021
        Button changeMusicButton = (Button) pauseMenuPane.getChildren().get(2);
        // TODO: 6/30/2021
        Button saveButton = (Button) pauseMenuPane.getChildren().get(3);
        saveButton.setOnMouseClicked(mouseEvent -> {
            controller.getGame().getCurrentUser().setLastLevel(level);
        });
        Button restartButton = (Button) pauseMenuPane.getChildren().get(4);
        restartButton.setOnMouseClicked(mouseEvent -> {
            try {
                this.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button exitButton = (Button) pauseMenuPane.getChildren().get(5);
        exitButton.setOnMouseClicked(mouseEvent -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setMainCircleCounter(AnchorPane inGameMenuPane) {
        ballCountLabel2 = new Label();
        ballCountLabel2.setPrefWidth(100);
        ballCountLabel2.setPrefHeight(30);
        ballCountLabel2.setLayoutX(mainCircle.getX() - ballCountLabel2.getPrefWidth() / 2);
        ballCountLabel2.setLayoutY(mainCircle.getY() - ballCountLabel2.getPrefHeight() / 2);
        ballCountLabel2.setFont(Font.font(ballCountLabel2.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, 32));
        ballCountLabel2.setTextFill(Color.WHITE);
        ballCountLabel2.setText(String.valueOf((level.getNumberOfBalls() - level.getNumberOfConnectedBalls())));
        ballCountLabel2.setTextAlignment(TextAlignment.LEFT);
        inGameMenuPane.getChildren().add(8, ballCountLabel2);
    }

    private void setPauseButton(AnchorPane pauseMenuPane, Button pauseButton, ArrayList<Transition> allAnimations) {
        pauseButton.setOnAction(actionEvent -> {
            for (Transition allAnimation : allAnimations) {
                allAnimation.stop();
            }
            pauseMenuPane.setVisible(true);
            pauseMenuPane.toFront();
            pauseMenuPane.requestFocus();
        });
    }

    private void setTimerLabel() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> {
                    if (level.getSeconds() == 59) {
                        level.setSeconds(0);
                        level.setMinutes(level.getMinutes() + 1);
                    } else {
                        level.setSeconds(level.getSeconds() + 1);
                    }
                    timerLabel.setText((Level.GAME_TIME - level.getMinutes()) + " : " + (60 - level.getSeconds()));
                }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void shoot(AnchorPane anchorPane, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(anchorPane, level, currentBall, invisibleCircle, mainCircle, connectedBalls, allAnimations, ballCountLabel1, ballCountLabel2, scoreLabel, timeline);
        allAnimations.add(shootingAnimation);
        shootingAnimation.play();
        currentBall = createBall(anchorPane, connectedBalls, allAnimations, currentBall.getNumber() + 1);
    }

    private Ball createBall(AnchorPane anchorPane, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations, int number) {
        Ball ball = new Ball(anchorPane.getPrefWidth() / 2, anchorPane.getPrefHeight() - 2 * Ball.RADIUS - 20, number);
        anchorPane.getChildren().add(ball);
        anchorPane.getChildren().add(ball.getNumberText());
        ball.requestFocus();
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE)) shoot(anchorPane, connectedBalls, allAnimations);
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
