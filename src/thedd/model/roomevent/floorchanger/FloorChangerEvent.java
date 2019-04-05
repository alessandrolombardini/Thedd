package thedd.model.roomevent.floorchanger;

import thedd.model.roomevent.RoomEvent;

/**
 * An event that permit the player to change floor if a certain condition is met.
 *
 */
public interface FloorChangerEvent extends RoomEvent {
    /**
     * 
     * @return whether the condition is met
     */
    boolean isConditionMet();
}
