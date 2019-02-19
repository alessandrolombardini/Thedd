package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.CombatInstance;
import combat.interfaces.Combatant;
import combat.interfaces.NPCCombatant;

public abstract class AbstractCombatant implements Combatant {

    private final String name;
    private Optional<Action> currentAction;
    private CombatInstance combatInstance;
    private List<? extends Action> actionList = new ArrayList<>();

    public AbstractCombatant(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Action> getAction() {
        return currentAction;
    }

    @Override
    public void setAction(final Action action) {
        currentAction = Optional.ofNullable(action);
    }

    @Override
    public void setTargets(final ActionActor target) {
        if (!getAction().equals(Optional.empty())) {
            final List<ActionActor> targetedParty = NPCCombatant.class.isInstance(target) ? combatInstance.getNPCsParty() : combatInstance.getPlayerParty();
            getAction().get().setTargets(target, targetedParty);
        }
    }

    @Override
    public int compareTo(final ActionActor other) {
        return this.getPriority() - other.getPriority();
    }

    @Override
    public int getPriority() {
        return 1; ///DA CAMBIARE: RIFERISCITI AL PERSONAGGIO
    }

    @Override
    public void setCombatInstance(final CombatInstance instance) {
        this.combatInstance = instance;
    }

    @Override
    public void getCharacter() {
        //DA CAMBIARE ANCHE NELL'INTERFACCIA
    } 

    @Override
    public void setAvailableActionsList(final List<? extends Action> actions) {
        actionList = Collections.unmodifiableList(actions);
    }

    @Override
    public List<? extends Action> getAvailableActionsList() {
        return Collections.unmodifiableList(actionList);
    }

    protected CombatInstance getCombatInstance() {
        return combatInstance;
    }

}
