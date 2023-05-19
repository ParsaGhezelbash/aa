package controller;

import view.MainMenu;

import java.net.MalformedURLException;

public class MainMenuController {
    private final Controller controller;
    private final MainMenu mainMenu;



    public MainMenuController(Controller controller) throws MalformedURLException {
        this.controller = controller;
        this.mainMenu = new MainMenu();
        this.mainMenu.setController(controller);
    }

    public String startGame() {
        // TODO Auto-generated method
        return null;
    }

    public String resumeGame() {
        // TODO Auto-generated method
        return null;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }
}