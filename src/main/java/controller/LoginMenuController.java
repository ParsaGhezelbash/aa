package controller;

import model.User;
import view.LoginMenu;

public class LoginMenuController {
    private final Controller controller;
    private final LoginMenu loginMenu;

    public LoginMenuController(Controller controller) {
        this.controller = controller;
        this.loginMenu = new LoginMenu();
        this.loginMenu.setController(controller);
    }

    public String login(String username, String password) {
        User user = controller.getGame().getUserByUsername(username);
        if (user == null)
            return "User with username " + username + " does not exist!";

        if (!user.isPasswordCorrect(password))
            return "Password is incorrect!";

        controller.getGame().setCurrentUser(user);
        return "User logged in successfully!";
    }

    public LoginMenu getLoginMenu() {
        return loginMenu;
    }
}
