package model.room_event;

/**
 * Implementation of Stairs type of {@link model.room_event.RoomEvent}.
 *
 */
public final class Stairs extends AbstractRoomEvent implements RoomEvent {

    private static final String NAME = "Stairs";

    /**
     * Create an instance of Stairs.
     */
    public Stairs() {
        super(NAME);
    }

    @Override
    public RoomEventType getType() {
        return RoomEventType.STAIRS;
    }

}
