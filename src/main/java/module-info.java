
module Graphic.HomeWork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports controller;
    exports model;
    exports view;
    opens view to javafx.fxml;
    opens model to com.google.gson;
}