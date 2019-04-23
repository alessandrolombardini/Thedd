package thedd.model.character;

import thedd.model.character.types.EnemyCharacterType;
import thedd.model.character.types.Goblin;
import thedd.model.character.types.Headless;

/**
 * Implementation of character's factory.
 */
public final class RandomEnemyFactory {

    private RandomEnemyFactory() {

    }

    /**
     * Method that create a new random (non-boss) Enemy Character.
     * 
     * @return a new random Enemy Character.
     */
    public static BasicCharacter createRandomEnemy() {
        final EnemyCharacterType type = EnemyCharacterType.getRandom();
        if (type == EnemyCharacterType.GOBLIN) {
            return Goblin.getNewInstance();
        }
        return Headless.getNewInstance();
    }
}
