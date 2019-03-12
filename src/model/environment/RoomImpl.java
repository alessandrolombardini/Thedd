package model.environment;

import java.util.Collections;
import java.util.List;

import model.room_event.RoomEvent;

/**
 * Implementation of {@link model.environment.Room}.
 * 
 */
public class RoomImpl implements Room {

    private final List<RoomEvent> events;

    /**
     * RoomImpl constructor.
     * 
     * @param events of the room
     */
    public RoomImpl(final List<RoomEvent> events) {
        this.events = Collections.unmodifiableList(events);
    }

    @Override
    public final boolean checkToMoveOn() {
        /*
         * Ritorna true solo se all'interno della stanza non sono presenti eventi
         * combattimento o altri tipi di eventi bloccanti
         */
        return events.stream().anyMatch(event -> !event.isCompleted());
    }

    @Override
    public final List<RoomEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }


}
