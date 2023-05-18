package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MainMenu extends Application {
    private Controller controller;
    private Button startButton, scoreBoardButton, profileButton, exitButton;
    private URL profilePictureURL = new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures/3.jpg")).toExternalForm());
    private Stage stage;

    public MainMenu() throws MalformedURLException {
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane mainMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MainMenu.fxml")).toExternalForm()));

        HBox hBox = (HBox) mainMenuPane.getChildren().get(0);
        VBox vBox = null;
        outer:
        for (Node child : hBox.getChildren()) {
            if (child instanceof VBox) {
                vBox = (VBox) child;
                for (Node vBoxChild : vBox.getChildren()) {
                    if (vBoxChild instanceof Label) break outer;
                }
            }
        }

        Pane profilePane = null;
        for (Node child : vBox.getChildren()) {
            if (child instanceof Pane) profilePane = (Pane) child;
        }
        profilePane.getChildren().add(new ProfilePicture(profilePane.getPrefWidth() / 2, profilePane.getPrefHeight() / 2, profilePictureURL));
        Scene scene = new Scene(mainMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void setProfilePictureURL(URL profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }
}
