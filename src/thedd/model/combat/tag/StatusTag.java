package thedd.model.combat.tag;

/**
 * Tags that an {@link ActionActor} can get when
 * a {@Status} is applied.
 */
public enum StatusTag implements Tag {

    /**
     * To be applied to statues that imbue a poisoning condition.
     */
    POISONED("Poisoned", false);

    private final String literal;
    private final boolean hidden;

    StatusTag(final String literal, final boolean hidden) {
        this.literal = literal;
        this.hidden = hidden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

}
