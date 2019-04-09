package thedd.model.world.floor;

import thedd.model.world.enums.Difficulty;

/**
 * Interface that define the environment.
 * 
 */
public interface FloorDetailsFactory {

    /**
     * This method allows to get a FloorDetails' object with random values.
     * 
     * @param difficulty    of the floor
     * @param numberOfRooms of the floor
     * @param lastFloor     that define if the floor is the last
     * @return a FloorDetails' object
     * @throws NullPointerException if difficulty is null
     * @throws IllegalArgumentException if the number of rooms is less than the minimum
     */
    FloorDetails createFloorDetails(Difficulty difficulty, int numberOfRooms, boolean lastFloor);
}
