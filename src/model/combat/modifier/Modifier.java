package model.combat.modifier;

import java.util.Set;

import model.combat.action.Action;
import model.combat.action.effect.ActionEffect;
import model.combat.actor.ActionActor;
import model.combat.common.Modifiable;
import model.combat.tag.Tag;

/**
 * An entity that can modify the attributes of another entity based
 * on Tags.
 * <p>
 * Modifiable entities, such as {@link Action} and {@link ActionEffect}
 *  may have Tags and may provide the Tags of the entity
 *  (Example: an {@link ActionActor}) executing and being targeted by them.
 */
public interface Modifier {

    /**
     * Adds a {@link Tag} to the collection of tags of the Modifiable
     * that will be evaluated when running the accept method.
     * @param tag the tag to be added
     * @param isAllowed true if the modifiable must have the provided tag
     *        in order to be accepted, false if it must not.
     */
    void addModifiableTargetTag(Tag tag, boolean isAllowed);

    /**
     * Adds a {@link Tag} to the collection of tags of the Modifiable's
     * Target that will be evaluated when running the accept method.
     * @param tag the tag to be added
     * @param isAllowed true if the modifiable's target must have the provided tag
     *        in order to be accepted, false if it must not.
     */
    void addTargetActorTargetTag(Tag tag, boolean isAllowed);

    /**
     * Adds a {@link Tag} to the collection of tags of the Modifiable's
     * Source that will be evaluated when running the accept method.
     * @param tag the tag to be added
     * @param isAllowed true if the modifiable's source must have the provided tag
     *        in order to be accepted, false if it must not.
     */
    void addSourceActorTargetTag(Tag tag, boolean isAllowed);

    /**
     * Removes a {@link Tag} from the collection of tags of the Modifiable
     * that will be evaluated when running the accept method.
     * @param tag the tag to be removed
     * @param isAllowed true if the modifiable must have the provided tag
     *        in order to be accepted, false if it must not.
     * @return true if the tag is removed, false otherwise
     */
    boolean removeModifiableTargetTag(Tag tag, boolean isAllowed);

    /**
     * Removes a {@link Tag} from the collection of tags of the Modifiable's
     * Target that will be evaluated when running the accept method.
     * @param tag the tag to be removed
     * @param isAllowed true if the modifiable's target must have the provided tag
     *        in order to be accepted, false if it must not.
     * @return true if the tag is removed, false otherwise
     */
    boolean removeTargetActorTargetTag(Tag tag, boolean isAllowed);

    /**
     * Removes a {@link Tag} from the collection of tags of the Modifiable's
     * Source that will be evaluated when running the accept method.
     * @param tag the tag to be removed
     * @param isAllowed true if the modifiable's source must have the provided tag
     *        in order to be accepted, false if it must not.
     * @return true if the tag is removed, false otherwise
     */
    boolean removeSourceActorTargetTag(Tag tag, boolean isAllowed);

    /**
     * Gets the collection of targeted (allowed of not) Modifiable {@link Tag}.
     * @param allowed true to return the collection of allowed tags, false to
     *          return the one of unallowed ones.
     * @return the collection of specified tags
     */
    Set<Tag> getModifiableTargetTags(boolean allowed);

    /**
     * Gets the collection of targeted (allowed of not) Modifiable's target
     * {@link Tag}.
     * @param allowed true to return the collection of allowed tags, false to
     *          return the one of unallowed ones.
     * @return the collection of specified tags
     */
    Set<Tag> getTargetActorTargetTags(boolean allowed);

    /**
     * Gets the collection of targeted (allowed of not) Modifiable's source
     * {@link Tag}.
     * @param allowed true to return the collection of allowed tags, false to
     *          return the one of unallowed ones.
     * @return the collection of specified tags
     */
    Set<Tag> getSourceActorTargetTags(boolean allowed);

    /**
     * Sets the value of this modifier.
     * @param value the value of the modifier
     */
    void setValue(double value);

    /**
     * Sets if the value of the modifier should be treated as a percentage.
     * @param isPercentage true if the value is a percentage, false otherwise
     */
    void setIsPercentage(boolean isPercentage);

    /**
     * Gets the value of the modifier.
     * @return the value of the modifier
     */
    double getValue();

    /**
     * Gets whether or not the value of the modifier is treated as a percentage.
     * @return true if the value is a percentage, false otherwise
     */
    boolean isPercentage();

    /**
     * Gets the modifier activation condition.
     * @return the {@link ModifierActivation} of the modifier
     */
    ModifierActivation getModifierActivation();

    /**
     * Sets the {@link ModifierActivation} condition of the modifier
     * @param type the modifier activation condition
     */
    void setModifierActivation(ModifierActivation type);

    /**
     * Checks whether the Modifiable is accepted by the modifier and can be modified.
     * @param modifiable the Modifiable to be accepted
     * @return true if the parameter can be modified by this modifier, false otherwise
     */
    boolean accept(Modifiable modifiable);

    /**
     * Modifies the specified Modifiable.<br>
     * The accept method must be called beforehand to check if the modifiable is valid.
     * @param modifiable the modifiable to be modified
     */
    void modify(Modifiable modifiable);
}
