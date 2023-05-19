package controller;

import view.ScoreBoardMenu;

public class ScoreBoardMenuController {
    private Controller controller;
    private final ScoreBoardMenu scoreBoardMenu;

    public ScoreBoardMenuController(Controller controller) {
        this.controller = controller;
        this.scoreBoardMenu = new ScoreBoardMenu();
        this.scoreBoardMenu.setController(controller);
    }
}
