package thedd.model.combat.tag;

/**
 * Implementation of the Tag interface.
 */
public class TagImpl implements Tag {

    private final String tag;
    private final boolean hidden;

    /**
     * Public constructors.
     * @param tag the string associated with this tag
     * @param hidden false if the Tag has to be shown to the player
     */
    public TagImpl(final String tag, final boolean hidden) {
        this.tag = tag;
        this.hidden = hidden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLiteral() {
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag)) {
            return false;
        }
        final Tag o = ((Tag) other);
        return tag.equals(o.getLiteral());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHidden() {
        return hidden;
    }
}
