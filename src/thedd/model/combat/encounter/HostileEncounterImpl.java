package thedd.model.combat.encounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import thedd.model.combat.actionexecutor.ActionExecutor;
import thedd.model.combat.actor.ActionActor;

/**
 * Implementation of the HostileEncounter interface.
 */
public final class HostileEncounterImpl implements HostileEncounter {

    private ActionExecutor combatLogic;
    private final Set<ActionActor> npcs = new LinkedHashSet<>();
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
    public void addAll(final Set<ActionActor> characters) {
        npcs.addAll(npcs);
    }

    @Override
    public void setCombatLogic(final ActionExecutor combatLogic) {
        this.combatLogic = Objects.requireNonNull(combatLogic);
    }

    @Override
    public Set<ActionActor> getNPCs() {
        return Collections.unmodifiableSet(npcs);
    }

}
