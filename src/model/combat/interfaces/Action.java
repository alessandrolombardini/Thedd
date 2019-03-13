package model.combat.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.combat.enums.TargetType;

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
public interface Action extends Modifiable {

    //public boolean checkRequirements()
    /**
     * Sets one or more targets to the action.
     * <p>
     * Once the main target it's selected the action, if it can target multiple actors,
     * will determine which of the other actors belonging to the provided targeted party will be targeted
     * 
     * @param target the main target selected by the player
     * @param targetedParty List of the possible other targets
     */
    void setTargets(ActionActor target, List<ActionActor> targetedParty);

    /**
     * Set the source {@link ActionActor} of the action.
     * @param source the Actor 
     */
    void setSource(ActionActor source);

    /**
     * Returns the {@link ActionActor} that is capable of performing this action.
     * @return the source Actor
     */
    Optional<ActionActor> getSource();

    /**
     * Returns the {@link AtionEffect} linked to this action.
     * @return a List of the action's effects
     */
    List<ActionEffect> getEffects();

    /**
     * Adds one or more {@link AtionEffect} to the action.
     * @param effects a list of effects
     */
    void addEffects(List<ActionEffect> effects);

    /**
     * Adds one {@link AtionEffect} to the action.
     * @param effect the effect to be added
     */
    void addEffect(ActionEffect effect);

    /**
     * Removes one {@link AtionEffect} to the action.
     * @param effect the targeted effect
     */
    void removeEffect(ActionEffect effect);

    /**
     * Applies all of the action's {@link ActionEffect} to the current target.
     */
    void applyEffects();

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
     * Returns the action's modifier to the hitChance (a value between 0.0 and 1.0).
     * @return the hit chance modifier
     */
    double getHitChanceModifier();

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
     * Gets whether or not the target of the action is hit.
     * @return true if the target is hit, false otherwise.
     */
    boolean isTargetHit();

    /**
     * Returns the list of valid targets for this action.
     * @param combatInstance the combat instance in which this action is executed
     * @return a collection of valid targets
     */
    List<ActionActor> getValidTargets(CombatInstance combatInstance);

    /**
     * Adds a {@link Tag} to the Action.
     * @param tag the tag to be added
     */
    void addTag(Tag tag);

    /**
     * Adds one or more {@link Tag} to the Action.
     * @param tags the set of tags to be added
     */
    void addTags(Set<Tag> tags);

    /**
     * Gets the {@link Tag} of the Action.
     * @return the set of tags of the action
     */
    Set<Tag> getTags();

    /**
     * Gets the base hit chance modifier of the Action.
     * @return the base value for the hit chance modifier
     */
    double getBaseHitChance();

    /**
     * Sets the next target in the targets collection as the
     * current target for the action.
     */
    void selectNextTarget();

    /**
     * Gets the current target of the action.
     * @return the current target
     */
    ActionActor getCurrentTarget();

}
