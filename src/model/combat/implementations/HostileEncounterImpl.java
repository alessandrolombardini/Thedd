package model.combat.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.combat.interfaces.ActionActor;
import model.combat.interfaces.CombatInstance;
import model.combat.interfaces.CombatLogic;
import model.combat.interfaces.HostileEncounter;

/**
 * Implementation of the HostileEncounter interface.
 */
public final class HostileEncounterImpl implements HostileEncounter {

    private Optional<CombatLogic> combatLogic = Optional.empty();
    private CombatInstance instance = new CombatInstanceImpl();

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
    public HostileEncounterImpl(final CombatLogic combatLogic, final List<ActionActor> npcParty) {
        this.combatLogic = Optional.ofNullable(combatLogic);
        instance.addNPCsPartyMembers(npcParty);
    }

    @Override
    public CombatLogic getCombatLogic() {
        return combatLogic.get();
    }

    @Override
    public void addNPC(final ActionActor character) {
        instance.addNPCsPartyMember(character);
    }

    @Override
    public void addAll(final List<ActionActor> characters) {
        instance.addNPCsPartyMembers(characters);
    }

    @Override
    public void setCombatLogic(final CombatLogic combatLogic) {
        this.combatLogic = Optional.ofNullable(combatLogic);
        if (this.combatLogic.isPresent()) {
            this.combatLogic.get().setCombatInstance(instance);
        }
    }

}
