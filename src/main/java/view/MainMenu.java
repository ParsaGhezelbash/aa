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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import model.Game;
import model.Level;
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
        mainMenuPane.setBackground(Background.fill(Color.WHITE));
        mainMenuPane.setBackground(Background.fill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/background/background.jpg")).toExternalForm()))));

        VBox vBox = (VBox) ((HBox) mainMenuPane.getChildren().get(0)).getChildren().get(1);

        Pane profilePane = (Pane) vBox.getChildren().get(1);
        profilePane.getChildren().add(new ProfilePicture(profilePane.getPrefWidth() / 2, profilePane.getPrefHeight() / 2, controller.getGame().getCurrentUser().getAvatar()));

        ((Label) vBox.getChildren().get(2)).setText(controller.getGame().getCurrentUser().getUsername());

        ((Label) vBox.getChildren().get(2)).setTextFill(Color.GREEN);

        Button resumeButton = (Button) vBox.getChildren().get(3);
        EventHandler<Event> resumeButtonEvent = event -> {
            try {
                if (controller.getGame().getCurrentUser().getLastLevel() != null) {
                    InGameMenu inGameMenu = controller.getInGameMenuController().getInGameMenu();
                    inGameMenu.setLevel(controller.getGame().getCurrentUser().getLastLevel());
                    this.stop();
                    inGameMenu.start(stage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        resumeButton.setOnMouseClicked(resumeButtonEvent);
        resumeButton.setOnKeyPressed(resumeButtonEvent);

        Button singlePlayerButton = (Button) vBox.getChildren().get(4);
        singlePlayerButton.setOnMouseClicked(event -> {
            try {
                Game game = controller.getGame();
                InGameMenu inGameMenu = controller.getInGameMenuController().getInGameMenu();
                inGameMenu.setLevel(new Level(game.getDifficulty(), game.getNumberOfBalls(), game.getNumberOfPrimaryBalls(),
                        game.getMapNumber(), true));
                this.stop();
                inGameMenu.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button multiPlayerButton = (Button) vBox.getChildren().get(5);
        multiPlayerButton.setOnMouseClicked(event -> {
            try {
                Game game = controller.getGame();
                InGameMenu inGameMenu = controller.getInGameMenuController().getInGameMenu();
                inGameMenu.setLevel(new Level(game.getDifficulty(), game.getNumberOfBalls(), game.getNumberOfPrimaryBalls(),
                        game.getMapNumber(), false));
                this.stop();
                inGameMenu.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button scoreBoardButton = (Button) vBox.getChildren().get(6);
        EventHandler<Event> scoreBoardButtonEvent = event -> {
            try {
                this.stop();
                controller.getScoreBoardMenuController().getScoreBoardMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        scoreBoardButton.setOnMouseClicked(scoreBoardButtonEvent);
        scoreBoardButton.setOnKeyPressed(scoreBoardButtonEvent);

        Button profileButton = (Button) vBox.getChildren().get(7);
        EventHandler<Event> profileButtonEvent = event -> {
            try {
                this.stop();
                controller.getProfileMenuController().getProfileMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        profileButton.setOnMouseClicked(profileButtonEvent);
        profileButton.setOnKeyPressed(profileButtonEvent);

        Button settingsButton = (Button) vBox.getChildren().get(8);
        EventHandler<Event> settingsButtonEvent = event -> {
            try {
                this.stop();
                controller.getSettingsMenuController().getSettingsMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        settingsButton.setOnMouseClicked(settingsButtonEvent);
        settingsButton.setOnKeyPressed(settingsButtonEvent);


        Button exitButton = (Button) vBox.getChildren().get(9);
        exitButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setCurrentUser(null);
                this.stop();
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
