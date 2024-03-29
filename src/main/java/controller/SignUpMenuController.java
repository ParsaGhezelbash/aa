package controller;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.ProfilePicture;
import model.User;
import view.SignUpMenu;

import java.io.IOException;

public class SignUpMenuController {
    private final Controller controller;
    private final SignUpMenu signUpMenu;

    public SignUpMenuController(Controller controller) {
        this.controller = controller;
        this.signUpMenu = new SignUpMenu();
        this.signUpMenu.setController(controller);
    }

    public String signUp(String username, String password, ImagePattern avatar) throws IOException {
        if (controller.getGame().getUserByUsername(username) != null)
            return "User with username " + username + " already exists!";

        User user = new User(username, password, avatar.getImage().getUrl());
        controller.getGame().addUser(user);
        controller.getGame().setCurrentUser(user);
        return "User " + username + " created successfully!";
    }

    public void enterAsGuest(ImagePattern avatar) {
        User guest = new User("Guest", "0", avatar.getImage().getUrl());
        controller.getGame().setCurrentUser(guest);
    }

    public SignUpMenu getSignUpMenu() {
        return signUpMenu;
    }
}
