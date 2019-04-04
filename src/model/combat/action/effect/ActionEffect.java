package model.combat.action.effect;

import model.combat.actor.ActionActor;
import model.combat.common.Modifiable;
import model.combat.common.SourceHolder;
import model.combat.common.TargetHolder;
import model.combat.tag.Taggable;

/**
 * 
 * The effect that an {@link Action} has on its target.
 * <p>
 * The effect can be applied to the target and may be change depending on who is 
 * executing the Action that has this effect and who is the target, depending on
 * their respective {@link Modifier}
 *
 */
public interface ActionEffect extends Modifiable, Taggable, SourceHolder, TargetHolder {

    /**
     * Applies the effect to the target.
     * @param target the target of the effect
     */
    void apply(ActionActor target);

    /**
     * Applies to the effect all the target's modifiers that accept this effect.
     * @param target the target of the effects
     */
    void updateEffectByTarget(ActionActor target);

    /**
     * Applies to the effect all the source's modifiers that accept this effect.
     * @param source the actor executing the action that has this effect
     */
    void updateEffectBySource(ActionActor source);

    /**
     * Returns a string describing what the effect did to the target.
     * @return a description of what the effect did
     */
    String getLogMessage();

    /**
     * Gets a text describing the effect.
     * @return a description of the action.
     */
    String getDescription();

    /**
     * Gets a text describing an updated (by target and source)
     * version of the effect.
     * @return a string previewing the effect
     */
    String getPreviewMessage();
}
