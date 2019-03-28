package thedd.model.character.types;

import java.util.Random;

/**
 * Enumeration of game's character types.
 */
public enum EnemyCharacterType {

    /**
     * Goblin Non-Player Character.
     */
    GOBLIN,

    /**
     * Headless Non-Player Character.
     */
    HEADLESS;

    private static final Random RSEED = new Random();

    /**
     * Get a Random enemy type from the enum.
     * 
     * @return a random EnemyCharacterType.
     */
    public static EnemyCharacterType getRandom() {
        return values()[RSEED.nextInt(values().length)];
    }
}
