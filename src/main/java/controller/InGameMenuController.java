package controller;

import view.InGameMenu;

public class InGameMenuController {
private final Controller controller;
private final InGameMenu inGameMenu;

    public InGameMenuController(Controller controller) {
        this.controller = controller;
        this.inGameMenu = new InGameMenu();
        this.inGameMenu.setController(controller);
    }

    public String resumeGame() {
        // TODO Auto-generated method
        return null;
    }

    public InGameMenu getInGameMenu() {
        return inGameMenu;
    }
}
