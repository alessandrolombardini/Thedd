package model.combat.implementations;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import model.combat.enums.RandomActionPrority;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import utils.RandomCollection;
import utils.RandomSet;
import utils.RandomSetImpl;

public abstract class AbstractActionActor implements ActionActor {

    private final String name;
    private Optional<Action> currentAction;
    private final RandomSet<Action> availableActions = new RandomSetImpl<>();
    private int roundPlace;
    private boolean inCombat;

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
    public abstract int getPriority();

    @Override
    public void setAvailableActions(final Set<? extends Action> actions) {
        actions.forEach(a -> addAction(a));
    }

    @Override
    public Set<? extends Action> getAvailableActions() {
        return availableActions.getSet();
    }

    @Override
    public boolean equals(final Object other) {
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
        this.inCombat = isInCombat;
    }

    @Override
    public boolean isInCombat() {
        return inCombat;
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
        return availableActions;
    }

    @Override
    public void addAction(final Action action) {
        availableActions.add(action, RandomActionPrority.DEFAULT.getWeight());
    }

    @Override
    public boolean removeAction(final Action action) {
        return availableActions.remove(action);
    }

}
