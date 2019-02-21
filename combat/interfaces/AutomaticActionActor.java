package combat.interfaces;

import java.util.List;

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

}
