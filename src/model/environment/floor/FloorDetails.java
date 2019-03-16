package model.environment.floor;

import java.util.Optional;

import model.environment.enums.Difficulty;

/**
 * This class represent the content of a future floor.
 *
 */
public final class FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfAction;
    private final boolean isLastFloor;

    private FloorDetails(final Difficulty difficulty, final boolean isLastFloor, final int numberOfEnemies,
            final int numberOfTreasure, final int numberOfAction) {
        this.difficulty = difficulty;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfAction = numberOfAction;
        this.isLastFloor = isLastFloor;
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
        return this.numberOfAction;
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
     * This allows to know if the floor is the last and there is a boss at the end of it.
     * 
     * @return true if the floor is the last
     */
    public boolean isLastFloor() {
        return this.isLastFloor;
    }

    /**
     * This class represent a FloorDetails builder.
     */
    public static class Builder {

        private Optional<Difficulty> difficulty;
        private Optional<Integer> numberOfEnemies;
        private Optional<Integer> numberOfTreasures;
        private Optional<Integer> numberOfContraptions;
        private Optional<Boolean> isLastFloor;

        /**
         * FloorDetails builder' constructor.
         * 
         * @param difficulty  of this floor
         * @param isLastFloor is true if this floor is the last
         */
        public Builder(final Difficulty difficulty, final boolean isLastFloor) {
            this.difficulty = Optional.ofNullable(difficulty);
            this.isLastFloor = Optional.ofNullable(isLastFloor);
        }

        /**
         * This method allows to set the number of enemies.
         * 
         * @param numberOfEnemies to set
         * @return this builder
         */
        public Builder enemies(final int numberOfEnemies) {
            this.numberOfEnemies = Optional.ofNullable(numberOfEnemies);
            return this;
        }

        /**
         * This method allows to set the number of treasures.
         * 
         * @param numberOfTreasures to set
         * @return this builder
         */
        public Builder treasures(final int numberOfTreasures) {
            this.numberOfTreasures = Optional.ofNullable(numberOfTreasures);
            return this;
        }

        /**
         * This method allows to set the number of contraptions.
         * 
         * @param numberOfContraptions to set
         * @return this builder
         */
        public Builder contraptions(final int numberOfContraptions) {
            this.numberOfContraptions = Optional.ofNullable(numberOfContraptions);
            return this;
        }

        /**
         * This method allows to build the FloorDetails object.
         * 
         * @return FloorDetails object
         * @throws IllegalStateException if not all arguments are valid
         */
        public FloorDetails build() throws IllegalStateException {
            if (!this.difficulty.isPresent() || !this.isLastFloor.isPresent() || !this.numberOfContraptions.isPresent()
                    || !this.numberOfEnemies.isPresent() || !this.numberOfTreasures.isPresent()) {
                throw new IllegalStateException();
            }
            return new FloorDetails(this.difficulty.get(), this.isLastFloor.get(), this.numberOfEnemies.get(), this.numberOfTreasures.get(),
                    this.numberOfContraptions.get());
        }
    }

}
