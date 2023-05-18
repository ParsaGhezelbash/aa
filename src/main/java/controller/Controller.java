package controller;

import model.Game;

public class Controller {
    private final LoginMenuController loginMenuController;
    private final SignUpMenuController signUpMenuController;
    private final MainMenuController mainMenuController;
    private final ProfileMenuController profileMenuController;
    private final InGameMenuController inGameMenuController;
    private final ScoreBoardMenuController scoreBoardMenuController;
    private final SettingsMenuController settingsMenuController;
    private final Game game;

    public Controller() {
        loginMenuController = new LoginMenuController(this);
        signUpMenuController = new SignUpMenuController(this);
        mainMenuController = new MainMenuController(this);
        profileMenuController = new ProfileMenuController(this);
        inGameMenuController = new InGameMenuController(this);
        scoreBoardMenuController = new ScoreBoardMenuController(this);
        settingsMenuController = new SettingsMenuController(this);
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

    public ProfileMenuController getProfileMenuController() {
        return profileMenuController;
    }

    public InGameMenuController getInGameMenuController() {
        return inGameMenuController;
    }

    public ScoreBoardMenuController getScoreBoardMenuController() {
        return scoreBoardMenuController;
    }

    public SettingsMenuController getSettingsMenuController() {
        return settingsMenuController;
    }

    public Game getGame() {
        return game;
    }
}