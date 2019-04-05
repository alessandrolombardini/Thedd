package thedd.model.combat.action;

import java.util.List;

import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.common.Modifiable;
import thedd.model.combat.common.SourceHolder;
import thedd.model.combat.common.TargetHolder;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.tag.Taggable;

/**
 * An action that can be executed by an {@link ActionActor} in and out of combat, actively or passively.
 * <p>
 * An action has a source ActionActor that executes it and one or more target ActionActors,
 * it provides also a base bonus or malus to the hit chance.
 * <p>
 * It can store one or more {@link ActionEffect}
 * <p>
 * An action can either target the actor's enemies, allies, just itself or even everyone
 */
public interface Action extends Modifiable, Taggable, SourceHolder, TargetHolder {

    /**
     * Sets one or more targets to the action.
     * <p>
     * Once the main target it's selected the action, if it can target multiple actors,
     * will determine which of the other actors belonging to the provided list of actors
     * will be targeted
     * 
     * @param target the main target selected by the player
     * @param targetedParty List of the possible other targets
     */
    void setTargets(ActionActor target, List<ActionActor> targetedParty);

    /**
     * Returns the {@link ActionEffect} linked to this action.
     * @return a List of the action's effects
     */
    List<ActionEffect> getEffects();

    /**
     * Adds one or more {@link ActionEffect} to the action.
     * @param effects a list of effects
     */
    void addEffects(List<ActionEffect> effects);

    /**
     * Adds one {@link ActionEffect} to the action.
     * @param effect the effect to be added
     */
    void addEffect(ActionEffect effect);

    /**
     * Removes one {@link ActionEffect} to the action.
     * @param effect the targeted effect
     */
    void removeEffect(ActionEffect effect);

    /**
     * Applies all of the action's {@link ActionEffect} to the current target.
     * @param target the target on which to apply the effects
     */
    void applyEffects(ActionActor target);

    /**
     * Returns a literal name for the action.
     * @return the name of the action
     */
    String getName();

    /**
     * Returns a list containing all the action's targets.
     * @return a list of targets
     */
    List<ActionActor> getTargets();

    /**
     * Returns the action's chance to hit the provided
     * target (a value between 0.0 and 1.0).
     * @param target the tested target of the action
     * @return the chance to roll a hit against the provided target
     */
    double getHitChance(ActionActor target);

    /**
     * Returns the target type of this action.
     * @return the target type
     */
    TargetType getTargetType();

    /**
     * Sets the target type of this action.
     * @param targetType the target type
     */
    void setTargetType(TargetType targetType);

    /**
     * Gets whether or not the last roll to hit was a success.
     * @return the result of the last call to {@link #rollToHit}.
     */
    boolean isTargetHit();

    /**
     * Makes a roll to determine whether the provided target
     * is hit or not by the action.<p>
     * Updates the value returned by {@link #isTargetHit()}.
     * @param target the target to hit
     */
    void rollToHit(ActionActor target);

    /**
     * Returns the list of valid targets for this action.
     * @param instance the instance in which this action is executed
     * @return a collection of valid targets
     */
    List<ActionActor> getValidTargets(ActionExecutionInstance instance);

    /**
     * Gets the base hit chance of the Action.
     * @return the base value for the hit chance
     */
    double getBaseHitChance();

    /**
     * Gets a string describing the outcome of the action.
     * @param target the target of the action
     * @param success true if the action scored a hit, false otherwise
     * @return a description of of the outcome of the action
     */
    String getLogMessage(ActionActor target, boolean success);

    /**
     * Gets a text describing the action.
     * @return a description of the action.
     */
    String getDescription();

    /**
     * Adds the provided value to the action's hit-chance.
     * @param value the value to be added
     */
    void addToCurrentHitChance(double value);

    /**
     * Gets a copy of the action.
     * @return a copy of the action
     */
    Action getCopy();

    /**
     * Gets a preview of the effects of this action.
     * @param target the candidate target of this action
     * @return a preview of the effects of the action
     */
    String getEffectsPreview(ActionActor target);

    /**
     * Sets the source of the action.
     * @param source the new source of the action
     */
    void setSource(ActionActor source);

    /**
     * Gets the category of the action.
     * @return the category of the action
     */
    ActionCategory getCategory();

}
