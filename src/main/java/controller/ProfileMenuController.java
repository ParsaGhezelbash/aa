package controller;

import javafx.scene.image.Image;

public class ProfileMenuController {
    private final Controller controller;

    public ProfileMenuController(Controller controller) {
        this.controller = controller;
    }

    public String changeUsername(String newUsername) {
        if (controller.getGame().getUserByUsername(newUsername) != null)
            return "User with username " + newUsername + " already exists!";

        controller.getGame().getCurrentUser().setUsername(newUsername);
        return "Username changed successfully!";
    }

    public String changePassword(String newPassword) {
        controller.getGame().getCurrentUser().setPassword(newPassword);
        return "Password changed successfully!";
    }

    public String changeAvatar(Image avatar) {
        controller.getGame().getCurrentUser().setAvatar(avatar);
        return "Avatar changed successfully!";
    }
}