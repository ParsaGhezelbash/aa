package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        Pane scoreBoard1 = (Pane) scoreBoardMenuPane.getChildren().get(1);
        Pane scoreBoard2 = (Pane) scoreBoardMenuPane.getChildren().get(2);
        Pane scoreBoard3 = (Pane) scoreBoardMenuPane.getChildren().get(3);
        setScoreBoard(scoreBoard1, controller.getGame().getScoreBoard1(), 1);
        setScoreBoard(scoreBoard2, controller.getGame().getScoreBoard2(), 2);
        setScoreBoard(scoreBoard3, controller.getGame().getScoreBoard3(), 3);

//        HBox hBox = (HBox) scoreBoardMenuPane.getChildren().get(0);

//        Pane scoreBoardPane = (Pane) hBox.getChildren().get(1);

//        TableView<User> tableView3 = new TableView<>();
//        tableView3.setPrefWidth(300);
//        tableView3.setPrefHeight(400);
//        completeTable(tableView3, 3, controller.getGame().getScoreBoard1());
//        scoreBoardPane.getChildren().add(tableView3);
//
//        TableView<User> tableView2 = new TableView<>();
//        tableView2.setPrefWidth(300);
//        tableView2.setPrefHeight(400);
//        completeTable(tableView2, 2, controller.getGame().getScoreBoard2());
//        scoreBoardPane.getChildren().add(tableView2);
//
//        TableView<User> tableView1 = new TableView<>();
//        tableView1.setPrefWidth(300);
//        tableView1.setPrefHeight(400);
//        completeTable(tableView1, 1, controller.getGame().getScoreBoard3());
//        scoreBoardPane.getChildren().add(tableView1);

        VBox vBox = (VBox) scoreBoardMenuPane.getChildren().get(0);

        Button difficulty1 = (Button) vBox.getChildren().get(0);
        difficulty1.setOnMouseClicked(event -> {
            scoreBoard1.toFront();
        });

        Button difficulty2 = (Button) vBox.getChildren().get(2);
        difficulty2.setOnMouseClicked(event -> {
            scoreBoard2.toFront();
        });

        Button difficulty3 = (Button) vBox.getChildren().get(4);
        difficulty3.setOnMouseClicked(event -> {
            scoreBoard2.toFront();
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

    private void setScoreBoard(Pane scoreBoard, LinkedList<User> topUsers, int num) {
        Label[] names = {(Label) scoreBoard.getChildren().get(20), (Label) scoreBoard.getChildren().get(2),
                         (Label) scoreBoard.getChildren().get(4), (Label) scoreBoard.getChildren().get(6),
                         (Label) scoreBoard.getChildren().get(8), (Label) scoreBoard.getChildren().get(10),
                (Label) scoreBoard.getChildren().get(12), (Label) scoreBoard.getChildren().get(14),
                (Label) scoreBoard.getChildren().get(16), (Label) scoreBoard.getChildren().get(18)
        };
        Label[] scores = {(Label) scoreBoard.getChildren().get(21), (Label) scoreBoard.getChildren().get(3),
                (Label) scoreBoard.getChildren().get(5), (Label) scoreBoard.getChildren().get(7),
                (Label) scoreBoard.getChildren().get(9), (Label) scoreBoard.getChildren().get(11),
                (Label) scoreBoard.getChildren().get(13), (Label) scoreBoard.getChildren().get(15),
                (Label) scoreBoard.getChildren().get(17), (Label) scoreBoard.getChildren().get(19)
        };
        for (int i = 0; i < Math.min(10, topUsers.size()); i++) {
            names[i].setText(topUsers.get(i).getUsername());
            if (num == 1) scores[i].setText(String.valueOf(topUsers.get(i).getSec1()));
            else if (num == 2) scores[i].setText(String.valueOf(topUsers.get(i).getSec2()));
            else scores[i].setText(String.valueOf(topUsers.get(i).getSec3()));
        }
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
            tableView.getItems().add(topUsers.get(i));
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
