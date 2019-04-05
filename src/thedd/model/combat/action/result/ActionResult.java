package thedd.model.combat.action.result;

import java.util.AbstractMap.SimpleImmutableEntry;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;

import java.util.List;

/**
 * The result of an executed action. To be used by loggers or to decide what to show to the player.
 * <p>
 */
public interface ActionResult {

    /**
     * Returns the action that has been executed and must be analyzed.
     * @return the executed action.
     */
    Action getAction();

    /**
     * Adds a result to the list of the action results.
     * @param target the target of the action
     * @param result the result of the action specific to the target
     */
    void addResult(ActionActor target, ActionResultType result);

    /**
     * Returns a List of Pairs <target,ResultType>.
     * @return the list of results
     */
    List<SimpleImmutableEntry<ActionActor, ActionResultType>> getResults();
    //TODO:Might want to convert SimpleImmutableEntry to a library tuple/pair

}
