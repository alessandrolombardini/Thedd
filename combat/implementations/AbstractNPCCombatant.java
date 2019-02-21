package combat.implementations;

import java.util.List;
import java.util.Random;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.AutomaticActionActor;
import utils.RandomCollection;

public abstract class AbstractNPCCombatant extends AbstractCombatant implements AutomaticActionActor {

    public AbstractNPCCombatant(final String name) {
        super(name);
    }

    @Override
    public RandomCollection<Action> addWeightedAction(final Action action, final double weight) {
        return getWeightedActions().add(action, weight);
    }

    @Override
    public
    RandomCollection<Action> updateActionWeight(final Action action, final double newWeight) {
        getWeightedActions().updateItemWeight(action, newWeight);
        return getWeightedActions();
    }

    @Override
    public void setNextAction() {
        setAction(getWeightedActions().getNext());
    }

    @Override
    public void setNextTarget(final List<ActionActor> availableTargets) {
        setRandomTarget(availableTargets);
    }

    protected void setRandomTarget(final List<ActionActor> availableTargets) {
        final Random random = new Random();
        final int targetIndex = random.nextInt(availableTargets.size());
        getAction().get().setTargets(availableTargets.get(targetIndex), availableTargets);
    }

}
