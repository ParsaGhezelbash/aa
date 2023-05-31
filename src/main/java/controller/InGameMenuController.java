package controller;

import model.Level;
import view.Animations;
import view.InGameMenu;
import view.TimeLines;

public class InGameMenuController {
    private final Controller controller;
    private final InGameMenu inGameMenu;
    private Level level;

    public InGameMenuController(Controller controller) {
        this.controller = controller;
        this.inGameMenu = new InGameMenu();
        this.inGameMenu.setController(controller);
    }

    public InGameMenu getInGameMenu() {
        return inGameMenu;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}