package thedd.model.roomevent;

/**
 * Possible events that can be found in a room.
 *
 */
public enum RoomEventType {
    /**
     * A combat event, a combat should start when there is an event of this type in a room.
     */
    COMBAT_EVENT, 
    /**
     * An event which allow the player to change floor.
     */
    FLOOR_CHANGER_EVENT, 
    /**
     * An event that perform an {@link model.combat.Action}.
     */
    INTERACTABLE_ACTION_PERFORMER
}
