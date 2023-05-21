package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MainMenu extends Application {
    private Controller controller;
    private ImagePattern profileImagePattern;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane mainMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MainMenu.fxml")).toExternalForm()));

        VBox vBox = (VBox) ((HBox) mainMenuPane.getChildren().get(0)).getChildren().get(1);

        Pane profilePane = (Pane) vBox.getChildren().get(1);
        profilePane.getChildren().add(new ProfilePicture(profilePane.getPrefWidth() / 2, profilePane.getPrefHeight() / 2, controller.getGame().getCurrentUser().getAvatar()));

        ((Label) vBox.getChildren().get(2)).setText(controller.getGame().getCurrentUser().getUsername());

        Button singlePlayerButton = (Button) vBox.getChildren().get(3); // TODO Auto-generated
        singlePlayerButton.setOnMouseClicked(event -> {
            try {
                controller.getInGameMenuController().getInGameMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button multiPlayerButton = (Button) vBox.getChildren().get(4); // TODO Auto-generated

        Button scoreBoardButton = (Button) vBox.getChildren().get(5);
        EventHandler<Event> scoreBoardButtonEvent = event -> {
            try {
                controller.getScoreBoardMenuController().getScoreBoardMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        scoreBoardButton.setOnMouseClicked(scoreBoardButtonEvent);
        scoreBoardButton.setOnKeyPressed(scoreBoardButtonEvent);

        Button profileButton = (Button) vBox.getChildren().get(6);
        EventHandler<Event> profileButtonEvent = event -> {
            try {
                controller.getProfileMenuController().getProfileMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        profileButton.setOnMouseClicked(profileButtonEvent);
        profileButton.setOnKeyPressed(profileButtonEvent);

        Button exitButton = (Button) vBox.getChildren().get(8);
        exitButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setCurrentUser(null);
                controller.getSignUpMenuController().getSignUpMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(mainMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
