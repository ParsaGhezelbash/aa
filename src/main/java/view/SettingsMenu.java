package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Game;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class SettingsMenu extends Application {
    private Controller controller;
    private VBox vBox1, vBox2, vBox3;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        AnchorPane settingsMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/SettingsMenu.fxml")).toExternalForm()));
        settingsMenuPane.setBackground(Background.fill(Color.WHITE));

        HBox hBox = (HBox) settingsMenuPane.getChildren().get(0);
        vBox1 = (VBox) settingsMenuPane.getChildren().get(0);
        setVBox1();
        vBox2 = (VBox) hBox.getChildren().get(1);
        setVBox2();
        vBox3 = (VBox) vBox1.getChildren().get(2);
        setVBox3();


        Scene scene = new Scene(settingsMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    private void setVBox1() {
        Button changeColor = (Button) vBox1.getChildren().get(0);
        Button changeLanguage = (Button) vBox1.getChildren().get(1);
        Button deleteAccountButton = (Button) vBox1.getChildren().get(2);
        deleteAccountButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    controller.getGame().removeUser(controller.getGame().getCurrentUser());
                    controller.getGame().setCurrentUser(null);
                    stop();
                    controller.getSignUpMenuController().getSignUpMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void setVBox2() throws MalformedURLException {Pane innerPane = (Pane) vBox2.getChildren().get(0);

        Button setDifficultyButton = (Button) ((HBox) vBox2.getChildren().get(1)).getChildren().get(0);
        ChoiceBox<Integer> difficultyChoiceBox = (ChoiceBox<Integer>) ((HBox) vBox2.getChildren().get(1)).getChildren().get(1);
        difficultyChoiceBox.getItems().addAll(1, 2, 3);
        setDifficultyButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setDifficulty(difficultyChoiceBox.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button setNumberOfPrimaryBallsButton = (Button) ((HBox) vBox2.getChildren().get(3)).getChildren().get(0);
        ChoiceBox<Integer> numberOfPrimaryBallsChoiceBox = (ChoiceBox<Integer>) ((HBox) vBox2.getChildren().get(3)).getChildren().get(1);
        numberOfPrimaryBallsChoiceBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        setNumberOfPrimaryBallsButton.setOnMouseClicked(event -> {
            try {
                controller.getGame().setNumberOfPrimaryBalls(numberOfPrimaryBallsChoiceBox.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button musicButton1 = (Button) vBox2.getChildren().get(5);
        musicButton1.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(1);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton2 = (Button) vBox2.getChildren().get(6);
        musicButton2.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(2);
            controller.getMusicController().getMediaPlayer().play();
        });
        Button musicButton3 = (Button) vBox2.getChildren().get(7);
        musicButton3.setOnMouseClicked(mouseEvent -> {
            controller.getMusicController().getMediaPlayer().stop();
            controller.getMusicController().setMediaPlayer(3);
            controller.getMusicController().getMediaPlayer().play();
        });

        Button backButton = (Button) vBox2.getChildren().get(9);
        backButton.setOnMouseClicked(event -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Circle mutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Mute.png").toExternalForm()).toExternalForm())));
        Circle unMutedCircle = new Circle(innerPane.getPrefWidth() / 2, innerPane.getPrefHeight() / 2, 30,
                new ImagePattern(new Image(new URL(Game.class.getResource("/sound/Unmute.png").toExternalForm()).toExternalForm())));
        if (controller.getMusicController().getMediaPlayer().isMute()) {
            innerPane.getChildren().add(mutedCircle);
        } else {
            innerPane.getChildren().add(unMutedCircle);
        }
        mutedCircle.setOnMouseClicked(event -> {
            innerPane.getChildren().remove(mutedCircle);
            innerPane.getChildren().add(unMutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(false);
        });
        unMutedCircle.setOnMouseClicked(event -> {
            innerPane.getChildren().remove(unMutedCircle);
            innerPane.getChildren().add(mutedCircle);
            controller.getMusicController().getMediaPlayer().setMute(true);
        });

    }

    private void setVBox3() {
        TextField firstPlayerShoot = (TextField) vBox3.getChildren().get(1);
        firstPlayerShoot.setText(controller.getGame().getFirstPlayerShoot().toString());
        firstPlayerShoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setFirstPlayerShoot(keyEvent.getCode());
                firstPlayerShoot.setText(controller.getGame().getFirstPlayerShoot().toString());
            }
        });

        TextField secondPlayerShoot = (TextField) vBox3.getChildren().get(3);
        secondPlayerShoot.setText(controller.getGame().getSecondPlayerShoot().toString());
        secondPlayerShoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setSecondPlayerShoot(keyEvent.getCode());
                secondPlayerShoot.setText(controller.getGame().getSecondPlayerShoot().toString());
            }
        });

        TextField freezeMode = (TextField) vBox3.getChildren().get(5);
        freezeMode.setText(controller.getGame().getFreezeMode().toString());
        freezeMode.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setFreezeMode(keyEvent.getCode());
                freezeMode.setText(controller.getGame().getFreezeMode().toString());
            }
        });

        TextField firstMoveRight = (TextField) vBox3.getChildren().get(7);
        firstMoveRight.setText(controller.getGame().getMoveRight1().toString());
        firstMoveRight.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setMoveRight1(keyEvent.getCode());
                firstMoveRight.setText(controller.getGame().getMoveRight1().toString());
            }
        });


        TextField firstMoveLeft = (TextField) vBox3.getChildren().get(9);
        firstMoveLeft.setText(controller.getGame().getMoveLeft1().toString());
        firstMoveLeft.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setMoveLeft1(keyEvent.getCode());
                firstMoveLeft.setText(controller.getGame().getMoveLeft1().toString());
            }
        });

        TextField secondMoveRight = (TextField) vBox3.getChildren().get(11);
        secondMoveRight.setText(controller.getGame().getMoveRight2().toString());
        secondMoveRight.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setMoveRight2(keyEvent.getCode());
                secondMoveRight.setText(controller.getGame().getMoveRight2().toString());
            }
        });

        TextField secondMoveLeft = (TextField) vBox3.getChildren().get(13);
        secondMoveLeft.setText(controller.getGame().getMoveLeft2().toString());
        secondMoveLeft.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.getGame().setMoveLeft2(keyEvent.getCode());
                secondMoveLeft.setText(controller.getGame().getMoveLeft2().toString());
            }
        });
    }

    public void makeClickable (Circle circle1, Circle circle2, Pane pane) {
        circle1.setOnMouseClicked(event -> {
            pane.getChildren().remove(circle1);
            pane.getChildren().add(circle2);
        });
        circle2.setOnMouseClicked(event -> {
            pane.getChildren().remove(circle1);
            pane.getChildren().add(circle1);
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
