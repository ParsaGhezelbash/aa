package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Game;
import model.Level;
import model.Stick;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class InGameMenu extends Application {
    private Controller controller;
    private Level level;
    private Stick currentStick;
    private Circle mainCircle;

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Stick> primarySticks = new ArrayList<>();
        ArrayList<Stick> connectedSticks = new ArrayList<>();
        AnchorPane inGameMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/InGameMenu.fxml")).toExternalForm()));
        mainCircle = (Circle) inGameMenuPane.getChildren().get(0);

        currentStick = createStick(inGameMenuPane, 1);
//        currentStick.setOnKeyPressed(event -> {
//            if (event.getCode().getName().equals("Space")) {
//                shoot(connectedSticks);
//                currentStick = createStick(inGameMenuPane, currentStick.getNumber() + 1);
//            }
//        });

        Scene scene = new Scene(inGameMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    private void shoot(ArrayList<Stick> connectedSticks) {
        ShootingAnimation shootingAnimation = new ShootingAnimation(currentStick, mainCircle, connectedSticks);
        shootingAnimation.play();
    }

    private Stick createStick(AnchorPane anchorPane, int number) {
        Stick stick = new Stick(number);
        anchorPane.getChildren().add(stick);
        stick.getCircle().setCenterX(300);
        stick.getCircle().setCenterY(500);
        stick.getRectangle().setX(300 - 3);
        stick.getRectangle().setY(500 - 70 - stick.getCircle().getRadius());
        return stick;
    }

    public Level getLevel() {
        return level;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
