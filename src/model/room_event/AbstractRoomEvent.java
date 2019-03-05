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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (this.getType().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
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
