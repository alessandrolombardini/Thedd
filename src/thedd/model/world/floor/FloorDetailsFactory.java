package thedd.model.world.floor;

import thedd.model.world.Difficulty;

/**
 * Interface that define the factory of floor details.
 */
public interface FloorDetailsFactory {

    /**
     * This method allows to get a FloorDetails' object with pseudo-random values
     * based on level of difficulty, number of rooms and if the floor is the last.
     * 
     * @param difficulty    of the floor
     * @param numberOfRooms of the floor
     * @param lastFloor     that define if the floor is the last
     * @return a FloorDetails' object
     * @throws NullPointerException     if difficulty is null
     * @throws IllegalArgumentException if the number of rooms is less than the
     *                                  MIN_NUMBER_OF_ROOMS or MIN_NUMBER_OF_FLOORS.
     */
    FloorDetails createFloorDetails(Difficulty difficulty, int numberOfRooms, boolean lastFloor);
}
