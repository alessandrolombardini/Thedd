package model.environment;

/**
 * Interface that define the floor.
 * 
 */
public interface Floor {

    /**
     * This method allows to know if the current floor has other unexplored rooms.
     * 
     * @return true if the current floor has other unexplored rooms
     */
    boolean hasNextRoom();

    /**
     * This method allows to move, if the current room is completed, to the next
     * one.
     * 
     * @return true if the current room has been changed
     * @throws IllegalStateExeption if there aren't other unexplored rooms
     */
    boolean nextRoom();

    /**
     * This method allows to get the current room.
     * 
     * @return the current room
     */
    Room getCurrentRoom();

    /**
     * This method allows to get the index of the current room.
     * 
     * @return the current room index
     */
    int getCurrentRoomIndex();

    /**
     * This method allows to know if this floor is completed.
     * 
     * @return true if the current floor is completed
     */
    boolean checkToChangeFloor();
}
