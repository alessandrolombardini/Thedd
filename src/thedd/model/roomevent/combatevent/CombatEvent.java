package thedd.model.roomevent.combatevent;

import model.combat.interfaces.HostileEncounter;
import thedd.model.roomevent.RoomEvent;

/**
 *  Combat event. One should complete successfully this event before proceeding. 
 *
 */
public interface CombatEvent extends RoomEvent {

    /**
     * 
     * @return
     *  the hostile encounter instance
     */
    HostileEncounter getHostileEncounter();
}
