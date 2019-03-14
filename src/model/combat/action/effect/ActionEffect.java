package model.combat.action.effect;

import java.util.Set;

import model.combat.actor.ActionActor;
import model.combat.common.Modifiable;
import model.combat.tag.Tag;

/**
 * 
 * The effect that and Action has on its target.
 * <p>
 * The effect can be applied to the target and may change depending on who is 
 * executing the Action that has this effect and who is the target.<br>
 * It is also modified by its various {@link EffectModifiers}.
 *
 */
public interface ActionEffect extends Modifiable {

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
     * Gets the Set of target's {@link Tag}.
     * @return the target's tags
     */
    Set<Tag> getTargetTags();

    /**
     * Gets the Set of source's {@link Tag}.
     * @return the source's tags
     */
    Set<Tag> getSourceTags();

    /**
     * Gets the Set of {@link Tag} of this effect.
     * @return the effect's tags
     */
    Set<Tag> getTags();

    /**
     * Adds the specified {@link Tag} to the effect.
     * @param tag the tag to be added
     * @param isPermanent true to make the argument tag not removable, false otherwise
     */
    void addTag(Tag tag, boolean isPermanent);

    /**
     * Adds the elements of the specified Set to the effect's {@link Tag} Set.
     * @param tags the tags to be added
     * @param arePermanent true to make the argument tags not removable, false otherwise
     */
    void addTags(Set<Tag> tags, boolean arePermanent);

    /**
     * Removes the specified {@link Tag} if it's not permanent.
     * @param tag the tag to be removed
     * @return true if the tag is removed, false otherwise
     */
    boolean removeTag(Tag tag);

    /**
     * Returns a string describing what the effect did to the target.
     * <p>
     * Example:<br>
     * return "Dealt " + damage + " HP damage";
     * @return a description of what the effect did
     */
    String getLogMessage();

    /**
     * Gets a text describing the effect.
     * <p>
     * Example:<br>
     * "Deals " damage " HP damage";<br>
     * @return a description of the action.
     */
    String getDescription();
}
