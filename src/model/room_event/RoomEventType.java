package model.room_event;

/**
 * Possible events that can be found in a room.
 *
 */
public enum RoomEventType {
    /**
     * A combat event, a combat should start when there is an event with this type in a room.
     */
    COMBAT, 
    /**
     * In the room there are stairs. If stairs are present in a room, then they should be the only event inside that room.
     */
    STAIRS, 
    /**
     * An event that perform an {@link model.combat.Action}.
     */
    CONTRAPTION
}
