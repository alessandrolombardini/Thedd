package model.combat.actor;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import model.combat.action.Action;
import model.combat.action.TargetType;
import model.combat.action.effect.ActionEffect;
import model.combat.common.CombatInstance;
import model.combat.tag.Tag;
import utils.RandomPrority;
import utils.randomcollection.RandomCollection;
import utils.randomcollection.RandomSet;
import utils.randomcollection.RandomSetImpl;
import utils.randomcollection.weightedItem.WeightedItem;

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

    private RandomSet<Action> getRandomSet() {
        final RandomSet<Action> set = new RandomSetImpl<>();
        set.addAll(getAvailableActionsSet());
        return set;
    }

    private class WeightedAction implements Action, WeightedItem<Action> {

        private double weight;
        private Action action;

        WeightedAction(final Action action, final double weight) {
            this.weight = weight;
            this.action = action;
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
            return action;
        }

        @Override
        public void setTargets(final ActionActor target, final List<ActionActor> targetedParty) {
            action.setTargets(target, targetedParty);
        }

        @Override
        public void setSource(final ActionActor source) {
            action.setSource(source);
        }

        @Override
        public Optional<ActionActor> getSource() {
            return action.getSource();
        }

        @Override
        public List<ActionEffect> getEffects() {
            return action.getEffects();
        }

        @Override
        public void addEffects(final List<ActionEffect> effects) {
            action.addEffects(effects);
        }

        @Override
        public void addEffect(final ActionEffect effect) {
            action.addEffect(effect);
        }

        @Override
        public void removeEffect(final ActionEffect effect) {
            action.removeEffect(effect);
        }

        @Override
        public void applyEffects() {
            action.applyEffects();
        }

        @Override
        public String getName() {
            return action.getName();
        }

        @Override
        public List<ActionActor> getTargets() {
            return action.getTargets();
        }

        @Override
        public double getHitChanceModifier() {
            return action.getHitChanceModifier();
        }

        @Override
        public TargetType getTargetType() {
            return action.getTargetType();
        }

        @Override
        public void setTargetType(final TargetType targetType) {
            action.setTargetType(targetType);
        }

        @Override
        public boolean isTargetHit() {
            return action.isTargetHit();
        }

        @Override
        public void rollToHit() {
            action.rollToHit();
        }

        @Override
        public List<ActionActor> getValidTargets(final CombatInstance combatInstance) {
            return action.getValidTargets(combatInstance);
        }

        @Override
        public void addTag(final Tag tag) {
            action.addTag(tag);
        }

        @Override
        public void addTags(final Set<Tag> tags) {
            action.addTags(tags);
        }

        @Override
        public Set<Tag> getTags() {
            return action.getTags();
        }

        @Override
        public double getBaseHitChance() {
            return action.getBaseHitChance();
        }

        @Override
        public void selectNextTarget() {
            action.selectNextTarget();
        }

        @Override
        public ActionActor getCurrentTarget() {
            return action.getCurrentTarget();
        }

        @Override
        public String getLogMessage() {
            return action.getLogMessage();
        }

        @Override
        public String getDescription() {
            return action.getDescription();
        }

        @Override
        public boolean equals(final Object other) {
            return action.equals(other);
        }

        @Override
        public int hashCode() {
            return action.hashCode();
        }
    }

}
