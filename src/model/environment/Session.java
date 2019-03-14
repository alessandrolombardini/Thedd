package model.environment;

import model.character.BasicCharacter;

/**
 * Interface that define a game session.
 *
 */
public interface Session {

    /**
     * This method allows to get the player character of the game session.
     * @return the player character
     */
    BasicCharacter getPlayerCharacter();

    /**
     * This method allows to get the environment of the game session.
     * @return the environment
     */
    Environment getEnvironment();
}
