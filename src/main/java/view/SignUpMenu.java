package view;

import controller.Controller;
import controller.LoginMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SignUpMenu extends Application {
    private Controller controller;
    private Label label;
    private TextField username;
    private PasswordField password;
    private ProfilePicture profilePicture;

    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane signUpPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/SignUpMenu.fxml")).toExternalForm()));

        File profileDirectory = new File(new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures")).toExternalForm()).toURI());
        int defaultProfilesCount = Objects.requireNonNull(profileDirectory.listFiles()).length - 3;

        VBox vBox = (VBox) signUpPane.getChildren().get(0);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(1);
        addProfilePictures(scrollPane, defaultProfilesCount);

        username = (TextField) vBox.getChildren().get(2);

        password = (PasswordField) vBox.getChildren().get(3);

        label = (Label) vBox.getChildren().get(4);

        Button signUpButton = (Button) vBox.getChildren().get(5);
        signUpButton.setOnMouseClicked(event -> {
            try {
                createAccount(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Hyperlink hyperlink = (Hyperlink) vBox.getChildren().get(6);
        hyperlink.setOnMouseClicked(event -> {
            try {
                controller.getLoginMenuController().getLoginMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button enterAsGuestButton = (Button) vBox.getChildren().get(7);

        Scene scene = new Scene(signUpPane);
        stage.setScene(scene);
        stage.show();
    }

    private void addProfilePictures(ScrollPane scrollPane, int profilesCount) {
        ArrayList<ProfilePicture> profilePictures = new ArrayList<>();
        AnchorPane anchorPane = new AnchorPane();
        ImagePattern imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/0.jpg").toExternalForm())));
        profilePicture = new ProfilePicture( 40 + 70, 40, imagePattern);
        for (int i = 0; i < profilesCount; i++) {
            imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + (i + 1) + ".jpg").toExternalForm())));
            ProfilePicture profile = new ProfilePicture( 40 + i * 70, 40, imagePattern);
            profile.setOnMouseClicked(event -> {
                profilePicture.setStroke(Color.BLACK);
                profilePicture = profile;
                profilePicture.setStroke(Color.GREEN);
            });
            profilePictures.add(profile);
            anchorPane.getChildren().add(profile);
        }
        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Random.jpg").toExternalForm())));
        ProfilePicture profile = new ProfilePicture( 40 + profilesCount * 70, 40, imagePattern);
        profile.setOnMouseClicked(event -> {
            profilePicture.setStroke(Color.BLACK);
            profilePicture = profilePictures.get(new Random().nextInt(profilePictures.size()));
            profilePicture.setStroke(Color.GREEN);
        });
        anchorPane.getChildren().add(profile);

        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Custom.jpg").toExternalForm())));
        profile = new ProfilePicture( 40 + (profilesCount + 1) * 70, 40, imagePattern);
        ProfilePicture finalProfile = profile;
        profile.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Picture");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.getPath().endsWith(".jpg")) {
                ProfilePicture selectedProfilePicture = new ProfilePicture(finalProfile.getCenterX(), 40, new ImagePattern(new Image(file.toURI().toString())));
                anchorPane.getChildren().remove(finalProfile);
                finalProfile.setCenterX(finalProfile.getCenterX() + 70);
                anchorPane.getChildren().add(finalProfile);
                profilePicture.setStroke(Color.BLACK);
                profilePicture = selectedProfilePicture;
                profilePicture.setStroke(Color.GREEN);
                anchorPane.getChildren().add(selectedProfilePicture);
            }
            else {
                label.setText("Invalid file format!");
            }
        });
        anchorPane.getChildren().add(profile);

        scrollPane.setContent(anchorPane);
    }

    public void createAccount(MouseEvent mouseEvent) throws Exception {
        String result = controller.getSignUpMenuController().signUp(username.getText(), password.getText(), profilePicture.getImagePattern());
        if (result.endsWith("already exists!")) {
            label.setText(result);
        } else {
            controller.getMainMenuController().getMainMenu().start(stage);
        }
    }

    public void enterAsGuest(MouseEvent mouseEvent) throws Exception {
        // TODO Auto-generated
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
