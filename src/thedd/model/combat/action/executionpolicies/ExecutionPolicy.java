package thedd.model.combat.action.executionpolicies;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;

/**
 * Component of the {@link Action} interface which dictates how
 * an action is executed, given a target.
 */
public interface ExecutionPolicy {

    /**
     * Applies the effects of the action to the target.
     * @param action the action to be executed
     * @param target the target of the action
     */
    void applyEffects(Action action, ActionActor target);

    /**
     * Returns a copy of the current ExecutionPolicy.
     * @return a copy of the entity
     */
    ExecutionPolicy getCopy();

}
