package model.combat.actor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.combat.action.Action;
import model.combat.logic.CombatLogic;
import model.combat.modifier.Modifier;
import model.combat.tag.Tag;

/**
 * 
 * An entity capable of performing Actions in and out of combat.<br>
 * It also holds collections of Statuses, {@link Modifier} and {@link Tag}.
 * 
 */
public interface ActionActor {

    /**
     * Returns the literal name of the actor.
     * @return the name of the actor
     */
    String getName();

    /**
     * Returns an Optional containing the {@link Action} that the actor is going to actively execute.
     * @return the current action, Optional.empty() if no action is present
     */
    Optional<Action> getAction();

    /**
     * Sets the {@link Action} that the actor is going to actively execute.
     * @param action the next action of the actor
     */
    void setAction(Action action);

    /**
     * Adds the specified {@link Action} to the available set of Actions of the actor.
     * @param action the action to be added
     */
    void addAction(Action action);

    /**
     * Removes, if present, the specified {@link Action} from the actor's collection
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
     * Sets what {@link Action} the actor is capable of executing.
     * <p>
     * @param actions the list of available actions
     */
    void setAvailableActions(Set<? extends Action> actions);

    /**
     * Returns the list of available {@link Action}.
     * @return the list of actions
     */
    //List<Action> getAvailableActions();

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
     * in the current turn queue, as set by the {@link CombatLogic}.
     * Otherwise, it returns 0.
     * @return the index of the character in the current turn queue
     */
    int getPlaceInRound();

    /**
     * If the actor is in combat, sets the value to be returned by {@link ActionActor.getPlaceInRound}
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

    /**
     * Gets the Set of {@link Tag} of the Actor.
     * @return a Set of Tags 
     */
    Set<Tag> getTags();

    /**
     * Adds one or more {@link Tag} to the Actor's Set.
     * @param tags the Set of tags to be added
     * @param arePermanent true if the tags are going to be not removable, false otherwise
     */
    void addTags(Set<Tag> tags, boolean arePermanent);

    /**
     * Adds a {@link Tag} to the Actor's Set.
     * @param isPermanent true if the tag is going to be not removable, false otherwise
     * @param tag the tag to be added
     */
    void addTag(Tag tag, boolean isPermanent);

    /**
     * Removes a {@link Tag} to the Actor's Set, if the the provided tag is not permanent.
     * @param tag the tag to be removed
     * @return true if the tag was removed, false otherwise
     */
    boolean removeTag(Tag tag);

    /**
     * Adds a {@link Modifier} to the actor.
     * @param modifier the modifier to be added
     * @param isPermanent true if the modifier is going to be not removable, false otherwise
     */
    void addModifier(Modifier modifier, boolean isPermanent);

    /**
     * Removes a {@link Modifier} to the actor, if it's not permanent.
     * @param modifier the modifier to be added
     */
    void removeModifier(Modifier modifier);

    /**
     * Gets the Set of {@link Modifier} applicable to {@link Action}.
     * @return the set of action modifiers
     */
    Set<Modifier> getActionModifiers();

    /**
     * Gets the Set of {@link Modifier} applicable to {@link ActionEffect.
     * @return the set of effect modifiers
     */
    Set<Modifier> getEffectModifiers();

    /**
     * Gets a list containing the available actions of the actor.
     * @return the list of actions
     */
    List<Action> getAvailableActionsList();

    /**
     * Gets a set containing the available actions of the actor.
     * @return the set of actions
     */
    Set<Action> getAvailableActionsSet();
}
