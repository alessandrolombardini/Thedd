package thedd.model.character;

import java.util.Objects;
import java.util.Optional;
import thedd.model.character.types.DarkDestructor;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.character.types.Goblin;
import thedd.model.character.types.Headless;
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
     * @throws NullPointerException if name or difficulty parameters are null.
     */
    public static BasicCharacter createPlayerCharacter(final Optional<String> name, final Difficulty difficulty) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(difficulty);
        return new PlayerCharacter((name.isPresent() && !name.get().equals("")) ? name.get() : DEFAULT_PC_NAME,
                difficulty.getLevelOfDifficulty() * generateRandomRate() * PC_RATE);
    }

    /**
     * Method that create a new Enemy Non-Player Character.
     * 
     * @param type       the type of the enemy.
     * @param difficulty rate multiplied to basic statistics.
     * @return a Non-Player Character.
     * @throws NullPointerException if type of difficulty parameters are null.
     */
    public static BasicCharacter createEnemy(final EnemyCharacterType type, final Difficulty difficulty) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(difficulty);
        if (type == EnemyCharacterType.GOBLIN) {
            return new Goblin(type.toString(), generateRandomRate() * difficulty.getLevelOfDifficulty());
        }
        return new Headless(type.toString(), difficulty.getLevelOfDifficulty() * generateRandomRate());
    }

    /**
     * Method that create a new Boss Character.
     * 
     * @param difficulty a rate multiplied to basic statistics.
     * @return a Boss Non-PlayerCharacter.
     * @throws NullPointerException if difficulty parameter is null.
     */
    public static BasicCharacter createFinalBoss(final Difficulty difficulty) {
        Objects.requireNonNull(difficulty);
        return new DarkDestructor(BOSS_NAME, difficulty.getLevelOfDifficulty() * BOSS_RATE * generateRandomRate());
    }

    // Generate Random value between 1 and 2.
    private static double generateRandomRate() {
        return (Math.random() * (UPPER - LOWER) + LOWER) / LOWER;
    }
}
