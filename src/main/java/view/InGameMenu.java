package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Ball> primaryBalls = new ArrayList<>();
        ArrayList<Ball> connectedBalls = new ArrayList<>();
        AnchorPane inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        invisibleCircle = (Circle) inGameMenuPane.getChildren().get(0);
        mainCircle = (Circle) inGameMenuPane.getChildren().get(1);

        currentBall = createBall(inGameMenuPane, connectedBalls, 1);

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        currentBall.requestFocus();
        stage.show();
    }

    private void shoot(AnchorPane anchorPane, ArrayList<Ball> connectedBalls) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(anchorPane, currentBall, invisibleCircle, connectedBalls);
        shootingAnimation.play();
        currentBall = createBall(anchorPane, connectedBalls, currentBall.getNumber() + 1);
    }

    private Ball createBall(AnchorPane anchorPane, ArrayList<Ball> connectedBalls, int number) {
        Ball ball = new Ball(anchorPane.getPrefWidth() / 2, anchorPane.getPrefHeight() - 2 * Ball.RADIUS - 20, number);
        anchorPane.getChildren().add(ball);
        ball.requestFocus();
        ball.setOnMouseClicked(event -> {
            shoot(anchorPane, connectedBalls);
        });
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE)) shoot(anchorPane, connectedBalls);
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
