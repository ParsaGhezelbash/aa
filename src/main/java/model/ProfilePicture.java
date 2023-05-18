package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;

public class ProfilePicture extends Circle {
    private URL imageUrl;

    public ProfilePicture(double v, double v1, URL imageUrl) {
        super(v, v1, 30);
        this.setFill(new ImagePattern(new Image(imageUrl.toExternalForm())));
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }

    public URL getImageUrl() {
        return imageUrl;
    }
}