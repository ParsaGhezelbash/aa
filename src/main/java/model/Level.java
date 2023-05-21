package model;

public class Level {
    int difficulty;
    int numberOfSticks;
    int numberOfPrimarySticks;

    public Level(int difficulty, int numberOfSticks, int numberOfPrimarySticks) {
        this.difficulty = difficulty;
        this.numberOfSticks = numberOfSticks;
        this.numberOfPrimarySticks = numberOfPrimarySticks;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfSticks() {
        return numberOfSticks;
    }

    public int getNumberOfPrimarySticks() {
        return numberOfPrimarySticks;
    }
}
