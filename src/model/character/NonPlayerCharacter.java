package model.character;

import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import model.combat.enums.RandomActionPrority;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.AutomaticActionActor;
import utils.RandomCollection;

/**
 * Class that implements a Non-Player Character.
 */
public class NonPlayerCharacter extends AbstractCharacter implements AutomaticActionActor {

    /**
     * NonPlayerCharacter's constructor.
     * 
     * @param basicStat , a map with basic statistics.
     * @param name      , the name of the non-player character.
     */
    public NonPlayerCharacter(final EnumMap<Statistic, StatValues> basicStat, final String name) {
        super(basicStat, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public final void setNextAction() {
        setAction(getWeightedActions().getNext());
    }

    @Override
    public final void setNextTarget(final List<ActionActor> availableTargets) {
        setRandomTarget(availableTargets);
    }

    @Override
    public final RandomCollection<Action> addWeightedAction(final Action action, final double weight) {
        return getWeightedActions().add(action, weight);
    }

    @Override
    public final RandomCollection<Action> updateActionWeight(final Action action, final double newWeight) {
        getWeightedActions().updateItemWeight(action, newWeight);
        return getWeightedActions();
    }

    @Override
    public final RandomCollection<Action> updateActionWeight(final Action action, final RandomActionPrority newWeight) {
        return updateActionWeight(action, newWeight.getWeight());
    }

    @Override
    public final RandomCollection<Action> addWeightedAction(final Action action, final RandomActionPrority weight) {
        return addWeightedAction(action, weight.getWeight());
    }

    private void setRandomTarget(final List<ActionActor> availableTargets) {
        final Random random = new Random();
        final int targetIndex = random.nextInt(availableTargets.size());
        getAction().get().setTargets(availableTargets.get(targetIndex), availableTargets);
    }

}
