package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

        HBox hBox = (HBox) settingsMenuPane.getChildren().get(0);
        VBox vBox = (VBox) hBox.getChildren().get(1);
        Pane innerPane = (Pane) vBox.getChildren().get(0);

        Circle mutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Mute.jpg").toExternalForm()).toExternalForm())));
        Circle unMutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Unmute.jpg").toExternalForm()).toExternalForm())));

        innerPane.getChildren().add(unMutedCircle);
        makeClickable(mutedCircle, unMutedCircle, innerPane);

        Scene scene = new Scene(settingsMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void makeClickable (Node node1, Node node2, Pane pane) {
        node1.setOnMouseClicked(event -> {
            pane.getChildren().remove(0);
            pane.getChildren().add(node2);
        });
        node2.setOnMouseClicked(event -> {
            pane.getChildren().remove(0);
            pane.getChildren().add(node1);
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
