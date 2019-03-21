package model.environment.floor;

import java.util.Objects;
import java.util.Optional;

import model.environment.enums.Difficulty;
import model.environment.room.RoomImpl;

/**
 * This class represent the content of a future floor.
 *
 */
public final class FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfInteractableAction;

    private FloorDetails(final Difficulty difficulty, final int numberOfEnemies, final int numberOfTreasure,
            final int numberOfAction) {
        this.difficulty = difficulty;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfInteractableAction = numberOfAction;
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

    @Override
    public String toString() {
        return "FloorDetails [difficulty=" + difficulty + ", numberOfEnemies=" + numberOfEnemies + ", numberOfTreasure="
                + numberOfTreasure + ", numberOfAction=" + numberOfInteractableAction + "]";
    }

    @Override
    public int hashCode() {
        return difficulty.getLevelOfDifficulty() + numberOfInteractableAction  + numberOfEnemies + numberOfTreasure;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!Objects.nonNull(obj) || !(obj instanceof FloorDetails)) {
            return false;
        } 
        final FloorDetails other = (FloorDetails) obj;
        if (!(difficulty == other.difficulty) || !(numberOfInteractableAction == other.numberOfInteractableAction)
                || !(numberOfEnemies == other.numberOfEnemies) || !(numberOfTreasure == other.numberOfTreasure)) {
            return false;
        }
        return true;
    }

    /**
     * This class represent a FloorDetails builder.
     */
    public static class Builder {

        private Optional<Difficulty> difficulty;
        private Optional<Integer> numberOfEnemies;
        private Optional<Integer> numberOfTreasures;
        private Optional<Integer> numberOfContraptions;

        /**
         * FloorDetails builder' constructor.
         */
        public Builder() {
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
            if (!this.difficulty.isPresent() || !this.numberOfContraptions.isPresent()
                    || !this.numberOfEnemies.isPresent() || !this.numberOfTreasures.isPresent()) {
                throw new IllegalStateException();
            }
            return new FloorDetails(this.difficulty.get(), this.numberOfEnemies.get(), this.numberOfTreasures.get(),
                    this.numberOfContraptions.get());
        }
    }

}
