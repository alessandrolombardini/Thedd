package model.environment.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.room_event.RoomEvent;

/**
 * Implementation of {@link model.environment.Room}.
 * 
 */
public class RoomImpl implements Room {

    private final List<RoomEvent> events;

    /**
     * RoomImpl constructor.
     * @param events of the room
     */
    public RoomImpl(final List<RoomEvent> events) {
        this.events = new ArrayList<>(events);
    }

    /**
     * RoomImpl constructor.
     */
    public RoomImpl() {
        this(new ArrayList<>());
    }

    @Override
    public final boolean checkToMoveOn() {
        return events.stream().allMatch(event -> this.checkEventComplete(event));
    }

    @Override
    public final List<RoomEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    @Override
    public final void addEvent(final RoomEvent event) {
        Objects.requireNonNull(event);
        this.events.add(event);
    }

    @Override
    public final void addAllEvents(final List<RoomEvent> events) {
        this.events.addAll(events);
    }

    @Override
    public final boolean removeEvent(final RoomEvent event) {
        Objects.requireNonNull(event);
        if (this.checkEventComplete(event)) {
            return this.events.remove(event);
        }
        return false;
    }

    private boolean checkEventComplete(final RoomEvent event) {
        return event.isCompleted() || event.isSkippable();
    }

    @Override
    public final int hashCode() {
        return Objects.nonNull(events) ? events.hashCode() : 0;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!Objects.nonNull(obj) || !(obj instanceof RoomImpl)) {
            return false;
        } else if (obj == this) {
            return true;
        }
        final RoomImpl other = (RoomImpl) obj;
        if (!Objects.nonNull(other.events) && Objects.nonNull(this.events)) {
            return false;
        } else if (!this.events.equals(other.events)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "RoomImpl [events=" + events + "]";
    }

}
