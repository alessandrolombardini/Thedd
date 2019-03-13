package model.environment;

/**
 * This class represent the content of a future floor.
 *
 */
public final class FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfAction;
    private final int numberOfRooms;
    private final boolean isLastFloor;

    private FloorDetails(final Difficulty difficulty, final int numberOfRooms, final boolean isLastFloor,
            final int numberOfEnemies, final int numberOfTreasure, final int numberOfAction) {
        this.difficulty = difficulty;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfAction = numberOfAction;
        this.numberOfRooms = numberOfRooms;
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
     * This method allows to get the number of rooms of the floor.
     * 
     * @return the number of rooms
     */
    public int getNumberOfRooms() {
        return this.numberOfRooms;
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
     * This allows to know if the floor is the last and there is a boss at the end
     * of it.
     * 
     * @return true if the floor is the last
     */
    public boolean isLastFloor() {
        return this.isLastFloor;
    }

    /**
     * This class represent a FloorDetails builder.
     * 
     */
    public static class Builder {

        private Difficulty difficulty;
        private int numberOfEnemies;
        private int numberOfTreasure;
        private int numberOfContraptions;
        private int numberOfRooms;
        private boolean isLastFloor;

        /**
         * FloorDetails builder' constructor.
         * 
         * @param difficulty    of this floor
         * @param numberOfRooms of this floor
         * @param isLastFloor   is true if this floor is the last
         */
        public Builder(final Difficulty difficulty, final int numberOfRooms, final boolean isLastFloor) {
            this.difficulty = difficulty;
            this.numberOfRooms = numberOfRooms;
            this.isLastFloor = isLastFloor;
        }

        /**
         * This method allows to set the number of enemies.
         * 
         * @param numberOfEnemies to set
         * @return this builder
         */
        public Builder enemies(final int numberOfEnemies) {
            this.numberOfEnemies = numberOfEnemies;
            return this;
        }

        /**
         * This method allows to set the number of treasures.
         * 
         * @param numberOfTreasures to set
         * @return this builder
         */
        public Builder treasures(final int numberOfTreasures) {
            this.numberOfTreasure = numberOfTreasures;
            return this;
        }

        /**
         * This method allows to set the number of contraptions.
         * 
         * @param numberOfContraptions to set
         * @return this builder
         */
        public Builder contraptions(final int numberOfContraptions) {
            this.numberOfContraptions = numberOfContraptions;
            return this;
        }

        /**
         * This method allows to build the FloorDetails object.
         * 
         * @return FloorDetails object
         * @throws IllegalStateException if not all arguments are good
         */
        public FloorDetails build() throws IllegalStateException {
            if (false) {
                throw new IllegalStateException("");
            }
            return new FloorDetails(this.difficulty, this.numberOfRooms, this.isLastFloor, this.numberOfEnemies,
                    this.numberOfTreasure, this.numberOfContraptions);
        }
    }

}
