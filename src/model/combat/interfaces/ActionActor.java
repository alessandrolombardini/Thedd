package model.combat.interfaces;

import java.util.Optional;
import java.util.Set;

/**
 * 
 * An entity capable of performing Actions in and out of combat.
 *
 */
public interface ActionActor {
    /**
     * Returns the literal name of the actor.
     * @return the name of the actor
     */
    String getName();

    /**
     * Sets the combat instance of the Actor.
     * @param instance the combatInstance
     */
    void setCombatInstance(CombatInstance instance); //arguably better placed inside AutomaticActionAcotor only

    /**
     * Returns the action that the actor is going to execute.
     * @return the current action, Optional.empty() if no action is present
     */
    Optional<Action> getAction();

    /**
     * Sets the action that the actor is going to execute.
     * @param action the next action of the actor
     */
    void setAction(Action action);

    /**
     * Adds the specified action to the available actions of the actor.<br>
     * If the action is already present in the actor's collections, the
     * previous action will be overwritten with the new one.
     * @param action the action to be added
     */
    void addAction(Action action);

    /**
     * Removes, if present, the specified action from the actor's collection
     * of available actions.
     * @param action the action to be removed
     * @return true if the action was removed, false otherwise.
     */
    boolean removeAction(Action action);

    /**
     * Override of default compareTo method.
     * <p>
     * Compares two actors by using their priority.<br>
     * A positive integer is returned if this actor has higher priority
     * than the other one's, negative if lower and 0 if the actors have same priority
     * @param other the other Actors
     * @return the result of the operation
     */
    int compareTo(ActionActor other);

    /**
     * Returns the actor's priority.
     * @return the actor's priority
     */
    int getPriority();

    /**
     * Sets what actions the actor is capable of executing.
     * <p>
     * @param actions the list of available actions
     */
    void setAvailableActions(Set<? extends Action> actions);

    /**
     * Returns the list of available actions.
     * @return the list of actions
     */
    Set<? extends Action> getAvailableActions();

    /**
     * Sets whether or not the actor is in combat.
     * @param isInCombat whether or not the actor is in combat
     */
    void setIsInCombat(boolean isInCombat);

    /**
     * Returns whether or not the actor is in combat.
     * @return true if the actor is currently in combat, false otherwise
     */
    boolean isInCombat();

    /**
     * If the actor is in combat, it returns his place (starting from 1) 
     * in the current turn queue.
     * Otherwise, it returns 0;
     * @return the index of the character in the current turn queue
     */
    int getPlaceInRound();

    /**
     * If the actor is in combat, sets the value to be returned by getPlaceInRound
     * method.
     * @param place an integer greater than 0
     */
    void setPlaceInRound(int place);
    //compareTo, se la source non è un interactable, compara l'agilità dei due personaggi. Se source è un interactable, ha default priorità minore
    //alternativa: ActionActor implementa metodo per ritornare una priorità (interactable ritornerà -1, personaggi la loro statistica, effetti duraturi una loro iniziativa (oppure -1 e gli interactable -2))

    /**
     * Resets the value to be shown by getPlaceInRound method.
     */
    void resetPlaceInRound();

}
