package model.room_event;

import java.util.Objects;

/**
 * Abstract implementation of {@link.room_event.RoomEvent}.
 * Each specialization has to specify what type it is, whether it can be completed and whether it is mandatory.
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

    @Override
    public abstract boolean isCompleted();

    @Override
    public abstract boolean isSkippable();

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (this.getType().hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractRoomEvent other = (AbstractRoomEvent) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return this.getType() == other.getType();
    }
}
