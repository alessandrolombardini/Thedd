package thedd.model.world.environment;

import java.util.Objects;
import java.util.Optional;

import thedd.model.character.BasicCharacter;
import thedd.model.character.CharacterFactory;
import thedd.model.world.Difficulty;

/**
 * Implementation of {@link thedd.model.world.environment.Session}.
 */
public final class SessionImpl implements Session {

    private static final String ERROR_PARAMSNOTVALID = "Incorrect number of levels or rooms, under the minimum";

    private final Environment environment;
    private final BasicCharacter playerCharacter;

    /**
     * Session' constructor.
     * 
     * @param playerCharacterName name of the player character
     * @param numOfLevels         number of levels of map
     * @param numOfRooms          number of rooms of each floor of the map
     */
    public SessionImpl(final Optional<String> playerCharacterName, final int numOfLevels, final int numOfRooms) {
        Objects.requireNonNull(playerCharacterName);
        if (numOfLevels < EnvironmentImpl.MIN_NUMBER_OF_FLOORS || numOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException(ERROR_PARAMSNOTVALID);
        }
        this.playerCharacter = CharacterFactory.createPlayerCharacter(playerCharacterName, Difficulty.NORMAL);
        this.environment = new EnvironmentImpl(numOfLevels, numOfRooms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCharacter getPlayerCharacter() {
        return this.playerCharacter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public String toString() {
        return "SessionImpl [environment=" + this.environment + ", playerCharacter=" + this.playerCharacter + "]";
    }
}
