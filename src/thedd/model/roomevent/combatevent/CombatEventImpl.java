package thedd.model.roomevent.combatevent;

import thedd.model.combat.encounter.HostileEncounter;
import thedd.model.combat.encounter.HostileEncounterImpl;
import thedd.model.combat.instance.CombatStatus;
import thedd.model.roomevent.AbstractRoomEvent;
import thedd.model.roomevent.RoomEventType;

/**
 * Implementation of {@link thedd.model.roomevent.combatevent.CombatEvent}.
 * This is not skippable.
 */
public final class CombatEventImpl extends AbstractRoomEvent implements CombatEvent {

    private static final String NAME = "Combat";
    private final HostileEncounter hostileEncounter;

    /**
     * Create a combat event with an empty {@link thedd.model.combat.encounter.HostileEncounter}
     * Enemies shall be added with {@link thedd.model.combat.encounter.HostileEncounter#addNPC(thedd.model.combat.actor.ActionActor)}.
     */
    public CombatEventImpl() {
        super(NAME);
        this.hostileEncounter = new HostileEncounterImpl();
    }

    @Override
    public RoomEventType getType() {
        return RoomEventType.COMBAT_EVENT;
    }

    @Override
    public HostileEncounter getHostileEncounter() {
        return hostileEncounter;
    }

    @Override
    public boolean isCompleted() {
        return hostileEncounter.getCombatLogic().getExecutionStatus() == CombatStatus.PLAYER_WON;
    }

    @Override
    public boolean isSkippable() {
        return false;
    }

}
