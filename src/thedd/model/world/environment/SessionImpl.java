package thedd.model.world.environment;

import java.util.Objects;
import java.util.Optional;

import model.character.BasicCharacter;
import model.character.CharacterFactory;

/**
 * Implementation of {@link thedd.model.world.Session}.
 */
public final class SessionImpl implements Session {

    private final Environment environment;
    private final BasicCharacter playerCharacter;

    /**
     * Session' constructor.
     * 
     * @param playerCharacterName 
     *          name of the player character
     * @param numberOfLevels 
     *          number of levels of game's map
     * @param numberOfRooms 
     *          number of rooms of each floor of the map
     */
    private SessionImpl(final Optional<String> playerCharacterName, final int numberOfLevels, final int numberOfRooms) {
        Objects.requireNonNull(playerCharacterName);
        this.playerCharacter = CharacterFactory.createPlayerCharacter(playerCharacterName);
        this.environment = new EnvironmentImpl(numberOfLevels, numberOfRooms);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SessionImpl [environment=" + environment + ", playerCharacter=" + playerCharacter + "]";
    }

    /**
     * Builder of Session.
     *
     */
    public static class SessionBuilder {

        private Optional<String> playerCharacterName;
        private Optional<Integer> numberOfRooms;
        private Optional<Integer> numberOfFloors;
        private boolean built;

        /**
         * Builder' constructor.
         */
        public SessionBuilder() {
            this.playerCharacterName = Optional.empty();
            this.numberOfRooms = Optional.empty();
            this.numberOfFloors = Optional.empty();
            this.built = false;
        }

        /**
         * This method allows to set the name of the player character.
         * 
         * @param name 
         *              name of the player character
         * @return the builder
         */
        public SessionBuilder playerCharacterName(final String name) {
            this.playerCharacterName = Optional.ofNullable(name);
            return this;
        }

        /**
         * This method allows to set the number of rooms.
         * 
         * @param num 
         *              number of rooms
         * @return the builder
         */
        public SessionBuilder numberOfRooms(final int num) {
            this.numberOfRooms = Optional.of(num);
            return this;
        }

        /**
         * This method allows to set the number of floors.
         * 
         * @param num 
         *              number of floors
         * @return the builder
         */
        public SessionBuilder numberOfFloors(final int num) {
            this.numberOfFloors = Optional.of(num);
            return this;
        }

        /**
         * This method build the session.
         * 
         * @return a SessionImpl
         * @throws IllegalStateException if number of floors or rooms is not acceptable
         */
        public SessionImpl build() throws IllegalStateException {
            if (!this.numberOfRooms.isPresent() || !this.numberOfFloors.isPresent() || this.built) {
                throw new IllegalStateException();
            }
            this.built = true;
            return new SessionImpl(this.playerCharacterName, this.numberOfFloors.get(), this.numberOfRooms.get());
        }

    }

}
