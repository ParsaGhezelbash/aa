package controller;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.ProfileMenu;

public class ProfileMenuController {
    private final Controller controller;
    private final ProfileMenu profileMenu;

    public ProfileMenuController(Controller controller) {
        this.controller = controller;
        this.profileMenu = new ProfileMenu();
        this.profileMenu.setController(controller);
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

    public String changeAvatar(ImagePattern avatar) {
        controller.getGame().getCurrentUser().setAvatar(avatar);
        return "Avatar changed successfully!";
    }
}