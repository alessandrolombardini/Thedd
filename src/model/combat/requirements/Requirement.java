package model.combat.requirements;

/**
 * A condition that must be met.
 * @param <T> the type associated with this requirement
 */
public interface Requirement<T> {

    /**
     * Gets whether or not the underlying condition is fulfilled.
     * @param testedEntity the entity to test for the condition
     * @return true if the condition was met, false otherwise
     */
    boolean isFulfilled(T testedEntity);

    /**
     * Gets whether this requirement should be seen by the player.
     * @return true if the requirement is to be displayed, false otherwise
     */
    boolean isHidden();

}
