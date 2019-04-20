package thedd.model;

import java.util.Optional;

import thedd.model.character.BasicCharacter;
import thedd.model.world.environment.Environment;

/**
 * This class describe the model of the pattern MVC.
 *
 */
public interface Model {

    /**
     * This method allows to set a new game session.
     * 
     * @param playerCharacterName name of the player character
     * @param numOfLevels         number of levels of map
     * @param numOfRooms          number of rooms of each floor of the map
     * @return true if all values are good and it's possible create a new game
     * 
     */
    boolean initGame(Optional<String> playerCharacterName, int numOfLevels, int numOfRooms);

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
