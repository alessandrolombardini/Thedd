package model.combat.logic;

import java.util.List;

import model.combat.action.result.ActionResult;
import model.combat.actor.ActionActor;
import model.combat.common.CombatInstance;
import model.combat.common.CombatStatus;

/**
 * The part of the model that dictates how the combat will flow.<br>
 * It holds all the actors in a queue and it can interact with a provided
 * Combat Instance.
 */
public interface CombatLogic {

    /**
     * Sets the current combat instance.
     * @param newInstance the new instance
     */
    void setCombatInstance(CombatInstance newInstance);

    /**
     * Returns the current combat status.
     * @return the current status of the combat
     */
    CombatStatus getCombatStatus();

    /**
     * Executes the next action in the queue, eventually applies its effects
     * to the targets and updates the combat status.
     * @return the Result of the action
     */
    ActionResult executeNextAction(); 

    /**
     * Resets the combat instance.
     */
    void resetCombat();

    /**
     * Sets the combat status accordingly and prepares the first round.
     */
    void startCombat();

    /**
     * Sets up the next round.
     */
    void prepareNextRound();

    /**
     * If it has an action set, adds the actor to the queue.
     * @param actor the actor to be added
     */
    void addActorToQueue(ActionActor actor);

    /**
     * Returns whether the current round is ready to be executed.
     * @return a boolean signaling whether the round is ready to be executed
     */
    boolean isRoundReady();

    /**
     * Returns the list of the actors involved in the combat, ordered by actors' initiative.
     * @return the ordered list of actors
     */
    List<ActionActor> getOrderedActorsList();

    //boolean canSelectAction(ActionActor actor) //maybe better put in Combatant/Character

    //void applyModifiers();
}
