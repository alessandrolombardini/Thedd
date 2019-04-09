package thedd.model.world.floor;

import java.util.Optional;

import thedd.model.world.Difficulty;

/**
 * This class represent the content of a future floor.
 *
 */
public final class FloorDetailsImpl implements FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfRooms;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfContraptions;
    private final boolean isBossFloor;

    private FloorDetailsImpl(final Difficulty difficulty, final int numberOfRooms, final int numberOfEnemies,
                             final int numberOfTreasure, final int numberOfContraptions, final boolean isBossFloor) {
        this.difficulty = difficulty;
        this.numberOfRooms = numberOfRooms;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfContraptions = numberOfContraptions;
        this.isBossFloor = isBossFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfTreasures() {
        return this.numberOfTreasure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfContraptions() {
        return this.numberOfContraptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Difficulty getDifficult() {
        return this.difficulty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBossFloor() {
        return this.isBossFloor;
    }

    @Override
    public String toString() {
        return "FloorDetails [difficulty=" + this.difficulty + ", numberOfRooms=" + this.numberOfRooms
                + ", numberOfEnemies=" + this.numberOfEnemies + ", numberOfTreasure=" + this.numberOfTreasure
                + ", numberOfContraptions=" + this.numberOfContraptions + ", boosFloor=" + this.isBossFloor + "]";
    }

    @Override
    public int hashCode() {
        return this.difficulty.getLevelOfDifficulty() ^ this.numberOfContraptions ^ this.numberOfEnemies
                ^ this.numberOfTreasure ^ this.numberOfRooms ^ (this.isBossFloor() ? 1 : 0);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FloorDetailsImpl) {
            final FloorDetailsImpl other = (FloorDetailsImpl) obj;
            return this.difficulty == other.difficulty 
                    && this.numberOfContraptions == other.numberOfContraptions
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
    public static class FloorDetailsBuilder {

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
        public FloorDetailsBuilder() {
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
        public FloorDetailsBuilder setDifficulty(final Difficulty difficulty) {
            this.difficulty = Optional.ofNullable(difficulty);
            return this;
        }

        /**
         * This method allows to set the number of rooms.
         * 
         * @param numberOfRooms to set
         * @return this builder
         */
        public FloorDetailsBuilder setNumberOfRooms(final int numberOfRooms) {
            this.numberOfRooms = Optional.of(numberOfRooms).filter(n -> n >= MIN_NUMBER_OF_ROOMS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of enemies.
         * 
         * @param numberOfEnemies to set
         * @return this builder
         */
        public FloorDetailsBuilder setNumberOfEnemies(final int numberOfEnemies) {
            this.numberOfEnemies = Optional.of(numberOfEnemies).filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of treasures.
         * 
         * @param numberOfTreasures to set
         * @return this builder
         */
        public FloorDetailsBuilder setNumberOfTreasures(final int numberOfTreasures) {
            this.numberOfTreasures = Optional.of(numberOfTreasures).filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set the number of contraptions.
         * 
         * @param numberOfContraptions to set
         * @return this builder
         */
        public FloorDetailsBuilder setNumberOfContraptions(final int numberOfContraptions) {
            this.numberOfContraptions = Optional.of(numberOfContraptions)
                                                .filter(n -> n >= MIN_NUMBER_OF_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * This method allows to set if this is the boos floor.
         * 
         * @param isBossFloor it should be true if this is the boss floor
         * @return this builder
         */
        public FloorDetailsBuilder setIsLastFloor(final boolean isBossFloor) {
            this.isBossFloor = Optional.of(isBossFloor);
            return this;
        }

        /**
         * This method allows to build the FloorDetails object.
         * 
         * @return FloorDetails object
         * @throws IllegalStateException if not all arguments are valid
         */
        public FloorDetailsImpl build() throws IllegalStateException {
            if (!this.difficulty.isPresent() || !this.numberOfContraptions.isPresent()
                    || !this.numberOfEnemies.isPresent() || !this.numberOfTreasures.isPresent()
                    || !this.numberOfRooms.isPresent() || !this.isBossFloor.isPresent()) {
                throw new IllegalStateException(ERROR_INVALIDPARAMS);
            }
            return new FloorDetailsImpl(this.difficulty.get(), this.numberOfRooms.get(), this.numberOfEnemies.get(),
                                        this.numberOfTreasures.get(), this.numberOfContraptions.get(), 
                                        this.isBossFloor.get());
        }

        @Override
        public final String toString() {
            return "Builder [difficulty=" + difficulty + ", numberOfRooms=" + numberOfRooms + ", numberOfEnemies="
                    + numberOfEnemies + ", numberOfTreasures=" + numberOfTreasures + ", numberOfContraptions="
                    + numberOfContraptions + ", isBossFloor=" + isBossFloor + "]";
        }

    }

}
