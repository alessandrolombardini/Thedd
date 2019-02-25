package model.room_event;

import java.util.Objects;

/**
 * 
 *
 */
public abstract class AbstractRoomEvent implements RoomEvent {

    private final String name;

    /**
     * 
     * @param name
     *  the name of the room event
     */
    public AbstractRoomEvent(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public abstract RoomEventType getType();

    @Override
    public final String getName() {
        return name;
    }

}
