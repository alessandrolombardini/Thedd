package model.combat.encounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.combat.actionexecutor.ActionExecutor;
import model.combat.actor.ActionActor;

/**
 * Implementation of the HostileEncounter interface.
 */
public final class HostileEncounterImpl implements HostileEncounter {

    private ActionExecutor combatLogic;
    private List<ActionActor> npcs = new ArrayList<>();
    /**
     * Basic constructor. Initializes combatLogic with null and 
     * adds an empty list of enemies to the combat instance. 
     */
    public HostileEncounterImpl() {
        this(null, new ArrayList<ActionActor>());
    }

    /**
     * Constructor with arguments.
     * @param combatLogic the combat logic associated with this encounter.
     * @param npcParty a non null list of actors to placed against the player.
     */
    public HostileEncounterImpl(final ActionExecutor combatLogic, final List<ActionActor> npcParty) {
        this.combatLogic = combatLogic;
        npcs.addAll(npcParty);
    }

    @Override
    public ActionExecutor getCombatLogic() {
        if (combatLogic == null) {
            throw new IllegalStateException("ActionExecutor has not been initialized");
        }
        return combatLogic;
    }

    @Override
    public void addNPC(final ActionActor character) {
        npcs.add(character);
    }

    @Override
    public void addAll(final List<ActionActor> characters) {
        npcs.addAll(npcs);
    }

    @Override
    public void setCombatLogic(final ActionExecutor combatLogic) {
        this.combatLogic = Objects.requireNonNull(combatLogic);
    }

    @Override
    public List<ActionActor> getNPCs() {
        return Collections.unmodifiableList(npcs);
    }

}
