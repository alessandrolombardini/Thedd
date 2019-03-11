package model.room_event;

import model.combat.interfaces.HostileEncounter;

/**
 *  Combat event. One should complete successfully this event before proceeding. 
 *
 */
public interface Combat extends RoomEvent {

    /**
     * 
     * @return
     *  the hostile encounter instance
     */
    HostileEncounter getHostileEncounter();
}
