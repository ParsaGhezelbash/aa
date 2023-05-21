package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.Stick;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private final Circle mainCircle;
    private final ArrayList<Stick> connectedSticks;
    private final Stick stick;

    public ShootingAnimation(Stick stick, Circle mainCircle, ArrayList<Stick> connectedSticks) {
        this.stick = stick;
        this.mainCircle = mainCircle;
        this.connectedSticks = connectedSticks;
    }

    @Override
    protected void interpolate(double v) {
        double y = stick.getY() - 10;

        if (isConnectedToMainBall()) {
            stick.connect();
            this.stop();
        }
            stick.setY(y);
    }

    private boolean isConnectedToMainBall() {
        return mainCircle.getBoundsInParent().intersects(stick.getRectangle().getBoundsInParent());
    }

    private boolean isConnectedToStick(ArrayList<Stick> connectedSticks) {
        for (Stick connectedStick : connectedSticks) {
            if (connectedStick.getBoundsInParent().intersects(connectedStick.getRectangle().getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}
