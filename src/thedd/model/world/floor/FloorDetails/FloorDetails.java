package thedd.model.world.floor.FloorDetails;

import thedd.model.world.Difficulty;

/**
 * This class represent the numericaly content of a floor.
 */
public interface FloorDetails {

    /**
     * This method allows to get the number of rooms of the floor.
     * 
     * @return the number of rooms
     */
    int getNumberOfRooms();

    /**
     * This method allows to get the number of enemies of the floor.
     * 
     * @return the number of enemies
     */
    int getNumberOfEnemies();

    /**
     * This method allows to get the number of treasures of the floor.
     * 
     * @return the number of treasures
     */
    int getNumberOfTreasures();

    /**
     * This method allows to get the number of contraptions of the floor.
     * 
     * @return the number of contraptions
     */
    int getNumberOfContraptions();

    /**
     * This method allows to get the level of difficulty of the floor.
     * 
     * @return the level of difficulty
     */
    Difficulty getDifficult();

    /**
     * This method allows to know if this is the floor of the boss.
     * 
     * @return true if is the boss floor
     */
    boolean isBossFloor();
}
