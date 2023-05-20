package view;

import controller.Controller;
import controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Game;

import java.net.URL;
import java.util.Objects;

public class LoginMenu extends Application {
    private Controller controller;
    private TextField username;
    private PasswordField password;
    private Label errorLabel;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane loginMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/LoginMenu.fxml")).toExternalForm()));
        VBox vBox = (VBox) loginMenuPane.getChildren().get(0);

        username = (TextField) vBox.getChildren().get(1);
        password = (PasswordField) vBox.getChildren().get(2);

        errorLabel = (Label) vBox.getChildren().get(3);
        errorLabel.setWrapText(true);
        errorLabel.setTextAlignment(TextAlignment.CENTER);

        Button loginButton = (Button) vBox.getChildren().get(4);
        loginButton.setOnMouseClicked(event -> {
            try {
                errorLabel.setVisible(false);
                String result = controller.getLoginMenuController().login(username.getText(), password.getText());
                if (!result.endsWith(" successfully!")) {
                    errorLabel.setText(result);
                    errorLabel.setVisible(true);

                    username.clear();
                    password.clear();
                } else {
                    controller.getMainMenuController().getMainMenu().start(stage);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

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
