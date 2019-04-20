package thedd.model.world.floor.FloorDetails;


import thedd.model.world.Difficulty;

/**
 * This interface represent a FloorDetails builder.
 */
public interface FloorDetailsBuilder {

    /**
     * This method allows to set the difficulty.
     * 
     * @param difficulty level to set
     * @return this builder
     */
    FloorDetailsBuilder setDifficulty(Difficulty difficulty);

    /**
     * This method allows to set the number of rooms.
     * 
     * @param numberOfRooms to set
     * @return this builder
     */
    FloorDetailsBuilder setNumberOfRooms(int numberOfRooms);
    /**
     * This method allows to set the number of enemies.
     * 
     * @param numberOfEnemies to set
     * @return this builder
     */
    FloorDetailsBuilder setNumberOfEnemies(int numberOfEnemies);

    /**
     * This method allows to set the number of treasures.
     * 
     * @param numberOfTreasures to set
     * @return this builder
     */
    FloorDetailsBuilder setNumberOfTreasures(int numberOfTreasures);

    /**
     * This method allows to set the number of contraptions.
     * 
     * @param numberOfContraptions to set
     * @return this builder
     */
    FloorDetailsBuilder setNumberOfContraptions(int numberOfContraptions);

    /**
     * This method allows to set if this is the boos floor.
     * 
     * @param isLastFloor it should be true if this is the boss floor
     * @return this builder
     */
    FloorDetailsBuilder setIsLastFloor(boolean isLastFloor);

    /**
     * This method allows to build the FloorDetails object.
     * 
     * @return FloorDetails object
     * @throws IllegalStateException if not all arguments are valid
     */
    FloorDetails build() throws IllegalStateException;
}
