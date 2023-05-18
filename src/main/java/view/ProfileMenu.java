package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import model.ProfilePicture;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    private Controller controller;
    private ProfilePicture profilePicture;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane profileMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ProfileMenu.fxml")).toExternalForm()));
        HBox hBox = (HBox) profileMenuPane.getChildren().get(0);
        VBox vBox = (VBox) hBox.getChildren().get(2);

        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        File profileDirectory = new File(new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures")).toExternalForm()).toURI());
        int defaultProfilesCount = Objects.requireNonNull(profileDirectory.listFiles()).length;


        AnchorPane anchorPane = new AnchorPane();
        int eachRowCapacity = (int) (scrollPane.getPrefWidth() / 40);
        ProfilePicture profile = null;
        for (int i = 0; i < defaultProfilesCount; i++) {
            if (i + 1 <= eachRowCapacity) {
                profile = new ProfilePicture( 40 + i * 70, 40, new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + i + ".jpg")).toExternalForm()));
            } else {
                profile = new ProfilePicture( 40 + (i - eachRowCapacity) * 70, 110, new URL(Objects.requireNonNull(Game.class.getResource("/profile pictures/" + i + ".jpg")).toExternalForm()));
            }
            anchorPane.getChildren().add(profile);
            ProfilePicture finalProfile = profile;
            profile.setOnMouseClicked(event -> {
                if (profilePicture != null) profilePicture.setStroke(Color.BLACK);
                profilePicture = finalProfile;
                profilePicture.setStroke(Color.GREEN);
            });
        }

        scrollPane.setContent(anchorPane);

        Scene scene = new Scene(profileMenuPane);
        stage.setScene(scene);
        stage.show();
    }
}
