package thedd.model.combat.actor.automatic;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.utils.randomcollections.RandomCollection;
import thedd.utils.randomcollections.RandomPrority;

/**
 * An actor capable of autonomously selecting an action and a target. 
 */
public interface AutomaticActionActor extends ActionActor {

    /**
     * Adds an action to available actions of the actor and sets its weight
     * used in future random selections.
     * @param action the action to be added
     * @param weight a non negative value
     * @return the current collection
     */
    RandomCollection<Action> addWeightedAction(Action action, double weight);

    /**
     * Adds an action to available actions of the actor and sets its weight
     * used in future random selections.
     * @param action the action to be added
     * @param weight a non negative value
     * @return the current collection
     */
    RandomCollection<Action> addWeightedAction(Action action, RandomPrority weight);

    /**
     * Updates the value of the specified action with the specified amount.
     * @param action the selected action
     * @param newWeight the new weight of the action
     * @return the current collection
     */
    RandomCollection<Action> updateActionWeight(Action action, double newWeight);

    /**
     * Updates the value of the specified action with the specified amount.
     * @param action the selected action
     * @param newWeight the new weight of the action
     * @return the current collection
     */
    RandomCollection<Action> updateActionWeight(Action action, RandomPrority newWeight);

    /**
     * Lets the actor decide its' next Action and Target(s).
     * @param combatInstance the combat instance containing this actor
     */
    void selectNextMove(ActionExecutionInstance combatInstance);
}
