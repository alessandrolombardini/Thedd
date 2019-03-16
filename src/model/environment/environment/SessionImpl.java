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
     * @param playerCharacterName is the name of the player character
     * @param numberOfLevels is the number of levels of game's map
     * @param numberOfRooms is the number of rooms of each floor of the map
     */
    private SessionImpl(final Optional<String> playerCharacterName, final int numberOfLevels, final int numberOfRooms) {
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

    /**
     * Builder of Session.
     *
     */
    public static class Builder {

        private Optional<String> playerCharacterName;
        private Optional<Integer> numberOfRooms;
        private Optional<Integer> numberOfFloors;

        /**
         * Builder' constructor.
         */
        public Builder() {
            this.playerCharacterName = Optional.empty();
            this.numberOfRooms = Optional.empty();
            this.numberOfFloors = Optional.empty();
        }

        /**
         * This method allows to set the name of the player character.
         * @param name is the name of the player character
         * @return the builder
         */
        public Builder playerCharacterName(final String name) {
            this.playerCharacterName = Optional.ofNullable(name);
            return this;
        }

        /**
         * This method allows to set the number of rooms.
         * @param num is the number of rooms
         * @return the builder
         */
        public Builder numberOfRooms(final int num) {
            this.numberOfRooms = Optional.of(num);
            return this;
        }

        /**
         * This method allows to set the number of floors.
         * @param num is the number of floors
         * @return the builder
         */
        public Builder numberOfFloors(final int num) {
            this.numberOfRooms = Optional.of(num);
            return this;
        }

        /**
         * This method build the session.
         * @return a SessionImpl
         * @throws IllegalStateException if number of floors or rooms is not acceptable
         */
        public SessionImpl build() throws IllegalStateException {
            if (!this.numberOfRooms.isPresent() || !this.numberOfFloors.isPresent()) {
                throw new IllegalStateException();
            }
            return new SessionImpl(this.playerCharacterName, this.numberOfFloors.get(), this.numberOfRooms.get());
        }

    }

}
