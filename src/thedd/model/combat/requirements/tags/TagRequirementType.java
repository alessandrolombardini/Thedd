package thedd.model.combat.requirements.tags;

/**
 * The type of filters to be used by a {@link TagRequirement} to
 * decide whether it's fulfilled or not.
 */
public enum TagRequirementType {

    /**
     * The requirement is met if it wasn't looking for any tags or if
     * at least one of the target tags is present in the tested Taggable
     * entity.
     */
    ALLOWED,
    /**
     * The requirement is met if it wasn't looking for any tags or if
     * all the target tags are present in the tested Taggable
     * entity.
     */
    REQUIRED,
    /**
     * The requirement is met if it wasn't looking for any tags or if
     * all the target tags are missing from the tested Taggable
     * entity.
     */
    UNALLOWED

}
