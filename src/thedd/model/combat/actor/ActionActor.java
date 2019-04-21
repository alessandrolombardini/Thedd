package thedd.model.combat.actor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.status.Status;
import thedd.model.combat.tag.Taggable;

/**
 * 
 * An entity capable of performing Actions in and out of combat.<br>
 * It also holds collections of {@link Status}, {@link Modifier} and {@link Tag}.
 * 
 */
public interface ActionActor extends Taggable {

    /**
     * Returns the literal name of the actor.
     * @return the name of the actor
     */
    String getName();

    /**
     * Sets the {@link Action} that the actor is going to actively execute.<br>
     * Only one action marked by selectedByActor = true may be inserted each round.
     * @param action the next action of the actor
     * @param selectedByActor set to true if the action was actively selected by the actor
     */
    void addActionToQueue(Action action, boolean selectedByActor);

    /**
     * Inserts an {@link Action} in the specified position of the
     * actor's action queue.
     * Only one action marked by selectedByActor = true may be inserted each round.
     * @param pos the position of the newly inserted action
     * @param action the action to be inserted
     * @param selectedByActor set to true if the action was 
     *        actively selected by the actor 
     */
    void insertActionIntoQueue(int pos, Action action, boolean selectedByActor);

    /**
     * Removes the {@link Action} from the actor's queue.
     * If the target action was the one selected by the actor, the value is reseted.
     * @param action to be removed
     */
    void removeActionFromQueue(Action action);

    /**
     * Returns an Optional containing the {@link Action} that the actor is going to actively execute.
     * @return the current action, Optional.empty() if no action is present
     */
    Optional<Action> getNextQueuedAction();

    /**
     * Gets the list of queued Actions.
     * @return the list of the actor's queued actions
     */
    List<Action> getActionQueue();

    /**
     * Adds the specified {@link Action} to the available set of Actions of the actor.
     * @param action the action to be added
     */
    void addActionToAvailable(Action action);

    /**
     * Removes, if present, the specified {@link Action} from the actor's collection
     * of available actions.
     * @param action the action to be removed
     * @return true if the action was removed, false otherwise.
     */
    boolean removeActionFromAvailable(Action action);

    /**
     * Sets what {@link Action} the actor is capable of executing.
     * <p>
     * @param actions the list of available actions
     */
    void setAvailableActions(Set<Action> actions);

    /**
     * Gets a list of copies of the actors' available actions.<br>
     * The copies are also modified by the actors' modifiers of the
     * RETRIEVING_ACTION type.<br>
     * Effects of the copies are also updated in the same manner.
     * @return the list of actions
     */
    List<Action> getAvailableActionsList();

    /**
     * Returns the actor's priority.
     * @return the actor's priority
     */
    int getPriority();

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
     * Gets the Set of {@link Modifier} applicable to {@link Action}.
     * @return the set of action modifiers
     */
    Set<Modifier<Action>> getActionModifiers();

    /**
     * Gets the Set of {@link Modifier} applicable to {@link ActionEffect.
     * @return the set of effect modifiers
     */
    Set<Modifier<ActionEffect>> getEffectModifiers();

    /**
     * Gets a collection of {@link Status} assigned to the actor.
     * @return the statuses of the actor
     */
    List<Status> getStatuses();

    /**
     * Adds a {@link Status} to the actor.<br>
     * It also adds the various {@link Tag} provided by
     * the status to the actor.
     * @param status the status to be added
     */
    void addStatus(Status status);

    /**
     * Removes a {@link Status} from the actor.<br>
     * It also removes the various {@link Tag} provided by
     * the status to the actor.
     * @param status the status to be removed
     */
    void removeStatus(Status status);

    /**
     * Gets the currently selected {@link Action}.
     * @return the action selected by the actor
     */
    Optional<Action> getSelectedAction();

    /**
     * Resets the selected action of the actor.
     */
    void resetSelectedAction();

    /**
     * Resets both the action queue and the current action of the actor.
     */
    void resetActionsQueue();

    /**
     * Adds a {@link Modifier} affecting {@link ActionEffect} entities to the actor.
     * @param modifier the modifier to be added
     * @param isPermanent true if the modifier is going to be not removable, false otherwise
     */
    void addEffectModifier(Modifier<ActionEffect> modifier, boolean isPermanent);

    /**
     * Removes a non permanent {@link Modifier} affecting {@link ActionEffect}
     *  entities from the actor.
     * @param modifier the modifier to be removed
     */
    void removeEffectModifier(Modifier<ActionEffect> modifier);

    /**
     * Adds a {@link Modifier} affecting {@link Action} entities to the actor.
     * @param modifier the modifier to be added
     * @param isPermanent true if the modifier is going to be not removable, false otherwise
     */
    void addActionModifier(Modifier<Action> modifier, boolean isPermanent);

    /**
     * Removes a non permanent {@link Modifier} affecting {@link Action} entities 
     * from the actor.
     * @param modifier the modifier to be removed
     */
    void removeActionModifier(Modifier<Action> modifier);

    /**
     * Gets whether the actor is part of the player's party.
     * @return true if the actor belongs to the player's party, false otherwise
     */
    boolean isInPlayerParty();

}
