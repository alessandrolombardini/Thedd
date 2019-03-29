package thedd.model.character;

import java.util.Optional;

import thedd.model.character.types.DarkDestructorNPC;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.character.types.GoblinNPC;
import thedd.model.character.types.HeadlessNPC;
import thedd.model.character.types.PlayerCharacter;

/**
 * Implementation of a character factory.
 */
public final class CharacterFactory {

    private CharacterFactory() {
    }

    /**
     * Method that create a new PlayerCharacter.
     * 
     * @param name chosen by the player.
     * @return a Player Character.
     */
    public static BasicCharacter createPlayerCharacter(final Optional<String> name) {
        return new PlayerCharacter(name);
    }

    /**
     * Method that create a new Enemy Non-Player Character.
     * 
     * @param type       the type of the enemy.
     * @param multiplier a rate multiplied to basic statistics.
     * @return a Non-Player Character.
     */
    public static BasicCharacter createEnemy(final EnemyCharacterType type, final int multiplier) {
        if (type.equals(EnemyCharacterType.GOBLIN)) {
            return new GoblinNPC(multiplier);
        } else {
            return new HeadlessNPC(multiplier);
        }
    }

    /**
     * Method that create a new Boss Character.
     * 
     * @param multiplier a rate multiplied to basic statistics.
     * @return a Boss Non-PlayerCharacter.
     */
    public static BasicCharacter createFinalBoss(final int multiplier) {
        return new DarkDestructorNPC(multiplier);
    }
}
