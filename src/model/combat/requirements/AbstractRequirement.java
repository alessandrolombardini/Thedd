package model.combat.requirements;

/**
 * Abstract implementation of the {@link Requirement} interface.
 * @param <T> the type associated with the requirement
 */
public abstract class AbstractRequirement<T> implements Requirement<T> {

    private final boolean hidden;

    /**
     * @param hidden true if the requirement must be hidden to the player
     */
    public AbstractRequirement(final boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean isFulfilled(T testedEntity);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHidden() {
        return hidden;
    }

}
