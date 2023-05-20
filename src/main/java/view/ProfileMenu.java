package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private Label errorLabel;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane profileMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ProfileMenu.fxml")).toExternalForm()));
        HBox hBox = (HBox) profileMenuPane.getChildren().get(0);
        VBox vBox = (VBox) hBox.getChildren().get(2);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        errorLabel = (Label) vBox.getChildren().get(3);

        File profileDirectory = new File(new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures")).toExternalForm()).toURI());
        int defaultProfilesCount = Objects.requireNonNull(profileDirectory.listFiles()).length - 3;
        addProfilePictures(scrollPane, defaultProfilesCount);

        Pane currentProfilePane = (Pane) vBox.getChildren().get(1);
        currentProfilePane.getChildren().add(new ProfilePicture(currentProfilePane.getPrefWidth() / 2, currentProfilePane.getPrefHeight() / 2, controller.getGame().getCurrentUser().getAvatar()));

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
        int eachRowCapacity = (int) (scrollPane.getPrefWidth() / 70);
        final int[] profileX = {40};
        final int[] profileY = {40};
        System.out.println(eachRowCapacity + " " + profilesCount + " " + scrollPane.getPrefWidth());
        ProfilePicture profile;
        for (int i = 0; i < profilesCount; i++) {
            imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + (i + 1) + ".jpg").toExternalForm())));
            profile = new ProfilePicture(profileX[0], profileY[0], imagePattern);
            profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
            profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
            ProfilePicture finalProfile = profile;
            profile.setOnMouseClicked(event -> {
                if (selectedProfilePicture != null) selectedProfilePicture.setStroke(Color.BLACK);
                selectedProfilePicture = finalProfile;
                selectedProfilePicture.setStroke(Color.GREEN);
            });
            profilePictures.add(profile);
            anchorPane.getChildren().add(profile);
        }

        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Random.jpg").toExternalForm())));
        profile = new ProfilePicture(profileX[0], profileY[0], imagePattern);
        profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
        profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
        profile.setOnMouseClicked(event -> {
            if (selectedProfilePicture != null) selectedProfilePicture.setStroke(Color.BLACK);
            selectedProfilePicture = profilePictures.get(new Random().nextInt(profilePictures.size()));
            selectedProfilePicture.setStroke(Color.GREEN);
        });
        anchorPane.getChildren().add(profile);

        imagePattern = new ImagePattern(new Image(Objects.requireNonNull(Game.class.getResource("/profile pictures/Custom.jpg").toExternalForm())));
        profile = new ProfilePicture(profileX[0], profileY[0], imagePattern);
        ProfilePicture customProfile = profile;
        profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
        profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
        ProfilePicture finalProfile = profile;
        int finalProfileX = profileX[0];
        int finalProfileY = profileY[0];
        EventHandler<Event> eventHandler = new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Profile Picture");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null && file.getPath().endsWith(".jpg")) {
                    ProfilePicture newProfilePicture = new ProfilePicture(profileX[0], profileY[0], new ImagePattern(new Image(file.toURI().toString())));
                    profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
                    profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
                    if (selectedProfilePicture != null) selectedProfilePicture.setStroke(Color.BLACK);
                    selectedProfilePicture = newProfilePicture;
                    selectedProfilePicture.setStroke(Color.GREEN);
                    anchorPane.getChildren().add(newProfilePicture);
                } else {
                    errorLabel.setText("Invalid file format!");
                    errorLabel.setVisible(true);
                }
            }
        };
        profile.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Picture");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.getPath().endsWith(".jpg")) {
                ProfilePicture newProfilePicture = new ProfilePicture(finalProfileX, finalProfileY, new ImagePattern(new Image(file.toURI().toString())));
                profileX[0] = (scrollPane.getPrefWidth() - profileX[0]) < 110 ? 40 : profileX[0] + 70;
                profileY[0] = profileX[0] == 40 ? profileY[0] + 70 : profileY[0];
                if (selectedProfilePicture != null) selectedProfilePicture.setStroke(Color.BLACK);
                selectedProfilePicture = newProfilePicture;
                selectedProfilePicture.setStroke(Color.GREEN);
                anchorPane.getChildren().add(newProfilePicture);
            } else {
                errorLabel.setText("Invalid file format!");
                errorLabel.setVisible(true);
            }
        });
        anchorPane.getChildren().add(profile);

        scrollPane.setContent(anchorPane);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
