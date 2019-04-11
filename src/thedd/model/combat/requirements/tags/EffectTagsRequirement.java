package thedd.model.combat.requirements.tags;

import java.util.List;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.requirements.AbstractRequirement;
import thedd.model.combat.tag.Tag;

/**
 * A decorator for a {@link TagRequirement}.<p>
 * It checks, if any {@link ActionEffect} of the provided {@link Action}
 * fulfills the Requirement. 
 * @param <T> the type of the entities to be checked
 */
public class EffectTagsRequirement<T extends Action> extends AbstractRequirement<T> {

    private final TagRequirement<ActionEffect> condition;

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTags a list of tags to look for
     */
    public EffectTagsRequirement(final boolean hidden, final TagRequirementType rType, final List<Tag> targetTags) {
        super(hidden);
        condition = new TagRequirement<ActionEffect>(hidden, rType, targetTags);
    }

    /**
     * Gets a representation of the requirement.
     */
    @Override
    public String toString() {
        return condition.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFulfilled(final T testedEntity) {
        return testedEntity.getEffects().stream().anyMatch(condition::isFulfilled);
    }

}
