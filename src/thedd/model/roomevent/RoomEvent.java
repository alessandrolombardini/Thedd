package thedd.model.roomevent;

/**
 * Generic event inside a room.
 * It has a {@link thedd.model.roomevent.RoomEventType}.
 */
public interface RoomEvent {

    /**
     * Returns the {@link thedd.model.roomevent.RoomEventType} of the room.
     * @return the type of an event inside the room
     */
    RoomEventType getType();

    /**
     * Returns the name of the {@link thedd.model.roomevent.RoomEvent}.
     * @return
     *  the name of the event
     */
    String getName();

    /**
     * Returns whether the event is completed.
     * @return whether the RoomEvent is completed
     */
    boolean isCompleted();

    /**
     * Returns whether the event is skippable.
     * @return whether the RoomEvent is not mandatory
     */
    boolean isSkippable();
}
