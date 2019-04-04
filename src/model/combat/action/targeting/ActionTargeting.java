package model.combat.action.targeting;

import java.util.List;

import model.combat.action.Action;
import model.combat.actor.ActionActor;
import model.combat.instance.ActionExecutionInstance;

/**
 * The component of an {@link Action} which dictates how that
 * action selects its target(s).
 */
public interface ActionTargeting {

    /**
     * Gets the list of targeted {@link ActionActor} given
     * a selected main target and the list of other valid targets.
     * @param target the main target, selected by the actor
     * @param availableTargets a list of other targetable actors
     * @return the list of targets chosen by the action
     */
    List<ActionActor> getTargets(ActionActor target, List<ActionActor> availableTargets);

    /**
     * Gets a list of valid targets for the parent {@link Action}. 
     * @param combatInstance the instance in which the action is executed
     * @param sourceAction the parent action
     * @return the list of valid targets
     */
    List<ActionActor> getValidTargets(ActionExecutionInstance combatInstance, Action sourceAction);

}
