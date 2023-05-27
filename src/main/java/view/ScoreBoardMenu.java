package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        scoreBoardMenuPane.setBackground(Background.fill(Color.WHITE));
        HBox hBox = (HBox) scoreBoardMenuPane.getChildren().get(0);

        Pane scoreBoardPane = (Pane) hBox.getChildren().get(1);

        TableView<User> tableView3 = new TableView<>();
        tableView3.setPrefWidth(300);
        tableView3.setPrefHeight(400);
        completeTable(tableView3, 3, controller.getGame().getScoreBoard1());
        scoreBoardPane.getChildren().add(tableView3);

        TableView<User> tableView2 = new TableView<>();
        tableView2.setPrefWidth(300);
        tableView2.setPrefHeight(400);
        completeTable(tableView2, 2, controller.getGame().getScoreBoard2());
        scoreBoardPane.getChildren().add(tableView2);

        TableView<User> tableView1 = new TableView<>();
        tableView1.setPrefWidth(300);
        tableView1.setPrefHeight(400);
        completeTable(tableView1, 1, controller.getGame().getScoreBoard3());
        scoreBoardPane.getChildren().add(tableView1);

        VBox vBox = (VBox) hBox.getChildren().get(0);

        Button difficulty1 = (Button) vBox.getChildren().get(0);
        difficulty1.setOnMouseClicked(event -> {
            tableView1.toFront();
        });

        Button difficulty2 = (Button) vBox.getChildren().get(2);
        difficulty2.setOnMouseClicked(event -> {
            tableView2.toFront();
        });

        Button difficulty3 = (Button) vBox.getChildren().get(4);
        difficulty3.setOnMouseClicked(event -> {
            tableView3.toFront();
        });

        Button backButton = (Button) vBox.getChildren().get(6);
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

    public void completeTable(TableView<User> tableView, int difficulty, LinkedList<User> topUsers) {
        TableColumn<User, Integer> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(100);
        TableColumn<User, Integer> highScoreColumn = new TableColumn<>("Seconds");
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("sec" + difficulty));
        highScoreColumn.setPrefWidth(100);

        tableView.getColumns().add(usernameColumn);
        tableView.getColumns().add(highScoreColumn);

        int scoreBoardSize = Math.min(topUsers.size(), 10);
        for (int i = 0; i < scoreBoardSize; i++) {
            System.out.println(topUsers.get(i).getUsername() + " " + topUsers.get(i).getSec1());
            tableView.getItems().add(topUsers.get(i));
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
