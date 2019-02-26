package model.room_event;

/**
 * Generic event inside a room. It has a {@link model.room_event.RoomEventType}.
 *
 */
public interface RoomEvent {
    /**
     * 
     * @return the type of an event inside the room
     */
    RoomEventType getType();

    /**
     * @return
     *  the name of the contraption
     */
    String getName();
    
    boolean isCompleted();
    
}
