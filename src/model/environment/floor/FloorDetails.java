package model.environment.floor;

import java.util.Optional;
import model.environment.enums.Difficulty;

/**
 * This class represent the content of a future floor.
 *
 */
public final class FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfRooms;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfInteractableAction;
    private final boolean isBossFloor;

    private FloorDetails(final Difficulty difficulty, final int numberOfRooms, final int numberOfEnemies,
                         final int numberOfTreasure, final int numberOfContraptions, final boolean isBossFloor) {
        this.difficulty = difficulty;
        this.numberOfRooms = numberOfRooms;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfInteractableAction = numberOfContraptions;
        this.isBossFloor = isBossFloor;
    }

    /**
     * This method allows to get the number of rooms of the floor.
     * 
     * @return the number of rooms
     */
    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * This method allows to get the number of enemies of the floor.
     * 
     * @return the number of enemies
     */
    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    /**
     * This method allows to get the number of treasures of the floor.
     * 
     * @return the number of treasures
     */
    public int getNumberOfTreasures() {
        return this.numberOfTreasure;
    }

    /**
     * This method allows to get the number of contraptions of the floor.
     * 
     * @return the number of contraptions
     */
    public int getNumberOfContraptions() {
        return this.numberOfInteractableAction;
    }

    /**
     * This method allows to get the level of difficulty of the floor.
     * 
     * @return the level of difficulty
     */
    public Difficulty getDifficult() {
        return this.difficulty;
    }

    /**
     * This method allows to know if this is the floor of the boss.
     * 
     * @return true if is the boss floor
     */
    public boolean isBossFloor() {
        return this.isBossFloor;
    }

    @Override
    public String toString() {
        return "FloorDetails [difficulty=" + difficulty + ", numberOfRooms=" + numberOfRooms + ", numberOfEnemies="
                + numberOfEnemies + ", numberOfTreasure=" + numberOfTreasure + ", numberOfInteractableAction="
                + numberOfInteractableAction + ", boosFloor=" + isBossFloor + "]";
    }

    @Override
    public int hashCode() {
        return this.difficulty.getLevelOfDifficulty() ^ this.numberOfInteractableAction  ^ this.numberOfEnemies 
                ^ this.numberOfTreasure ^ this.numberOfRooms ^ (this.isBossFloor() ? 1 : 0);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FloorDetails) {
            final FloorDetails other = (FloorDetails) obj;
            return this.difficulty == other.difficulty 
                    && this.numberOfInteractableAction == other.numberOfInteractableAction
                    && this.numberOfEnemies == other.numberOfEnemies
                    && this.numberOfTreasure == other.numberOfTreasure
                    && this.numberOfRooms == other.numberOfRooms
                    && this.isBossFloor == other.isBossFloor;
        } 
        return false;
    }

    /**
     * This class represent a FloorDetails builder.
     */
    public static class Builder {

        private static final String ERROR_INVALIDPARAMS = "One or more params hasn't been setted or wasn't valid";
        private static final int MIN_NUMBER_OF_ROOMS_PER_FLOOR = 1;
        private static final int MIN_NUMBER_OF_CONTENTS_PER_FLOOR = 0;

        private Optional<Difficulty> difficulty;
        private Optional<Integer> numberOfRooms;
        private Optional<Integer> numberOfEnemies;
        private Optional<Integer> numberOfTreasures;
        private Optional<Integer> numberOfContraptions;
        private Optional<Boolean> isBossFloor;

        /**
         * FloorDetails builder' constructor.
         */
        public Builder() {
            this.difficulty = Optional.empty();
            this.numberOfRooms = Optional.empty();
            this.numberOfEnemies = Optional.empty();
            this.numberOfTreasures = Optional.empty();
            this.numberOfContraptions = Optional.empty();
            this.isBossFloor = Optional.empty();
        }


        /**
         * This method allows to set the difficulty.
         * 
         * @param difficulty level to set
         * @return this builder
         */
        public Builder difficulty(final Difficulty difficulty) {
            this.difficulty = Optional.ofNullable(difficulty);
            return this;
        }

        /**
         * This method allows to set the number of rooms.
         * 
         * @param numberOfRooms to set
         * @return this builder
         */
        public Builder rooms(final int numberOfRooms) {
            this.numberOfContraptions = Optional.of(numberOfRooms).filter(n -> n >= MIN_NUMBER_OF_ROOMS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of enemies.
         * 
         * @param numberOfEnemies to set
         * @return this builder
         */
        public Builder enemies(final int numberOfEnemies) {
            this.numberOfEnemies = Optional.of(numberOfEnemies).filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of treasures.
         * 
         * @param numberOfTreasures to set
         * @return this builder
         */
        public Builder treasures(final int numberOfTreasures) {
            this.numberOfTreasures = Optional.of(numberOfTreasures).filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of contraptions.
         * 
         * @param numberOfContraptions to set
         * @return this builder
         */
        public Builder contraptions(final int numberOfContraptions) {
            this.numberOfContraptions = Optional.of(numberOfContraptions).filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set if this is the boos floor.
         * 
         * @param isBossFloor it should be true if this is the boss floor
         * @return this builder
         */
        public Builder boss(final boolean isBossFloor) {
            this.isBossFloor = Optional.of(isBossFloor);
            return this;
        }

        /**
         * This method allows to build the FloorDetails object.
         * 
         * @return FloorDetails object
         * @throws IllegalStateException if not all arguments are valid
         */
        public FloorDetails build() throws IllegalStateException {
            if (!this.difficulty.isPresent() || !this.numberOfContraptions.isPresent()
                    || !this.numberOfEnemies.isPresent() || !this.numberOfTreasures.isPresent()
                    || !this.numberOfRooms.isPresent() || !this.isBossFloor.isPresent()) {
                throw new IllegalStateException(ERROR_INVALIDPARAMS);
            }
            return new FloorDetails(this.difficulty.get(), this.numberOfRooms.get(), this.numberOfEnemies.get(),
                                    this.numberOfTreasures.get(), this.numberOfContraptions.get(), this.isBossFloor.get());
        }
    }

}
