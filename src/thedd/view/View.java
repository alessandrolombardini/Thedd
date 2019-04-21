package thedd.view;

import java.util.List;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;

/**
 * Interface describing the view of the pattern MVC of this application.
 */
public interface View {

    /**
     * Set application state.
     * 
     * @param state the state of the application
     */
    void setState(ApplicationViewState state);

    /**
     * Update the view.
     */
    void update();

    /**
     * Show targets of an action.
     * 
     * @param targets     all the possible targets
     * @param alliedParty all the actors in the allied party
     * @param enemyParty  all the actors in the enemy party
     * @param action      the action
     */
    void showActionTargets(List<ActionActor> targets, List<ActionActor> alliedParty, List<ActionActor> enemyParty,
                           Action action);

    /**
     * Reset targets of an action.
     */
    void resetActionTargets();

    /**
     * Show effect of an action.
     * 
     * @param result of the action
     */
    void showActionEffect(ActionResult result);

    /**
     * Show result of an action.
     * 
     * @param result the result to show
     */
    void showActionResult(List<ActionResult> result);

    /**
     * Show inventory.
     */
    void showInventory();

    /**
     * Show action selector.
     */
    void showActionSelector();

    /**
     * Show a message to the player.
     * @param text
     *          the message to show
     */
    void showMessage(String text);

    /**
     * Hide a message previously shown.
     */
    void hideMessage();
    
    /**
     * Updates the statistic panel.
     */
    void partialUpdate();

}
