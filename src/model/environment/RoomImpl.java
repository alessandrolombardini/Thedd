package model.environment;

import java.util.Collections;
import java.util.Set;

/**
 * Implementation of {@link model.environment.Room}.
 * 
 */
public class RoomImpl implements Room {

    private final Set<RoomEvent> events;

    /**
     * RoomImpl constructor.
     * 
     * @param events of the room
     */
    public RoomImpl(final Set<RoomEvent> events) {
        this.events = Collections.unmodifiableSet(events);
    }

    @Override
    public final boolean checkToMoveOn() {
        /*
         * Ritorna true solo se all'interno della stanza non sono presenti eventi
         * combattimento o altri tipi di eventi bloccanti
         */
        return false;
    }

    @Override
    public final Set<RoomEvent> getEvents() {
        return Collections.unmodifiableSet(this.events);
    }

    @Override
    public final boolean removeEvent(final RoomEvent roomEvent) {
        return this.events.remove(roomEvent);
    }

}
