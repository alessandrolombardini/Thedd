package model.environment.environment;

import java.util.Optional;

import model.character.BasicCharacter;
import model.character.CharacterFactory;

/**
 * Implementation of {@link model.environment.Session}.
 */
public final class SessionImpl implements Session {

    private final Environment environment;
    private final BasicCharacter playerCharacter;

    /**
     * Session' constructor.
     * 
     * @param playerCharacterName is the name of the player character
     * @param numberOfLevels      is the number of levels of game's map
     * @param numberOfRooms       is the number of rooms of each floor of the map
     */
    public SessionImpl(final Optional<String> playerCharacterName, final int numberOfLevels, final int numberOfRooms) {
        this.playerCharacter = CharacterFactory.createPlayerCharacter(playerCharacterName);
        this.environment = new EnvironmentImpl(numberOfLevels, numberOfRooms);
    }

    @Override
    public BasicCharacter getPlayerCharacter() {
        return this.playerCharacter;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public String toString() {
        return "SessionImpl [environment=" + environment + ", playerCharacter=" + playerCharacter + "]";
    }

}
