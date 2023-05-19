package view;

import controller.Controller;
import controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;

import java.net.URL;
import java.util.Objects;

public class LoginMenu extends Application {
    private Controller controller;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane loginMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/LoginMenu.fxml")).toExternalForm()));
        VBox vBox = (VBox) loginMenuPane.getChildren().get(0);
        Hyperlink hyperlink = (Hyperlink) vBox.getChildren().get(5);
        hyperlink.setOnMouseClicked(event -> {
            try {
                controller.getSignUpMenuController().getSignUpMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(loginMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
