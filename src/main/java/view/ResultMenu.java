package view;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import model.Ball;
import model.Game;
import model.Level;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class ResultMenu {
    private final Controller controller;
    private final InGameMenu inGameMenu;
    private final AnchorPane resultMenuPane;
    private final Label resultLabel, resultTimeLabel, resultScoreLabel;
    private final Button enterMainMenu;
    private final Level level;

    public ResultMenu(Controller controller, InGameMenu inGameMenu) throws IOException {
        this.controller = controller;
        this.inGameMenu = inGameMenu;
        this.level = inGameMenu.getLevel();
        this.resultMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ResultMenu.fxml")).toExternalForm()));
        inGameMenu.addPaneToPane(inGameMenu.getInGameMenuPane(), resultMenuPane, 0);

        this.resultLabel = (Label) resultMenuPane.getChildren().get(0);
        resultLabel.setBackground(Background.fill(Color.WHITE));
        this.resultTimeLabel = (Label) resultMenuPane.getChildren().get(1);
        resultTimeLabel.setBackground(Background.fill(Color.WHITE));
        this.resultScoreLabel = (Label) resultMenuPane.getChildren().get(2);
        resultScoreLabel.setBackground(Background.fill(Color.WHITE));
        this.enterMainMenu = (Button) resultMenuPane.getChildren().get(3);
        enterMainMenu.setOnMouseClicked(mouseEvent -> {
            try {
                if (level.isWinner() && level.getDifficulty() == 1) {
                    controller.getGame().getCurrentUser().setSec1(level.getMinutes() * 60 + level.getSeconds());
                    controller.getGame().refreshScoreBoard(controller.getGame().getScoreBoard1(), controller.getGame().getCurrentUser());
                } else if (level.isWinner() && level.getDifficulty() == 2) {
                    controller.getGame().getCurrentUser().setSec2(level.getMinutes() * 60 + level.getSeconds());
                    controller.getGame().refreshScoreBoard(controller.getGame().getScoreBoard2(), controller.getGame().getCurrentUser());
                } else if (level.isWinner() && level.getDifficulty() == 3) {
                    controller.getGame().getCurrentUser().setSec3(level.getMinutes() * 60 + level.getSeconds());
                    controller.getGame().refreshScoreBoard(controller.getGame().getScoreBoard3(), controller.getGame().getCurrentUser());
                }
                controller.getGame().getCurrentUser().setLastLevel(null);
                controller.getMainMenuController().getMainMenu().start(inGameMenu.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnchorPane getResultMenuPane() {
        return resultMenuPane;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public Label getResultTimeLabel() {
        return resultTimeLabel;
    }

    public Label getResultScoreLabel() {
        return resultScoreLabel;
    }

    public Button getEnterMainMenu() {
        return enterMainMenu;
    }
}
