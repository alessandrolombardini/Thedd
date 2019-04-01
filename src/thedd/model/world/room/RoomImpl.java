package thedd.model.world.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.room_event.RoomEvent;

/**
 * Implementation of {@link thedd.model.world.Room}.
 * 
 */
public class RoomImpl implements Room {

    private final List<RoomEvent> events;

    /**
     * RoomImpl constructor.
     * 
     * @param events of the room
     * @throws NullPointerException if events is null
     */
    public RoomImpl(final List<RoomEvent> events) {
        Objects.requireNonNull(events);
        this.events = new ArrayList<>(events);
    }

    /**
     * RoomImpl constructor.
     */
    public RoomImpl() {
        this(Collections.<RoomEvent>emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean checkToMoveOn() {
        return events.stream().allMatch(event -> this.checkEventComplete(event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<RoomEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addEvent(final RoomEvent event) {
        Objects.requireNonNull(event);
        this.events.add(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addAllEvents(final List<RoomEvent> events) {
        Objects.requireNonNull(events);
        this.events.addAll(events);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean removeEvent(final RoomEvent event) {
        Objects.requireNonNull(event);
        if (this.checkEventComplete(event)) {
            return this.events.remove(event);
        }
        return false;
    }

    private boolean checkEventComplete(final RoomEvent event) {
        Objects.requireNonNull(event);
        return event.isCompleted() || event.isSkippable();
    }

    @Override
    public final int hashCode() {
        return events.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof RoomImpl) {
            final RoomImpl other = (RoomImpl) obj;
            return this.events.equals(other.events);
        }
        return false;
    }

    @Override
    public final String toString() {
        return "RoomImpl [events=" + events + "]";
    }

}
