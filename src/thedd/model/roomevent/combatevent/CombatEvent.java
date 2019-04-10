package thedd.model.roomevent.combatevent;

import thedd.model.combat.encounter.HostileEncounter;
import thedd.model.roomevent.RoomEvent;

/**
 *  Combat event. One should complete successfully this event before proceeding. 
 *
 */
public interface CombatEvent extends RoomEvent {

    /**
     * Returns the {@link thedd.model.combat.encounter.HostileEncounter} inside this event.
     * @return
     *  the hostile encounter instance
     */
    HostileEncounter getHostileEncounter();
}
