package thedd.model.combat.actor.automatic;

import java.util.List;
import java.util.Random;
import java.util.Set;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionDecorator;
import thedd.model.combat.actor.AbstractActionActor;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.utils.randomcollections.RandomCollection;
import thedd.utils.randomcollections.RandomPrority;
import thedd.utils.randomcollections.set.RandomSet;
import thedd.utils.randomcollections.set.RandomSetImpl;
import thedd.utils.randomcollections.weightedItem.WeightedItem;

/**
 * Abstract implementation of AutomaticActionActor, also extends AbstractActionActor.
 */
public abstract class AbstractAutomaticActor extends AbstractActionActor implements AutomaticActionActor {

    /**
     * @param name the name of the actor
     */
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
        super.addActionToAvailable(weightedAction);
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
    public void setAvailableActions(final Set<Action> actions) {
        actions.forEach(a -> addActionToAvailable(a));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the weight of the action to RandomActionPrority.DEFAULT.
     */
    @Override
    public void addActionToAvailable(final Action action) {
        final WeightedAction wAction = new WeightedAction(action, RandomPrority.DEFAULT.getWeight());
        super.addActionToAvailable(wAction);
    }

    /**
     * Randomly selects one action from a weighted list and assigns a random 
     * available target.
     */
    @Override
    public void selectNextMove(final ActionExecutionInstance combatInstance) {
        final Action nextAction = getRandomSet().getNext();
        final List<ActionActor> availableTargets = nextAction.getValidTargets(combatInstance);
        addActionToQueue(nextAction, true);
        final ActionActor nextTarget = setRandomTarget(availableTargets);
        nextAction.setTargets(nextTarget, availableTargets);
    }

    /**
     * Sets a random ActionActor as the target of the current Action.
     * @param availableTargets the available targets
     * @return the randomly selected actor
     */
    protected ActionActor setRandomTarget(final List<ActionActor> availableTargets) {
        final Random random = new Random();
        final int targetIndex = random.nextInt(availableTargets.size());
        return availableTargets.get(targetIndex);
    }

    /**
     * Gets the the {@link RandomSet} containing the weighted actions.
     * @return the randomset which holds the actors' weighted actions
     */
    protected RandomSet<Action> getRandomSet() {
        final RandomSet<Action> set = new RandomSetImpl<>();
        set.addAll(getAvailableActionsList());
        return set;
    }

    private class WeightedAction extends ActionDecorator implements WeightedItem<Action> {

        private double weight;

        WeightedAction(final Action action, final double weight) {
            super(action);
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

        @Override
        public Action getCopy() {
            final Action copy = new WeightedAction(super.getCopy(), getWeight());
            return copy;
        }
    }

}
