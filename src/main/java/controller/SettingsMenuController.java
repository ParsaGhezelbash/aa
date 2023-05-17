package controller;

public class SettingsMenuController {
    private final Controller controller;

    public SettingsMenuController(Controller controller) {
        this.controller = controller;
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
}