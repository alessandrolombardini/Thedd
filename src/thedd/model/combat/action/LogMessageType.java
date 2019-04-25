package thedd.model.combat.action;

import thedd.model.combat.actor.ActionActor;

/**
 * Interface for getting formatted text outputs describing
 * the consequences of an action.
 */
public interface LogMessageType {

    /**
     * Gets the log message relative to the action and its effects.
     * @param success whether the action was a hit or not
     * @param action the action to log
     * @param target the target of the action
     * @return a message describing the effects of the provided action relative to the target
     */
    String getLogMessage(boolean success, Action action, ActionActor target);

}
