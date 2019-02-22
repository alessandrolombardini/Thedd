package combat.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import combat.enums.RandomActionPrority;
import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.CombatInstance;
import utils.RandomCollection;
import utils.RandomList;
import utils.RandomListImpl;

public abstract class AbstractActionActor implements ActionActor {

    private final String name;
    private Optional<Action> currentAction;
    private CombatInstance combatInstance;
    private final RandomList<Action> actionList = new RandomListImpl<>();
    private int roundPlace;
    private boolean isInCombat;

    public AbstractActionActor(final String name) {
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
    public void setAvailableActionsList(final List<? extends Action> actions) {
        actions.forEach(a -> actionList.add(a, RandomActionPrority.DEFAULT.getWeight()));
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
        if (!(other instanceof AbstractActionActor)) {
            return false;
        }
        final AbstractActionActor o = ((AbstractActionActor) other);
        return getName().equals(o.getName());
                //&& getAvailableActionsList().equals(o.getAvailableActionsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName()/*,getAvailableActionsList()*/);
    }

    @Override
    public void setIsInCombat(final boolean isInCombat) {
        this.isInCombat = isInCombat;
    }

    @Override
    public boolean isInCombat() {
        return isInCombat;
    }

    @Override
    public int getPlaceInRound() {
        return roundPlace;
    }

    @Override
    public void setPlaceInRound(final int place) {
        if (place > 0) {
            roundPlace = place;
        } else {
            throw new IllegalArgumentException("Number must be greater than 0");
        }
    }

    @Override
    public void resetPlaceInRound() {
        roundPlace = 0;
    }

    //PER MARTINA: se intendi rendere sia i personaggi giocanti che non giocanti capaci di eseguire azioni
    //             random fondendo le clasi AbstractCombatant e AbstractNPCCombatant, questo metodo non ti
    //             serve ad un cubo probabilmente e puoi semplicemente operare con actionList
    protected RandomCollection<Action> getWeightedActions() {
        return actionList;
    }
}
