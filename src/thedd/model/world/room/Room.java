package thedd.model.world.room;

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

    /**
     * This method allows to add an event to the collection.
     * 
     * @param event that has to be added to the room
     * @throws NullPointerExeption if event is null
     */
    void addEvent(RoomEvent event);

    /**
     * This method allows to add all events passed.
     * 
     * @param events that has to be added to the room
     * @throws NullPointerException if events is null
     */
    void addAllEvents(List<RoomEvent> events);

    /**
     * This method allows to remove an event by the room.
     * 
     * @param event that has to be removed
     * @return true if the event has been removed
     * @throws NullPointerExeption if event is null
     */
    boolean removeEvent(RoomEvent event);

}
