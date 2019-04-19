package thedd.model.world.environment;

import thedd.model.character.BasicCharacter;

/**
 * Interface that represent a game session.
 */
public interface Session {

    /**
     * This method allows to get the player character of the game session.
     * 
     * @return the player character
     */
    BasicCharacter getPlayerCharacter();

    /**
     * This method allows to get the environment of the game session.
     * 
     * @return the environment
     */
    Environment getEnvironment();

    /**
     * This method allows to know if the player has won.
     * 
     * @return true if the player has won
     */
    boolean hasPlayerWon();

}
