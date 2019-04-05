package thedd.model.world;

import java.util.Objects;

import thedd.utils.RandomUtils;

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


    private static final int MIN_LEVEL_OF_DIFFICULTY = 0;
    private static final String ERROR_LOWDIFFICULTY = "The level of difficulty is too low";

    private final int levelOfDifficulty;
    private final String name;

    Difficulty(final int levelOfDifficulty, final String name) {
        Objects.requireNonNull(name);
        if (levelOfDifficulty < MIN_LEVEL_OF_DIFFICULTY) {
            throw new IllegalArgumentException(ERROR_LOWDIFFICULTY);
        }
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
        final int randomValue = RandomUtils.getRandomInteger(Difficulty.values().length - 1);
        return Difficulty.values()[randomValue];
    }
}
