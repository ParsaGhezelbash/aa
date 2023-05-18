package view;

import controller.Controller;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class SignUpMenu extends Application {
//    private final Controller controller;
    private Label label1, label2;
    private TextField username;
    private PasswordField password;
    private ProfilePicture profilePicture;
    private Stage stage;

//    public SignUpMenu(Controller controller) {
//        this.controller = controller;
//    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Pane signUpPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/SignUpMenu.fxml")).toExternalForm()));

        File profileDirectory = new File(new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures")).toExternalForm()).toURI());
        int defaultProfilesCount = Objects.requireNonNull(profileDirectory.listFiles()).length;

        VBox vBox = null;
        for (Node child : signUpPane.getChildren()) {
            if (child instanceof VBox) vBox = (VBox) child;
        }
        ScrollPane scrollPane = null;
        for (Node child : vBox.getChildren()) {
            if (child instanceof ScrollPane) scrollPane = (ScrollPane) child;
        }
        AnchorPane anchorPane = new AnchorPane();

        for (int i = 0; i < defaultProfilesCount; i++) {
            ProfilePicture profile = new ProfilePicture( 40 + i * 70, 40, new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + i + ".jpg")).toExternalForm()));
            anchorPane.getChildren().add(profile);
            profile.setOnMouseClicked(event -> {
                if (profilePicture != null) profilePicture.setStroke(Color.BLACK);
                profilePicture = profile;
                profilePicture.setStroke(Color.GREEN);
            });
        }

        scrollPane.setContent(anchorPane);

        Scene scene = new Scene(signUpPane);
        stage.setScene(scene);
        stage.show();
    }

//    public void createAccount(MouseEvent mouseEvent) throws Exception {
//        String result = controller.getSignUpMenuController().signUp(username.getText(), password.getText());
//        if (result.endsWith("already exists!")) {
//            label2.setText(result);
//        } else {
//        }
//    }

    public void enterAsGuest(MouseEvent mouseEvent) throws Exception {
        // TODO Auto-generated
    }
}
