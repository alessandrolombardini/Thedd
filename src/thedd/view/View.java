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
     * Set scene of a given state.
     * 
     * @param state the state of application of the wanted view
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
     * 
     * @param action of targets
     */
    void resetActionTargets(Action action);

    /**
     * Show effect of an action.
     * 
     * @param result of the action
     * @param action to show
     */
    void showActionEffect(ActionResult result, Action action);

    /**
     * Show result of an action.
     * 
     * @param result the result to show
     * @param action to show
     */
    void showActionResult(ActionResult result, Action action);

    /**
     * Show inventory.
     */
    void showInventory();

    /**
     * Show action selector.
     */
    void showActionSelector();

}
