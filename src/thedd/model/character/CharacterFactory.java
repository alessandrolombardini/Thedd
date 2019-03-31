package thedd.model.character;

import java.util.Optional;
import thedd.model.character.types.DarkDestructorNPC;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.character.types.GoblinNPC;
import thedd.model.character.types.HeadlessNPC;
import thedd.model.character.types.Multiplier;
import thedd.model.character.types.PlayerCharacter;

/**
 * Implementation of a character factory.
 */
public final class CharacterFactory {
    // Upper and lower values for random generations
    private static final int UPPER = 20;
    private static final int LOWER = 10;
    // Default player and boss names
    private final static String BOSS_NAME = "Dark Destructor";
    private static final String DEFAULT_PC_NAME = "Player";
    // Default multiplier for Boss and PlayerCharacter
    private static final double BOSS_RATE = 3;
    private static final double PC_RATE = 1.5;

    private CharacterFactory() {
    }

    /**
     * Method that create a new PlayerCharacter.
     * 
     * @param name chosen by the player.
     * @return a Player Character.
     */
    public static BasicCharacter createPlayerCharacter(final Optional<String> name, final Multiplier multiplier) {
        if (multiplier == null || name == null) {
            throw new NullPointerException();
        }
        return new PlayerCharacter(name.isPresent() ? name.get() : DEFAULT_PC_NAME,
                multiplier.getRate() * generateRandomRate() * PC_RATE);
    }

    /**
     * Method that create a new Enemy Non-Player Character.
     * 
     * @param type       the type of the enemy.
     * @param multiplier rate multiplied to basic statistics.
     * @return a Non-Player Character.
     */
    public static BasicCharacter createEnemy(final EnemyCharacterType type, final Multiplier multiplier) {
        if (type == null || multiplier == null) {
            throw new NullPointerException();
        }
        if (type == EnemyCharacterType.GOBLIN) {
            return new GoblinNPC(type.toString(), generateRandomRate() * multiplier.getRate());
        } else {
            return new HeadlessNPC(type.toString(), multiplier.getRate() * generateRandomRate());
        }
    }

    /**
     * Method that create a new Boss Character.
     * 
     * @param multiplier a rate multiplied to basic statistics.
     * @return a Boss Non-PlayerCharacter.
     */
    public static BasicCharacter createFinalBoss(final Multiplier multiplier) {
        if (multiplier == null) {
            throw new NullPointerException();
        }
        return new DarkDestructorNPC(BOSS_NAME, multiplier.getRate() * BOSS_RATE * generateRandomRate());
    }

    // Generate Random value between 1 and 2.
    private final static double generateRandomRate() {
        return ((Math.random() * (UPPER - LOWER)) + LOWER) / LOWER;
    }
}
