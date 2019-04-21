package thedd.view.controller.interfaces;

import java.util.List;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;

/**
 * Controller methods to show something on the view.
 */
public interface ExplorationView {

    /**
     * Enable ActionActors to be targeted.
     * 
     * @param targetables all the possible targets
     * @param alliedParty the actors in the allied party
     * @param enemyParty  the actors in the enemy party
     * @param action      the action which needs a target
     */
    void showTargets(List<ActionActor> targetables, List<ActionActor> alliedParty, List<ActionActor> enemyParty,
                    Action action);

    /**
     * Reset the possible targets.
     */
    void hideTargets();

    /**
     * Visualize effects of the action to the log.
     * 
     * @param result the result to log
     */
    void logAction(List<ActionResult> result);

    /**
     * Show animations of the result of the action, if any.
     * 
     * @param result the result to show
     */
    void visualizeAction(ActionResult result);

    /**
     * 
     * @param text
     */
    void showUserMessage(String text);

    /**
     * 
     */
    void hideUserMessage();

}
