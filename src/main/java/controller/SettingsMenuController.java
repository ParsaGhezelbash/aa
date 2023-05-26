package controller;

import view.SettingsMenu;

public class SettingsMenuController {
    private final Controller controller;
    private final SettingsMenu settingsMenu;

    public SettingsMenuController(Controller controller) {
        this.controller = controller;
        this.settingsMenu = new SettingsMenu();
        this.settingsMenu.setController(controller);
    }

    public String setDifficulty(int difficulty) {
        // TODO Auto-generated method
        return null;
    }

    public void mute() {
        controller.getGame().setSoundMuted(true);
    }

    public void unmute() {
        controller.getGame().setSoundMuted(false);
    }

    public SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }
}