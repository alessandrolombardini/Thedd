package model.environment;

import java.util.List;

import model.room_event.RoomEvent;

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
    List<RoomEvent> getEvents();


}
