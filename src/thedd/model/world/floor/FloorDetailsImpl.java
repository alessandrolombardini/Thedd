package thedd.model.world.floor;

import java.util.Optional;

import thedd.model.world.Difficulty;
import thedd.model.world.environment.EnvironmentImpl;

/**
 * This class represent the content of a future floor.
 */
public final class FloorDetailsImpl implements FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfRooms;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfContraptions;
    private final boolean isLastFloor;

    /**
     * FloorDetials constructor.
     * 
     * @param difficulty of the floor
     * @param numberOfRooms fo the floor
     * @param numberOfEnemies of the floor
     * @param numberOfTreasure of the floor
     * @param numberOfContraptions of the floor
     * @param isLastFloor of the floor
     */
    protected FloorDetailsImpl(final Difficulty difficulty, final int numberOfRooms, final int numberOfEnemies,
                             final int numberOfTreasure, final int numberOfContraptions, 
                             final boolean isLastFloor) {
        this.difficulty = difficulty;
        this.numberOfRooms = numberOfRooms;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfContraptions = numberOfContraptions;
        this.isLastFloor = isLastFloor;
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
        return this.isLastFloor;
    }

    @Override
    public String toString() {
        return "FloorDetails [difficulty=" + this.difficulty + ", numberOfRooms=" + this.numberOfRooms
                + ", numberOfEnemies=" + this.numberOfEnemies + ", numberOfTreasure=" + this.numberOfTreasure
                + ", numberOfContraptions=" + this.numberOfContraptions + ", boosFloor=" + this.isLastFloor + "]";
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
                    && this.isLastFloor == other.isLastFloor;
        }
        return false;
    }

    /**
     * Implementation of {@link thedd.model.world.floor.FloorDetailsBuilder}.
     */
    public static class FloorDetailsBuilderImpl implements FloorDetailsBuilder {

        private static final String ERROR_INVALIDPARAMS = "One or more params hasn't been setted or wasn't valid";
        private static final int MIN_NUMBER_CONTENTS_PER_FLOOR = 0;

        private Optional<Difficulty> difficulty;
        private Optional<Integer> numberOfRooms;
        private Optional<Integer> numberOfEnemies;
        private Optional<Integer> numberOfTreasures;
        private Optional<Integer> numberOfContraptions;
        private Optional<Boolean> isLastFloor;

        /**
         * FloorDetailsBuilderImpl constructor.
         */
        public FloorDetailsBuilderImpl() {
            this.difficulty = Optional.empty();
            this.numberOfRooms = Optional.empty();
            this.numberOfEnemies = Optional.empty();
            this.numberOfTreasures = Optional.empty();
            this.numberOfContraptions = Optional.empty();
            this.isLastFloor = Optional.empty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setDifficulty(final Difficulty difficulty) {
            this.difficulty = Optional.ofNullable(difficulty);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setNumberOfRooms(final int numOfRooms) {
            this.numberOfRooms = Optional.of(numOfRooms).filter(n -> n >= EnvironmentImpl.MIN_NUMBER_OF_ROOMS);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setNumberOfEnemies(final int numOfEnemies) {
            this.numberOfEnemies = Optional.of(numOfEnemies).filter(n -> n >= MIN_NUMBER_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setNumberOfTreasures(final int numOfTreasures) {
            this.numberOfTreasures = Optional.of(numOfTreasures).filter(n -> n >= MIN_NUMBER_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setNumberOfContraptions(final int numOfContraptions) {
            this.numberOfContraptions = Optional.of(numOfContraptions).filter(n -> n >= MIN_NUMBER_CONTENTS_PER_FLOOR);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetailsBuilder setIsLastFloor(final boolean isLastFloor) {
            this.isLastFloor = Optional.of(isLastFloor);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final FloorDetails build() throws IllegalStateException {
            if (!this.difficulty.isPresent() || !this.numberOfContraptions.isPresent()
                    || !this.numberOfEnemies.isPresent() || !this.numberOfTreasures.isPresent()
                    || !this.numberOfRooms.isPresent() || !this.isLastFloor.isPresent()) {
                throw new IllegalStateException(ERROR_INVALIDPARAMS);
            }
            return new FloorDetailsImpl(this.difficulty.get(), this.numberOfRooms.get(), this.numberOfEnemies.get(),
                                        this.numberOfTreasures.get(), this.numberOfContraptions.get(), 
                                        this.isLastFloor.get());
        }

        @Override
        public final String toString() {
            return "Builder [difficulty=" + this.difficulty + ", numberOfRooms=" + this.numberOfRooms
                    + ", numberOfEnemies=" + this.numberOfEnemies + ", numberOfTreasures=" + this.numberOfTreasures
                    + ", numberOfContraptions=" + this.numberOfContraptions + ", isBossFloor=" + this.isLastFloor + "]";
        }

    }

}
