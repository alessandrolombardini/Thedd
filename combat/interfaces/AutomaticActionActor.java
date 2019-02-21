package combat.interfaces;

import java.util.List;

import utils.RandomCollection;

/**
 * An actor capable of autonomously selecting an action and a target. 
 */
public interface AutomaticActionActor extends ActionActor {
 
    /**
     * Makes the actor select a new Action between the ones he possesses.
     */
    void setNextAction();

    /**
     * Makes the actor select a target between the ones provided in a list. 
     * @param availableTargets the available targets
     */
    void setNextTarget(List<ActionActor> availableTargets);

    /**
     * Adds an action to available actions of the actor and sets its weight
     * used in future random selections.
     * @param action the action to be added
     * @param weight a non negative value
     * @return the current collection
     */
    RandomCollection<Action> addWeightedAction(Action action, double weight);

    /**
     * Updates the value of the specified action with the specified amount.
     * @param action the selected action
     * @param newWeight the new weight of the action
     * @return the current collection
     */
    RandomCollection<Action> updateActionWeight(Action action, double newWeight);
}
