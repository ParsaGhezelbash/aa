package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ProfileMenu extends Application {
    private Controller controller;
    private ProfilePicture selectedProfilePicture;
    private ProfilePicture currentProfilePicture;
    private Label profilePictureErrorLabel;
    private Label usernameLabel;
    private Label usernameErrorLabel;
    private Label passwordErrorLabel;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane profileMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ProfileMenu.fxml")).toExternalForm()));
        profileMenuPane.setBackground(Background.fill(Color.WHITE));
        HBox hBox = (HBox) profileMenuPane.getChildren().get(0);
        VBox rightVBox = (VBox) hBox.getChildren().get(2);
        VBox leftVBox = (VBox) hBox.getChildren().get(0);

        ScrollPane scrollPane = (ScrollPane) rightVBox.getChildren().get(0);

        File profileDirectory = new File(new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures")).toExternalForm()).toURI());
        int defaultProfilesCount = Objects.requireNonNull(profileDirectory.listFiles()).length - 3;
        addProfilePictures(scrollPane, defaultProfilesCount);

        Pane currentProfilePane = (Pane) rightVBox.getChildren().get(1);
        currentProfilePane.getChildren().add(new ProfilePicture(currentProfilePane.getPrefWidth() / 2, currentProfilePane.getPrefHeight() / 2, controller.getGame().getCurrentUser().getAvatar()));

        profilePictureErrorLabel = (Label) rightVBox.getChildren().get(3);

        Button changeProfileButton = (Button) rightVBox.getChildren().get(2);
        changeProfileButton.setOnMouseClicked(event -> {
            if (selectedProfilePicture == null) {
                profilePictureErrorLabel.setText("Please select a profile picture!");
            } else {
                String result = controller.getProfileMenuController().changeAvatar(selectedProfilePicture.getImagePattern());
                profilePictureErrorLabel.setText(result);
                profilePictureErrorLabel.setTextFill(Color.GREEN);
                currentProfilePane.getChildren().clear();
                currentProfilePicture = new ProfilePicture(currentProfilePane.getPrefWidth() / 2, currentProfilePane.getPrefHeight() / 2, controller.getGame().getCurrentUser().getAvatar());
                currentProfilePane.getChildren().add(currentProfilePicture);
            }
        });

        Label scoreLabel = (Label) leftVBox.getChildren().get(0);
        scoreLabel.setText("High Score: " + controller.getGame().getCurrentUser().getHighScore());

        usernameLabel = (Label) leftVBox.getChildren().get(2);
        usernameLabel.setText("Username: " + controller.getGame().getCurrentUser().getUsername());

        TextField newUsername = (TextField) leftVBox.getChildren().get(3);

        usernameErrorLabel = (Label) leftVBox.getChildren().get(5);
        Button changeUsernameButton = (Button) leftVBox.getChildren().get(4);
        changeUsernameButton.setOnMouseClicked(event -> {
            String result = controller.getProfileMenuController().changeUsername(newUsername.getText());
            if (!result.endsWith("changed successfully!")) {
                usernameErrorLabel.setText(result);
            } else {
                usernameLabel.setText("Username: " + controller.getGame().getCurrentUser().getUsername());
                usernameErrorLabel.setText(result);
                usernameErrorLabel.setTextFill(Color.GREEN);
            }
        });

        passwordErrorLabel = (Label) leftVBox.getChildren().get(7);

        TextField oldPassword = (TextField) leftVBox.getChildren().get(8);
        TextField newPassword = (TextField) leftVBox.getChildren().get(9);

        Button changePasswordButton = (Button) leftVBox.getChildren().get(10);
        changePasswordButton.setOnMouseClicked(event -> {
            String result = controller.getProfileMenuController().changePassword(oldPassword.getText(), newPassword.getText());
            if (!result.endsWith("successfully!")) {
                passwordErrorLabel.setText(result);
                passwordErrorLabel.setTextFill(Color.RED);
            } else {
                passwordErrorLabel.setText(result);
                passwordErrorLabel.setTextFill(Color.GREEN);
            }
        });

        Button backButton = (Button) leftVBox.getChildren().get(12);
        backButton.setOnMouseClicked(event -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Scene scene = new Scene(profileMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void addProfilePictures(ScrollPane scrollPane, int profilesCount) {
        ArrayList<ProfilePicture> profilePictures = new ArrayList<>();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(scrollPane.getPrefWidth());
        anchorPane.setPrefHeight(scrollPane.getPrefHeight());
        ImagePattern imagePattern;
        final int[] profileX = {40};
        final int[] profileY = {40};
        ProfilePicture profile;
        for (int i = 0; i < profilesCount; i++) {
            imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + (i + 1) + ".jpg").toExternalForm())));
            profile = createProfilePicture(scrollPane, profileX, profileY, imagePattern);
            makeSelectable(profile);
            profilePictures.add(profile);
            anchorPane.getChildren().add(profile);
        }

        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Random.jpg").toExternalForm())));
        profile = createProfilePicture(scrollPane, profileX, profileY, imagePattern);
        profile.setOnMouseClicked(event -> {
            selectProfilePicture(profilePictures.get(new Random().nextInt(profilePictures.size())));
        });
        anchorPane.getChildren().add(profile);

        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Custom.jpg").toExternalForm())));
        profile = createProfilePicture(scrollPane, profileX, profileY, imagePattern);
        profile.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Picture");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.getPath().endsWith(".jpg")) {
                ProfilePicture newProfilePicture = createProfilePicture(scrollPane, profileX, profileY, new ImagePattern(new Image(file.toURI().toString())));
                makeSelectable(newProfilePicture);
                selectProfilePicture(newProfilePicture);
                anchorPane.getChildren().add(newProfilePicture);
            } else {
                profilePictureErrorLabel.setText("Invalid file format!");
                profilePictureErrorLabel.setVisible(true);
            }
        });
        anchorPane.getChildren().add(profile);

        scrollPane.setContent(anchorPane);
    }

    private void selectProfilePicture(ProfilePicture profilePicture) {
        if (selectedProfilePicture != null) selectedProfilePicture.setStroke(Color.BLACK);
        selectedProfilePicture = profilePicture;
        selectedProfilePicture.setStroke(Color.GREEN);
    }
    private ProfilePicture createProfilePicture(ScrollPane scrollPane, int[] profileX, int[] profileY, ImagePattern imagePattern) {
        ProfilePicture profilePicture = new ProfilePicture(profileX[0], profileY[0], imagePattern);
        profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
        profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
        return profilePicture;
    }

    private void makeSelectable(ProfilePicture profilePicture) {
        profilePicture.setOnMouseClicked(event -> {
            selectProfilePicture(profilePicture);
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
