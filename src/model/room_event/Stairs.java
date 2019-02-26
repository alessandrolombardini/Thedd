package model.room_event;

/**
 * Implementation of Stairs type of {@link model.room_event.RoomEvent}.
 *
 */
public final class Stairs implements RoomEvent {

    private static final String NAME = "Stairs";

    @Override
    public RoomEventType getType() {
        return RoomEventType.STAIRS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

}
