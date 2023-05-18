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

        User user = (avatar.length == 1) ? new User(username, password) : new User(username, password, avatar[0]);
        controller.getGame().addUser(user);
        controller.getGame().setCurrentUser(user);
        return "User " + username + " created successfully!";
    }

    public String enterAsGuest() {
        // TODO Auto-generated method
        return null;
    }
}
