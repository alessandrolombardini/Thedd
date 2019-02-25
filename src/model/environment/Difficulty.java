package model.environment;

/**
 * Representation of difficulty levels of this game.
 * 
 */
public enum Difficulty {

    /**
     * Easy difficulty level.
     */
    EASY(0, "EASY"),

    /**
     * Normal difficulty level.
     */
    NORMAL(1, "NORMAL"),

    /**
     * Hard difficulty level.
     */
    HARD(2, "HARD");

    private final int levelOfDifficulty;
    private final String name;

    Difficulty(final int levelOfDifficulty, final String name) {
        this.levelOfDifficulty = levelOfDifficulty;
        this.name = name;
    }

    /**
     * This method allows to get a numerical representation of current difficulty
     * level through a numerical scale.
     * 
     * @return an integer that define the level of difficulty
     */
    public int getLevelOfDifficulty() {
        return this.levelOfDifficulty;
    }

    /**
     * This method allows to get a string representation of current difficulty
     * level.
     * 
     * @return a string that represent the level of difficulty
     */
    public String getName() {
        return this.name;
    }
}
