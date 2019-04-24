package thedd.model.combat.requirements.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import thedd.model.combat.common.SourceHolder;
import thedd.model.combat.requirements.AbstractRequirement;
import thedd.model.combat.tag.Tag;
import thedd.model.combat.tag.Taggable;

/**
 * A decorator for a {@link TagRequirement}.<p>
 * It checks, as per {@link TagRequirement#isFulfilled} if the source of the
 * provided {@link SourceHolder} fulfills the Requirement. 
 * @param <T> the type of the entities to be checked
 */
public class SourceTagRequirement<T extends SourceHolder> extends AbstractRequirement<T> {

    private final TagRequirement<Taggable> condition;
    private final TagRequirementType rType;
    private final List<Tag> targetTags = new ArrayList<Tag>();

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTags a list of tags to look for
     */
    public SourceTagRequirement(final boolean hidden, final TagRequirementType rType, final List<Tag> targetTags) {
        super(hidden);
        this.rType = rType;
        condition = new TagRequirement<Taggable>(hidden, rType, targetTags);
        targetTags.addAll(targetTags);
    }

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTag a tag to look for
     */
    public SourceTagRequirement(final boolean hidden, final TagRequirementType rType, final Tag targetTag) {
        super(hidden);
        this.rType = rType;
        condition = new TagRequirement<Taggable>(hidden, rType, targetTag);
        targetTags.add(targetTag);
    }

    /**
     * Gets a representation of the requirement.
     */
    @Override
    public String toString() {
        String conditionString;
        switch (rType) {
        case ALLOWED:
            conditionString = " must have ";
            break;
        case REQUIRED:
            conditionString = " must not have ";
            break;
        case UNALLOWED:
            conditionString = " must have one or more of ";
            break;
        default:
            conditionString = "THIS SHOULD NOT BE SEEN";
            break;
        }
        return "Actor" + conditionString + "the following conditions: " + targetTags.stream()
                                                                                    .map(t -> t.getLiteral())
                                                                                    .collect(Collectors.joining("/"));
    }

    /**
     * If the testedEntiy has a source, checks (as per {@link TagRequirement#isFulfilled})
     * if it meets the condition.
     */
    @Override
    public boolean isFulfilled(final T testedEntity) {
        if (testedEntity == null || !testedEntity.getSource().isPresent()) {
            return false;
        }
        return condition.isFulfilled(testedEntity.getSource().get());
    }

}
