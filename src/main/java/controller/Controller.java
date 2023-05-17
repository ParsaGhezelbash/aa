package controller;

import model.Game;

public class Controller {
    private final LoginMenuController loginMenuController;
    private final SignUpMenuController signUpMenuController;
    private final MainMenuController mainMenuController;
    private final Game game;

    public Controller() {
        loginMenuController = new LoginMenuController(this);
        signUpMenuController = new SignUpMenuController(this);
        mainMenuController = new MainMenuController(this);
        game = new Game();
    }

    public LoginMenuController getLoginMenuController() {
        return loginMenuController;
    }

    public SignUpMenuController getSignUpMenuController() {
        return signUpMenuController;
    }

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }


    public Game getGame() {
        return game;
    }
}