package thedd.model.character;

import java.util.Optional;
import thedd.model.character.types.DarkDestructorNPC;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.character.types.GoblinNPC;
import thedd.model.character.types.HeadlessNPC;
import thedd.model.character.types.PlayerCharacter;
import thedd.model.world.Difficulty;

/**
 * Implementation of character's factory.
 */
public final class CharacterFactory {
    // Upper and lower values for random generations
    private static final int UPPER = 20;
    private static final int LOWER = 10;
    // Default player and boss names
    private static final String BOSS_NAME = "Dark Destructor";
    private static final String DEFAULT_PC_NAME = "Player";
    // Default multiplier for Boss and PlayerCharacter
    private static final double BOSS_RATE = 3;
    private static final double PC_RATE = 1.5;

    private CharacterFactory() {
    }

    /**
     * Method that create a new PlayerCharacter.
     * 
     * @param name       chosen by the player.
     * @param difficulty a rate multiplied to basic statistics.
     * @return a Player Character.
     */
    public static BasicCharacter createPlayerCharacter(final Optional<String> name, final Difficulty difficulty) {
        if (difficulty == null || name == null) {
            throw new IllegalArgumentException();
        }
        return new PlayerCharacter(name.isPresent() ? name.get() : DEFAULT_PC_NAME,
                difficulty.getLevelOfDifficulty() * generateRandomRate() * PC_RATE);
    }

    /**
     * Method that create a new Enemy Non-Player Character.
     * 
     * @param type       the type of the enemy.
     * @param difficulty rate multiplied to basic statistics.
     * @return a Non-Player Character.
     */
    public static BasicCharacter createEnemy(final EnemyCharacterType type, final Difficulty difficulty) {
        if (type == null || difficulty == null) {
            throw new IllegalArgumentException();
        }
        if (type == EnemyCharacterType.GOBLIN) {
            return new GoblinNPC(type.toString(), generateRandomRate() * difficulty.getLevelOfDifficulty());
        } else {
            return new HeadlessNPC(type.toString(), difficulty.getLevelOfDifficulty() * generateRandomRate());
        }
    }

    /**
     * Method that create a new Boss Character.
     * 
     * @param difficulty a rate multiplied to basic statistics.
     * @return a Boss Non-PlayerCharacter.
     */
    public static BasicCharacter createFinalBoss(final Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException();
        }
        return new DarkDestructorNPC(BOSS_NAME, difficulty.getLevelOfDifficulty() * BOSS_RATE * generateRandomRate());
    }

    // Generate Random value between 1 and 2.
    private static double generateRandomRate() {
        return ((Math.random() * (UPPER - LOWER)) + LOWER) / LOWER;
    }
}
