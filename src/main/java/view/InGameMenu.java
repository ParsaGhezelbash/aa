package view;

import controller.Controller;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Ball;
import model.Game;
import model.Level;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class InGameMenu extends Application {
    private Controller controller;
    private Level level;
    private Ball currentBall;
    private Circle mainCircle, invisibleCircle;
    private Label timerLabel, difficultyLabel, ballCountLabel;
    private ProgressBar icingModeProgressBar;

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Ball> primaryBalls = new ArrayList<>();
        ArrayList<Ball> connectedBalls = new ArrayList<>();
        ArrayList<Transition> allAnimations = new ArrayList<>();

        AnchorPane inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        invisibleCircle = (Circle) inGameMenuPane.getChildren().get(0);
        mainCircle = (Circle) inGameMenuPane.getChildren().get(1);

        currentBall = createBall(inGameMenuPane, connectedBalls, allAnimations, 1);

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        currentBall.requestFocus();
        stage.show();
    }

    private void shoot(AnchorPane anchorPane, ArrayList<Ball> connectedBalls, ArrayList<Transition> allAnimations) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(anchorPane, currentBall, invisibleCircle, connectedBalls, allAnimations);
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

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
