package thedd.model.combat.tag;

/**
 * Implementation of the Tag interface.
 */
public class TagImpl implements Tag {

    private final String tag;

    /**
     * Public constructors.
     * @param tag the string associated with this tag
     */
    public TagImpl(final String tag) {
        this.tag = tag;
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
}
