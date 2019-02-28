package model.room_event;

import model.combat.interfaces.ActionActor;

/**
 * Specialization of {@link model.room_event.RoomEvent}. It defines the "Contraption" {@link model.room_event.RoomEventType}.
 *
 */
public interface Contraption extends RoomEvent, ActionActor {

    /**
     * Complete the RoomEvent and make it no longer available.
     */
    void complete();
    
    /**
     * @return
     *  a new instance, copy of the contraption it is called on
     */
    Contraption copy();
}
