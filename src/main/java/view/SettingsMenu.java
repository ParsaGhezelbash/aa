package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Game;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class SettingsMenu extends Application {
    private Controller controller;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane settingsMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/SettingsMenu.fxml")).toExternalForm()));
        settingsMenuPane.setBackground(Background.fill(Color.WHITE));

        HBox hBox = (HBox) settingsMenuPane.getChildren().get(0);
        VBox vBox = (VBox) hBox.getChildren().get(1);
        Pane innerPane = (Pane) vBox.getChildren().get(0);

        Button setDifficultyButton = (Button) ((HBox) vBox.getChildren().get(1)).getChildren().get(0);
        ChoiceBox<Integer> difficultyChoiceBox = (ChoiceBox<Integer>) ((HBox) vBox.getChildren().get(1)).getChildren().get(1);
        difficultyChoiceBox.getItems().addAll(1, 2, 3);
        setDifficultyButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setDifficulty(difficultyChoiceBox.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button setNumberOfPrimaryBallsButton = (Button) ((HBox) vBox.getChildren().get(3)).getChildren().get(0);
        ChoiceBox<Integer> numberOfPrimaryBallsChoiceBox = (ChoiceBox<Integer>) ((HBox) vBox.getChildren().get(3)).getChildren().get(1);
        numberOfPrimaryBallsChoiceBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        setNumberOfPrimaryBallsButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setNumberOfPrimaryBalls(numberOfPrimaryBallsChoiceBox.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button musicButton1 = (Button) vBox.getChildren().get(5);
        musicButton1.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(1);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton2 = (Button) vBox.getChildren().get(6);
        musicButton2.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(2);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton3 = (Button) vBox.getChildren().get(7);
        musicButton3.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(3);
            controller.getMusicController().getMediaPlayer().play();
        });

        Button backButton = (Button) vBox.getChildren().get(9);
        backButton.setOnMouseClicked(event -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Circle mutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Mute.png").toExternalForm()).toExternalForm())));
        Circle unMutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Unmute.png").toExternalForm()).toExternalForm())));
        if (controller.getMusicController().getMediaPlayer().isMute()) {
            innerPane.getChildren().add(mutedCircle);
        } else {
            innerPane.getChildren().add(unMutedCircle);
        }
        mutedCircle.setOnMouseClicked(event -> {
            innerPane.getChildren().remove(mutedCircle);
            innerPane.getChildren().add(unMutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(false);
        });
        unMutedCircle.setOnMouseClicked(event -> {
            innerPane.getChildren().remove(unMutedCircle);
            innerPane.getChildren().add(mutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(true);
        });

        Scene scene = new Scene(settingsMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void makeClickable (Circle circle1, Circle circle2, Pane pane) {
        circle1.setOnMouseClicked(event -> {
            pane.getChildren().remove(circle1);
            pane.getChildren().add(circle2);
        });
        circle2.setOnMouseClicked(event -> {
            pane.getChildren().remove(circle1);
            pane.getChildren().add(circle1);
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
