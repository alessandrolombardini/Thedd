package model.combat.interfaces;

/**
 * The effect that and Action has on its target.
 * <p>
 * The effect can be applied to the target and may change depending on who is 
 * executing the Action that has this effect and who is the target
 *
 */
public interface ActionEffect {

    /**
     * Applies the effect to the target.
     * @param target the target of the effect
     */
    void apply(ActionActor target);

    /**
     * Updates the magnitude of the effect depending on who
     * the target is.
     * @param target the target of the effects
     */
    void updateEffectByTarget(ActionActor target);

    /**
     * Updates the magnitude of the effect depending on who
     * the source is.
     * @param source the actor executing the action that has this effect
     */
    void updateEffectBySource(ActionActor source);

    /**
     * Returns a string describing what the effect did to the targets.
     * <p>
     * Example:<br>
     * return sourceName + " hits " + targetName + " dealing " + damage + " damage ";
     * @return a description of what the effect did
     */
    String getLogMessage();

}
