package thedd.model.world;

import java.util.Objects;

import thedd.utils.RandomUtils;

/**
 * Representation of difficulty levels of this game.
 */
public enum Difficulty {

    /**
     * Easy difficulty level.
     */
    EASY(1, 0.25, "EASY"),

    /**
     * Normal difficulty level.
     */
    NORMAL(2, 0.5, "NORMAL"),

    /**
     * Hard difficulty level.
     */
    HARD(3, 1, "HARD");

    private static final int MIN_LEVEL_OF_DIFFICULTY = 0;
    private static final double MIN_VALUE_OF_MULTIPLIER = 0;
    private static final String ERROR_LOWDIFFICULTY = "The level of difficulty is too low";

    private final int levelOfDifficulty;
    private final double multiplier;
    private final String name;

    Difficulty(final int levelOfDifficulty, final double multiplier, final String name) {
        Objects.requireNonNull(name);
        if (levelOfDifficulty < MIN_LEVEL_OF_DIFFICULTY || multiplier < MIN_VALUE_OF_MULTIPLIER) {
            throw new IllegalArgumentException(ERROR_LOWDIFFICULTY);
        }
        this.levelOfDifficulty = levelOfDifficulty;
        this.multiplier = multiplier;
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
     * This method allows to get the multiplier linked to the level of difficulty.
     * 
     * @return an integer that define the multiplier
     */
    public Double getMultiplier() {
        return this.multiplier;
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
