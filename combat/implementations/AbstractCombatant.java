package combat.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.CombatInstance;
import combat.interfaces.Combatant;
import utils.RandomCollection;
import utils.RandomList;
import utils.RandomListImpl;

public abstract class AbstractCombatant implements Combatant {

    private final String name;
    private Optional<Action> currentAction;
    private CombatInstance combatInstance;
    private final RandomList<Action> actionList = new RandomListImpl<>();

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
        actions.forEach(a -> actionList.add(a, 1.0));
    }

    @Override
    public List<? extends Action> getAvailableActionsList() {
        return actionList.getList();
    }

    protected CombatInstance getCombatInstance() {
        return combatInstance;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AbstractCombatant)) {
            return false;
        }
        final AbstractCombatant o = ((AbstractCombatant) other);
        return getName().equals(o.getName())
                && getAvailableActionsList().equals(o.getAvailableActionsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),getAvailableActionsList());
    }
    
    //PER MARTINA: se intendi rendere sia i personaggi giocanti che non giocanti capaci di eseguire azioni
    //             random fondendo le clasi AbstractCombatant e AbstractNPCCombatant, questo metodo non ti
    //             serve ad un cubo probabilmente e puoi semplicemente operare con actionList
    protected RandomCollection<Action> getWeightedActions() {
        return actionList;
    }
}
