package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;

public class ProfilePicture extends Circle {
    private final ImagePattern imagePattern;

    public ProfilePicture(double v, double v1, ImagePattern imagePattern) {
        super(v, v1, 30);
        this.imagePattern = imagePattern;
        this.setFill(imagePattern);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}