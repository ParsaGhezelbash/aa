package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.User;

import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;

public class ScoreBoardMenu extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane scoreBoardMenuPane = FXMLLoader.load(new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ScoreBoardMenu.fxml")).toExternalForm()));
        HBox hBox = (HBox) scoreBoardMenuPane.getChildren().get(0);

        TableView<User> tableView = new TableView<>();
        tableView.setPrefWidth(200);
        tableView.setPrefHeight(400);
        completeTable(tableView);
        hBox.getChildren().add(1, tableView);

        Button backButton = (Button) ((VBox) hBox.getChildren().get(0)).getChildren().get(0);
        backButton.setOnMouseClicked(event -> {
            try {
                controller.getMainMenuController().getMainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(scoreBoardMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void completeTable(TableView<User> tableView) {
        LinkedList<User> topUsers = controller.getGame().getScoreBoard();

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(100);
        TableColumn<User, Integer> highScoreColumn = new TableColumn<>("High Score");
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        highScoreColumn.setPrefWidth(100);

        tableView.getColumns().add(usernameColumn);
        tableView.getColumns().add(highScoreColumn);

        int scoreBoardSize = Math.min(topUsers.size(), 10);
        for (int i = 0; i < scoreBoardSize; i++) {
            tableView.getItems().add(topUsers.get(i));
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
