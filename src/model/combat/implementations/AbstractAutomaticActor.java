package model.combat.implementations;

import java.util.List;
import java.util.Random;
import java.util.Set;

import model.combat.enums.RandomPrority;
import model.combat.enums.TargetType;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;
import model.combat.interfaces.AutomaticActionActor;
import model.combat.interfaces.CombatInstance;
import utils.randomCollection.RandomCollection;
import utils.randomCollection.RandomSet;
import utils.randomCollection.RandomSetImpl;
import utils.randomCollection.weightedItem.WeightedItem;

/**
 * Abstract implementation of AutomaticActionActor, also extends AbstractActionActor.
 */
public abstract class AbstractAutomaticActor extends AbstractActionActor implements AutomaticActionActor {

    public AbstractAutomaticActor(final String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<Action> addWeightedAction(final Action action, final RandomPrority weight) {
        addWeightedAction(action, weight.getWeight());
        return getRandomSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<Action> addWeightedAction(final Action action, final double weight) {
        final WeightedAction weightedAction = new WeightedAction(action, weight);
        super.addAction(weightedAction);
        return getRandomSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<Action> updateActionWeight(final Action action, final RandomPrority newWeight) {
        return updateActionWeight(action, newWeight.getWeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<Action> updateActionWeight(final Action action, final double newWeight) {
        final int index = getAvailableActionsList().indexOf(action);
        final WeightedAction wAction = ((WeightedAction) getAvailableActionsList().get(index));
        wAction.weight = newWeight;
        return getRandomSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextAction(final CombatInstance combatInstance) {
        setAction(getRandomSet().getNext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextTarget(final List<ActionActor> availableTargets) {
        setRandomTarget(availableTargets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvailableActions(final Set<? extends Action> actions) {
        actions.forEach(a -> addAction(a));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the weight of the action to RandomActionPrority.DEFAULT.
     */
    @Override
    public void addAction(final Action action) {
        final WeightedAction wAction = new WeightedAction(action, RandomPrority.DEFAULT.getWeight());
        super.addAction(wAction);
    }

    /**
     * Sets a random ActionActor as the target of the current Action.
     * @param availableTargets the available targets
     */
    protected void setRandomTarget(final List<ActionActor> availableTargets) {
        final Random random = new Random();
        final int targetIndex = random.nextInt(availableTargets.size());
        getAction().get().setTargets(availableTargets.get(targetIndex), availableTargets);
    }

    private class WeightedAction extends ActionImpl implements WeightedItem<Action> {

        private double weight;

        WeightedAction(final Action action, final double weight) {
            this(action.getSource().orElse(null),
                action.getName(), action.getEffects(), action.getBaseHitChance(),
                action.getTargetType(), weight);
            this.weight = weight;
        }

        WeightedAction(final ActionActor source, final String name, final List<ActionEffect> effects,
                final double baseHitChance, final TargetType targetType, final double weight) {
            super(source, name, effects, baseHitChance, targetType);
            this.weight = weight;
        }

        @Override
        public void setWeight(final double weight) {
            this.weight = weight;
        }

        @Override
        public double getWeight() {
            return weight;
        }

        @Override
        public Action getItem() {
            return this;
        }
    }

    private RandomSet<Action> getRandomSet() {
        final RandomSet<Action> set = new RandomSetImpl<>();
        set.addAll(getAvailableActionsSet());
        return set;
    }
}
