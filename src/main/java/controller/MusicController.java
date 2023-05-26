package controller;

import javafx.scene.media.MediaPlayer;

public class MusicController {
    private final Controller controller;
    private MediaPlayer mediaPlayer;

    public MusicController(Controller controller) {
        this.controller = controller;
        this.mediaPlayer = controller.getGame().getMusic1();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(int index) {
        switch (index) {
            case 1 -> mediaPlayer = controller.getGame().getMusic1();
            case 2 -> mediaPlayer = controller.getGame().getMusic2();
            case 3 -> mediaPlayer = controller.getGame().getMusic3();
        }
    }
}
