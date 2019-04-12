package thedd.controller;

/**
 * Interface describing the controller of the pattern MVC of this application.
 */
public interface Controller {

    /**
     * Start a new game session from a given informations.
     * 
     * @param playerName
     *          Name of the player
     * @param numberOfRooms
     *          Number of rooms of each floor
     * @param numberOfFloors
     *          Number of floor of game world
     * @return 
     *          true if values are valid to build a new world
     */
    boolean newGame(String playerName, String numberOfRooms, String numberOfFloors);

    /**
     * Close application.
     */
    void closeApplication();

}
