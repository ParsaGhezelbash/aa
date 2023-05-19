package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;

import java.net.URL;
import java.util.Objects;

public class ScoreBoardMenu extends Application {
    private Controller controller;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane scoreBoardMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ScoreBoardMenu.fxml")).toExternalForm()));

        Scene scene = new Scene(scoreBoardMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
