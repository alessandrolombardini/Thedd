package model.environment;

/**
 * Interface that define the environment.
 *
 */
public interface Environment {

    /***
     * This method allows to set the next floor.
     * 
     * @param floor Next floor
     */
    void setNextFloor(Floor floor);

    /**
     * This method allows to get the current floor.
     * 
     * @return the current floor
     */
    Floor getCurrentFloor();

    /**
     * This method allows to get the current floor index.
     * 
     * @return the current floor index
     */
    int getCurrentFloorIndex();
}
