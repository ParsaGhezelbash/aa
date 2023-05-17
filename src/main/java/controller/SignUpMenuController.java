package controller;

import javafx.scene.image.Image;
import model.User;

public class SignUpMenuController {
    private final Controller controller;

    public SignUpMenuController(Controller controller) {
        this.controller = controller;
    }

    public String signUp(String username, String password, Image... avatar) {
        if (controller.getGame().getUserByUsername(username) != null)
            return "User with username " + username + " already exists!";

        if (avatar.length == 1) controller.getGame().addUser(new User(username, password));
        else controller.getGame().addUser(new User(username, password, avatar[0]));
        return "User " + username + " created successfully!";
    }

    public String enterAsGuest() {
        // TODO Auto-generated method
        return null;
    }
}
