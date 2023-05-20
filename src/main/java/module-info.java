module Graphic.HomeWork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports controller;
    exports model;
    exports view;
    opens view to javafx.fxml;
}