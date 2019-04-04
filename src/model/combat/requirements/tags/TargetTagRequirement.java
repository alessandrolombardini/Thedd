package model.combat.requirements.tags;

import java.util.List;

import model.combat.common.TargetHolder;
import model.combat.requirements.AbstractRequirement;
import model.combat.tag.Tag;
import model.combat.tag.Taggable;

/**
 * A decorator for a {@link TagRequirement}.<p>
 * It checks, as per {@link TagRequirement#isFulfilled} if the target of the
 * provided {@link TargetHolder} fulfills the Requirement. 
 * @param <T> the type of the entities to be checked
 */
public class TargetTagRequirement<T extends TargetHolder> extends AbstractRequirement<T> {

    private final TagRequirement<Taggable> condition;

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTags a list of tags to look for
     */
    public TargetTagRequirement(final boolean hidden, final TagRequirementType rType, final List<Tag> targetTags) {
        super(hidden);
        condition = new TagRequirement<Taggable>(hidden, rType, targetTags);
    }

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTag a tag to look for
     */
    public TargetTagRequirement(final boolean hidden, final TagRequirementType rType, final Tag targetTag) {
        super(hidden);
        condition = new TagRequirement<Taggable>(hidden, rType, targetTag);
    }

    /**
     * Gets a representation of the requirement.
     */
    @Override
    public String toString() {
        //May be useful to return "Target must/might/must not have tags: + condition.toString()
        return condition.toString();
    }

    /**
     * If the testedEntiy has a target, checks (as per {@link TagRequirement#isFulfilled})
     * if it meets the condition.
     */
    @Override
    public boolean isFulfilled(final T testedEntity) {
        if (testedEntity == null || !testedEntity.getTarget().isPresent()) {
            return false;
        }
        return condition.isFulfilled(testedEntity.getTarget().get());
    }
}
