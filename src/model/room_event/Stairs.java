package model.room_event;

/**
 * Implementation of Stairs type of {@link model.room_event.RoomEvent}.
 * It is mandatory as they are always placed at the end of the floor.
 *
 */
public final class Stairs extends AbstractFloorChangerEvent implements RoomEvent {

    private static final String NAME = "Stairs";

    /**
     * One can always use the Stairs.
     */
    public Stairs() {
        super(NAME, () -> true);
    }

    @Override
    public boolean isSkippable() {
        return false;
    }
}
