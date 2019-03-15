package model.environment;

import java.util.List;

/**
 * Interface that define the environment.
 *
 */
public interface Environment {

    /***
     * This method allows to set the next floor.
     * 
     * @param floorDetails is the details of the floor that has to be setted
     * @throws NullPointerExeption   if floorDetails is null
     * @throws IllegalStateException if the current floor isn't completed
     */
    void setNextFloor(FloorDetails floorDetails);

    /**
     * This method allows to get the current floor.
     * 
     * @return the current floor
     * @throws IllegalStateException if there aren't floors
     */
    Floor getCurrentFloor();

    /**
     * This method allows to get the current floor index.
     * The index is -1 if there aren't floor yet.
     * 
     * @return the current floor index
     */
    int getCurrentFloorIndex();

    /**
     * This method allows to know if the current floor is the last.
     * 
     * @return true if the current floor is the last
     */
    boolean isCurrentLastFloor();


}
