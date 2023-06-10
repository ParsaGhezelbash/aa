package view;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.Game;
import model.Level;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class InGameMenu extends Application {
    private Controller controller;
    private Level level;
    private AnchorPane inGameMenuPane;
    private ArrayList<Ball> connectedBalls;
    private Animations animations;
    private TimeLines timeLines;
    private ResultMenu resultMenu;
    private PauseMenu pauseMenu;
    private Ball currentBall1, currentBall2;
    private Ball mainCircle;
    private Label usernameLabel, scoreLabel, ballCountLabel1, ballCountLabel2, timerLabel, difficultyLabel, windLabel;
    private Label resultLabel, resultTimeLabel, resultScoreLabel;
    private Timeline timeline;
    private ProgressBar icingModeProgressBar;
    private Button pauseButton;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        inGameMenuPane.setBackground(Background.fill(Color.WHITE));

        resultMenu = new ResultMenu(controller, this);
        pauseMenu = new PauseMenu(controller, this);

        setCircles();

        this.animations = new Animations();
        animations.startAllAnimations();

        usernameLabel = (Label) inGameMenuPane.getChildren().get(3);
        usernameLabel.setText("Username : " + controller.getGame().getCurrentUser().getUsername());

        scoreLabel = (Label) inGameMenuPane.getChildren().get(4);
        scoreLabel.setText("Score : " + level.getScore());

        ballCountLabel1 = (Label) inGameMenuPane.getChildren().get(5);
        if (level.isSinglePlayer()) {
            ballCountLabel1.setText("Ball Count : " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()));
        } else {
            ballCountLabel1.setText("Ball Count 1: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                    "\nBall Count 2: " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
        }

        timerLabel = (Label) inGameMenuPane.getChildren().get(6);
        setTimerLabel();

        difficultyLabel = (Label) inGameMenuPane.getChildren().get(7);
        difficultyLabel.setText("Difficulty : " + level.getDifficulty());

        setMainCircleCounter();

        windLabel = (Label) inGameMenuPane.getChildren().get(9);
        windLabel.setText("Wind : " + level.getWind());

        pauseButton = (Button) inGameMenuPane.getChildren().get(10);
        setPauseButton();

        icingModeProgressBar = (ProgressBar) inGameMenuPane.getChildren().get(11);
        icingModeProgressBar.setProgress(level.getIcingMode());

        connectedBalls = new ArrayList<>();
        setConnectedBalls();

        this.timeLines = new TimeLines(this);

        if (level.isSinglePlayer()) {
            currentBall1 = createBall(0, level.equals(controller.getGame().getCurrentUser().getLastLevel()) ? level.getNumberOfConnectedBalls1() + 1 : 1);
        } else {
            currentBall1 = createBall(1, level.equals(controller.getGame().getCurrentUser().getLastLevel()) ? level.getNumberOfConnectedBalls1() + 1 : 1);
            currentBall2 = createBall(2, level.equals(controller.getGame().getCurrentUser().getLastLevel()) ? level.getNumberOfConnectedBalls2() + 1 : 1);
        }

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        inGameMenuPane.requestFocus();
        stage.show();
    }

    private void setConnectedBalls() {
        if (level.equals(controller.getGame().getCurrentUser().getLastLevel())) {
            for (int i = 0; i < level.getConnectedBallsNumber().size(); i++) {
                Ball ball;
                if (level.getConnectedBallsNumber().get(i) != -1) {
                    ball = new Ball(level.getConnectedBallsX().get(i), level.getConnectedBallsY().get(i), level.getConnectedBallsNumber().get(i),
                            level.getConnectedBallsPlayerNumber().get(i));
                } else {
                    ball = new Ball(level.getConnectedBallsX().get(i), level.getConnectedBallsY().get(i));
                }
                inGameMenuPane.getChildren().add(ball);
                if (ball.getNumberText() != null) inGameMenuPane.getChildren().add(ball.getNumberText());
                connectBall(ball);
            }
        } else {
            for (int i = 0; i < level.getNumberOfPrimaryBalls(); i++) {
                if (level.getNumberOfPrimaryBalls() != 0) {
                    double angle = (double) (360 / level.getNumberOfPrimaryBalls());
                    Ball ball = new Ball(mainCircle.getX() - (Game.DISTANCE + Ball.RADIUS) * Math.sin(i * Math.toRadians(angle)),
                            mainCircle.getY() + (Game.DISTANCE + Ball.RADIUS) * Math.cos(i * Math.toRadians(angle)));
                    inGameMenuPane.getChildren().add(ball);
                    connectBall(ball);
                    level.addConnectedBall(ball);
                }
            }
        }
    }

    private void setCircles() {
        mainCircle = new Ball(Level.LEVEL_X, Level.LEVEL_Y);
        mainCircle.setRadius(40);
        mainCircle.setFill(Color.BLACK);
        mainCircle.setStroke(null);
        inGameMenuPane.getChildren().add(2, mainCircle);
        inGameMenuPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(controller.getGame().getFirstPlayerShoot()))
                    shoot(currentBall1);
                else if (keyEvent.getCode().equals(controller.getGame().getSecondPlayerShoot()))
                    shoot(currentBall2);
                else if (level.getPhase() >= 0.75 && keyEvent.getCode().equals(controller.getGame().getMoveRight1())) {
                    currentBall1.setX(currentBall1.getX() + 10);
                    currentBall1.getNumberText().setX(currentBall1.getX() - currentBall1.getNumberText().getLayoutBounds().getWidth() / 2);
                } else if (level.getPhase() >= 0.75 && keyEvent.getCode().equals(controller.getGame().getMoveLeft1())) {
                    currentBall1.setX(currentBall1.getX() - 10);
                    currentBall1.getNumberText().setX(currentBall1.getX() - currentBall1.getNumberText().getLayoutBounds().getWidth() / 2);
                } else if (level.getPhase() >= 0.75 && keyEvent.getCode().equals(controller.getGame().getMoveRight2())) {
                    currentBall2.setX(currentBall2.getX() + 10);
                    currentBall2.getNumberText().setX(currentBall2.getX() - currentBall2.getNumberText().getLayoutBounds().getWidth() / 2);
                } else if (level.getPhase() >= 0.75 && keyEvent.getCode().equals(controller.getGame().getMoveLeft2())) {
                    currentBall2.setX(currentBall2.getX() - 10);
                    currentBall2.getNumberText().setX(currentBall2.getX() - currentBall2.getNumberText().getLayoutBounds().getWidth() / 2);
                } else if (level.getIcingMode() >= 1 && keyEvent.getCode().equals(controller.getGame().getFreezeMode())) {
                    System.out.println("freeze");
                    animations.setAngleOfRotations(1);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis((9 - level.getDifficulty() * 2) * 1000), actionEvent -> {
                        level.setIcingMode(0);
                        icingModeProgressBar.setProgress(0);
                        animations.setAngleOfRotations(level.getDifficulty() * 2);
                        System.out.println("unfreeze");
                    }));
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            }
        });
    }

    public void addPaneToPane(Pane pane, Pane paneToAdd, int index) {
        paneToAdd.setLayoutX(pane.getPrefWidth() / 2 - paneToAdd.getPrefWidth() / 2);
        paneToAdd.setLayoutY(pane.getPrefHeight() / 2 - paneToAdd.getPrefHeight() / 2);
        paneToAdd.setVisible(false);
        pane.getChildren().add(index, paneToAdd);
    }

    private void setMainCircleCounter() {
        ballCountLabel2 = new Label();
        ballCountLabel2.setPrefWidth(60);
        ballCountLabel2.setPrefHeight(60);
        ballCountLabel2.setLayoutX(mainCircle.getX() - ballCountLabel2.getPrefWidth() / 2);
        ballCountLabel2.setLayoutY(mainCircle.getY() - ballCountLabel2.getPrefHeight() / 2);
        ballCountLabel2.setFont(Font.font(ballCountLabel2.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, level.isSinglePlayer() ? 32 : 16));
        ballCountLabel2.setTextFill(Color.WHITE);
        if (level.isSinglePlayer()) {
            ballCountLabel2.setText(String.valueOf((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1())));
        } else {
            ballCountLabel2.setText((level.getNumberOfBalls() - level.getNumberOfConnectedBalls1()) +
                    " | " + (level.getNumberOfBalls() - level.getNumberOfConnectedBalls2()));
        }
        ballCountLabel2.setAlignment(Pos.CENTER);
        inGameMenuPane.getChildren().add(8, ballCountLabel2);
    }

    private void setPauseButton() {
        pauseButton.setOnAction(actionEvent -> {
            animations.stopAllAnimations();
            timeline.stop();
            timeLines.pauseRunningTimeLines();
            pauseMenu.getPauseMenuPane().setVisible(true);
            pauseMenu.getPauseMenuPane().toFront();
            pauseMenu.getPauseMenuPane().requestFocus();
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
                    if (level.getMinutes() == Level.GAME_TIME && level.getSeconds() == 0) {
                        finishGame(false, 1);
                        try {
                            timeline.stop();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    timerLabel.setText((Level.GAME_TIME - level.getMinutes() - 1) + " : " + (60 - level.getSeconds()));
                    windLabel.setText("Wind : " + level.getWind());
                }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void shoot(Ball shootingBall) {
        controller.getGame().getShootSound().play();
        ShootingAnimation shootingAnimation = new ShootingAnimation(this, shootingBall);
        animations.addAnimation(shootingAnimation);
        shootingAnimation.play();
        if (shootingBall.getPlayerNumber() != 2) {
            currentBall1 = createBall(shootingBall.getPlayerNumber(), shootingBall.getNumber() + 1);
        } else {
            currentBall2 = createBall(shootingBall.getPlayerNumber(), shootingBall.getNumber() + 1);
        }
    }

    private Ball createBall(int playerNumber, int number) {
        Ball ball = new Ball(inGameMenuPane.getPrefWidth() / 2, playerNumber != 2 ? inGameMenuPane.getPrefHeight() - 2 * Ball.RADIUS - 20 : 2 * Ball.RADIUS + 20,
                number, playerNumber);
        inGameMenuPane.getChildren().add(ball);
        inGameMenuPane.getChildren().add(ball.getNumberText());
        inGameMenuPane.requestFocus();
        return ball;
    }

    public void connectBall(Ball ball) {
        System.out.println(connectedBalls.size());
        ball.setAngle(ball.getAngleFromCoordinate(mainCircle.getX(), mainCircle.getY()));
        connectedBalls.add(ball);
        ball.setStick(mainCircle);
        inGameMenuPane.getChildren().add(ball.getStick());
        BallRotation ballRotation = new BallRotation(mainCircle, ball, animations.getAngleOfRotations() == 0 ? level.getDifficulty() * 2 : animations.getAngleOfRotations());
        animations.addAnimation(ballRotation);
        ballRotation.play();
    }

    public void saveGame() {
        for (Ball ball : connectedBalls) {
            level.saveDetails(ball);
        }
        controller.getGame().getCurrentUser().setLastLevel(level);
    }

    public void finishGame(boolean win, int playerNumber) {
        level.setFinished(true);
        level.setWinner(win);
        //ballCountTimeline.stop(); // ???
        timeline.stop();
        timeLines.pauseRunningTimeLines();
//            changeDirectionTimeLine.stop();
//            changeDirectionTimeLine = null;
//            changeBallSizeTimeLine.stop();
//            changeBallSizeTimeLine = null;
//            ballsVisibilityTimeLine.stop();
//            ballsVisibilityTimeLine = null;
//            windTimeLine.stop();
//            windTimeLine = null;
        animations.stopAllAnimations();
        if (win) {
            level.setScore(level.getSeconds() + level.getMinutes() * 60);
            inGameMenuPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            resultMenu.getResultLabel().setText(playerNumber != 2 ? "You Won!" : "GuestPlayer Won!");
            resultMenu.getResultLabel().setTextFill(Color.GREEN);
        } else {
            level.setScore(0);
            inGameMenuPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            resultMenu.getResultLabel().setText(playerNumber != 2 ? "You Lost!" : "GuestPlayer Lost!");
            resultMenu.getResultLabel().setTextFill(Color.RED);
        }
        resultMenu.getResultScoreLabel().setText("Score : " + level.getScore());
        resultMenu.getResultTimeLabel().setText("Time : " + level.getMinutes() + " : " + level.getSeconds());
        resultMenu.getResultMenuPane().setVisible(true);
        resultMenu.getResultMenuPane().toFront();
        resultMenu.getResultMenuPane().requestFocus();
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

    public ArrayList<Ball> getConnectedBalls() {
        return connectedBalls;
    }

    public ProgressBar getIcingModeProgressBar() {
        return icingModeProgressBar;
    }

    public Label getBallCountLabel1() {
        return ballCountLabel1;
    }

    public Label getBallCountLabel2() {
        return ballCountLabel2;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public AnchorPane getInGameMenuPane() {
        return inGameMenuPane;
    }

    public Ball getMainCircle() {
        return mainCircle;
    }

    public Animations getAnimations() {
        return animations;
    }

    public TimeLines getTimeLines() {
        return timeLines;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public Ball getCurrentBall1() {
        return currentBall1;
    }

    public AnchorPane getResultPane() {
        return resultMenu.getResultMenuPane();
    }

    public Stage getStage() {
        return stage;
    }
}