package view;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Animations {
    private final ArrayList<Transition> allAnimations;

    public Animations() {
        this.allAnimations = new ArrayList<>();
    }

    public void startAllAnimations() {
        for (Transition animation : allAnimations) {
            animation.play();
        }
    }

    public void stopAllAnimations() {
        for (Transition animation : allAnimations) {
            animation.stop();
        }
    }

    public ArrayList<Transition> getAllAnimations() {
        return allAnimations;
    }

    public void addAnimation(Transition animation) {
        allAnimations.add(animation);
    }

    public void removeAnimation(Transition animation) {
        allAnimations.remove(animation);
    }

    public void setRotation(Rotate rotate) {
        for (Transition animation : allAnimations) {
            if (animation instanceof BallRotation) {
                ((BallRotation) animation).setRotate(rotate);
            }
        }
    }

    public Rotate getRotation() {
        for (Transition animation : allAnimations) {
            if (animation instanceof BallRotation) {
                return ((BallRotation) animation).getRotate();
            }
        }
        return null;
    }
}
