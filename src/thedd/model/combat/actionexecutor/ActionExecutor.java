package thedd.model.combat.actionexecutor;

import java.util.List;
import java.util.Optional;

import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.instance.CombatStatus;

/**
 * The entity that dictates how the combat will flow.<br>
 * It holds all the actors in a queue and it can interact with a provided
 * Combat Instance.<p>
 * It decides by what order execute the queued actions and how to resolve
 * them.
 */
public interface ActionExecutor {

    /**
     * Sets the current combat instance.
     * @param newInstance the new instance
     */
    void setExecutionInstance(ActionExecutionInstance newInstance);

    /**
     * Sets the combat status accordingly and prepares the first round.
     */
    void startExecutor();

    /**
     * Sets up the next round.
     */
    void prepareNextRound();

    /**
     * Executes the next action in the queue.
     */
    void executeCurrentAction(); 

    /**
     * Prepares the next action to be executed.
     */
    void setNextAction();

    /**
     * Determines whether the current action hit,
     * missed or was blocked by every individual target.
     * @return the result of the action
     */
    Optional<ActionResult> evaluateCurrentAction();

    /**
     * Updates the status of the associated instance.
     */
    void updateExecutionStatus();

    /**
     * Returns the current instance status.
     * @return the current status of the combat
     */
    CombatStatus getExecutionStatus();

    /**
     * Adds the actor to the queue.
     * @param actor the actor to be added
     */
    void addActorToQueue(ActionActor actor);

    /**
     * Returns whether the current round is ready to be executed.
     * @return a boolean signaling whether the round is ready to be executed
     */
    boolean isRoundReady();

    /**
     * Returns an ordered list of the actors involved in the combat.
     * @return the ordered list of actors
     */
    List<ActionActor> getOrderedActorsList();

    /**
     * Gets a copy of the assigned {@link ActionExecutionInstance}.
     * @return the current combat instance
     */
    ActionExecutionInstance getExecutionInstance();

    /**
     * Gets whether an actor can currently act (and therefore if it
     * should select an action).
     * @param actor the actor to be tested
     * @return true if the actor can execute its action
     */
    boolean canActorAct(ActionActor actor);

}
