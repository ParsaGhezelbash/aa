package view;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Start extends Application {

    public static void main(String[] args) throws MalformedURLException {
        Controller controller = new Controller();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller.controller.getMusicController().getMediaPlayer().play();
        Controller.controller.getSignUpMenuController().getSignUpMenu().start(stage);
    }
}