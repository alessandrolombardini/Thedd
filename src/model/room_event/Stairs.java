package model.room_event;

/**
 * Implementation of Stairs type of {@link model.room_event.RoomEvent}.
 *
 */
public final class Stairs implements RoomEvent {

    @Override
    public RoomEventType getType() {
        return RoomEventType.STAIRS;
    }

}
