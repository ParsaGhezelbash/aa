package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;

public class ProfilePicture extends Circle {
    public static final int PROFILE_RADIUS = 30;
    private final ImagePattern imagePattern;

    public ProfilePicture(double x, double y, ImagePattern imagePattern) {
        super(x, y, PROFILE_RADIUS);
        this.imagePattern = imagePattern;
        this.setFill(imagePattern);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}