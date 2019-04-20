package thedd.model;

import java.util.Objects;
import java.util.Optional;
import thedd.model.character.BasicCharacter;
import thedd.model.character.CharacterFactory;
import thedd.model.world.Difficulty;
import thedd.model.world.environment.Environment;
import thedd.model.world.environment.EnvironmentImpl;

/**
 * Implementation of {@link thedd.model.Model}.
 */
public final class ModelImpl implements Model {

    private static final String ERROR_NOGAMESETTED = "Game hasn't been setted yet";
    private static final String ERROR_GAMEALREDYSETTED = "Game has been alredy setted";

    private Optional<Environment> environment;
    private Optional<BasicCharacter> playerCharacter;

    /**
     * ModelImpl constructor.
     */
    public ModelImpl() {
        this.environment = Optional.empty();
        this.playerCharacter = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCharacter getPlayerCharacter() {
        if (!this.playerCharacter.isPresent()) {
            throw new IllegalArgumentException(ERROR_NOGAMESETTED);
        }
        return this.playerCharacter.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Environment getEnvironment() {
        if (!this.environment.isPresent()) {
            throw new IllegalArgumentException(ERROR_NOGAMESETTED);
        }
        return this.environment.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerWon() {
        if (!this.environment.isPresent() || !this.playerCharacter.isPresent()) {
            throw new IllegalArgumentException(ERROR_NOGAMESETTED);
        }
        return this.environment.get().isCurrentLastFloor() 
                && !this.environment.get().getCurrentFloor().hasNextRoom()
                && this.environment.get().getCurrentFloor().getCurrentRoom().checkToMoveOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean initGame(final Optional<String> playerCharacterName, final int numOfLevels, final int numOfRooms) {
        if (this.environment.isPresent() || this.playerCharacter.isPresent()) {
            throw new IllegalStateException(ERROR_GAMEALREDYSETTED);
        }
        Objects.requireNonNull(playerCharacterName);
        if (numOfLevels < EnvironmentImpl.MIN_NUMBER_OF_FLOORS || numOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            return false;
        }
        this.playerCharacter = Optional.of(CharacterFactory.createPlayerCharacter(playerCharacterName, 
                                                                                  Difficulty.NORMAL));
        this.environment = Optional.of(new EnvironmentImpl(numOfLevels, numOfRooms));
        return true;
    }

}
