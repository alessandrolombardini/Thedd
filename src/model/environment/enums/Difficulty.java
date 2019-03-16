package model.environment.enums;

import java.util.Random;

/**
 * Representation of difficulty levels of this game.
 * 
 */
public enum Difficulty {

    /**
     * Easy difficulty level.
     */
    EASY(1, "EASY"),

    /**
     * Normal difficulty level.
     */
    NORMAL(2, "NORMAL"),

    /**
     * Hard difficulty level.
     */
    HARD(3, "HARD");

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

    /**
     * This method allows to get a random difficulty level.
     * 
     * @return a random Difficulty
     */
    public static Difficulty getRandom() {
        final Random rand = new Random(System.currentTimeMillis());
        final int randomValue = rand.nextInt(Difficulty.values().length);
        return Difficulty.values()[randomValue];
    }
}
