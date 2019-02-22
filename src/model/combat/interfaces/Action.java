package model.combat.interfaces;

import java.util.List;

import model.combat.enums.TargetType;

/**
 * An action that can be selected and executed in and out of combat.
 * <p>
 * An action has a source ActionActor that executes it and one or more target ActionActors,
 * it provides also a base bonus or malus to the hit chance.
 * <p>
 * It can store one or more ActionEffects
 * <p>
 * An action can either target the actor's enemies, allies, just itself or even everyone
 */
public interface Action {
    //anche metodi per rimuovere gli effetti magari

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
     * Set the source Actor of the action.
     * @param source the Actor 
     */
    void setSource(ActionActor source); //ActionActor può esserlo anche un interagibile

    /**
     * Returns the Actor that is capable of performing this action.
     * @return the source Actor
     */
    ActionActor getSource();

    /**
     * Returns the effects linked to this action.
     * @return a List of the action's effects
     */
    List<ActionEffect> getEffects();

    /**
     * Adds one or more to the action.
     * @param effects a list of effects
     */
    void addEffects(List<ActionEffect> effects);

    /**
     * Adds one effect to the action.
     * @param effect the effect
     */
    void addEffect(ActionEffect effect);

    /**
     * Applies all of the action's effects to the target.
     * @param target the target of the action
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

}
