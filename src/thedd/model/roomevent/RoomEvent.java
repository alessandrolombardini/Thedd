package thedd.model.roomevent;

/**
 * Generic event inside a room. It has a {@link thedd.model.roomevent.RoomEventType}.
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

    /**
     * 
     * @return whether the RoomEvent is completed
     */
    boolean isCompleted();

    /**
     * 
     * @return whether the RoomEvent is not mandatory
     */
    boolean isSkippable();
}
