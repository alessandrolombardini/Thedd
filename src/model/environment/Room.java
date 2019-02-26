package model.environment;

import java.util.Set;

/**
 * 
 * Interface that define the room.
 *
 */
public interface Room {

    /**
     * This method allows to know if the current room is completed.
     * 
     * @return true if current room is completed
     */
    boolean checkToMoveOn();

    /**
     * This method allows to get all events of the current room.
     * 
     * @return a set that contains all events of the current room
     */
    Set<RoomEvent> getEvents();

    /**
     * This method allows to remove an event by the room. It is possible if the
     * event is completed or it isn't mandatory.
     * 
     * @param roomEvent that has to be removed by the room
     * @return true if the roomEvent has been removed
     */
    boolean removeEvent(RoomEvent roomEvent);

}
