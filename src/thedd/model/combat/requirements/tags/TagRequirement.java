package thedd.model.combat.requirements.tags;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import thedd.model.combat.requirements.AbstractRequirement;
import thedd.model.combat.tag.Tag;
import thedd.model.combat.tag.Taggable;

/**
 * A requirement based on {@link Tag}s, it can look for required, allowed or not allowed tags in a {@link Taggable}.
 * @param <T> a Taggable entity
 */
public class TagRequirement<T extends Taggable> extends AbstractRequirement<T> {

    private final TagRequirementType rType;
    private final List<Tag> tags;

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTags a list of tags to look for
     */
    public TagRequirement(final boolean hidden, final TagRequirementType rType, final List<Tag> targetTags) {
        super(hidden);
        tags = targetTags;
        this.rType = rType;
    }

    /**
     * @param hidden true if the requirement must be hidden
     * @param rType the filter that will be applied to the provided target tags
     * @param targetTag a tag to look for
     */
    public TagRequirement(final boolean hidden, final TagRequirementType rType, final Tag targetTag) {
        super(hidden);
        tags = Collections.singletonList(targetTag);
        this.rType = rType;
    }

    /**
     * Gets a representation of the requirement.
     */
    @Override
    public String toString() {
        return rType.name() + ":" 
                + tags.stream()
                      .map(t -> t.getLiteral())
                      .collect(Collectors.joining(", ")); 
    }

    /**
     * Checks, based on the provided {@link TagRequirementType}, if the target tags are either
     * all present, not present or partially present.
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean isFulfilled(final T testedEntity) {
        if (testedEntity == null) {
            return false;
        }
        switch (rType) {
        case ALLOWED:
            return tags.isEmpty() || !Collections.disjoint(tags, testedEntity.getTags());
        case REQUIRED:
            return tags.isEmpty() || testedEntity.getTags().containsAll(tags);
        case UNALLOWED:
            return tags.isEmpty() || Collections.disjoint(tags, testedEntity.getTags());
        default:
            throw new IllegalStateException("This should not happen");
        }
    }
}
