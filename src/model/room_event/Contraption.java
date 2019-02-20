package model.room_event;

import model.combat.Action;

/**
 * Specialization of {@link model.room_event.RoomEvent}. It defines the "Contraption" {@link model.room_event.RoomEventType}.
 *
 */
public interface Contraption extends RoomEvent {
    /**
     * 
     * @return
     *  the action to be performed to the activator of the Contraption.
     */
    Action getAction();
}
