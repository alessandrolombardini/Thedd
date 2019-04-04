package model.combat.tag;

/**
 * Tags that an {@link ActionActor} can get when
 * a {@Status} is applied.
 */
public enum StatusTag implements Tag {

    POISONED("Poisoned");

    private String literal;

    StatusTag(final String literal) {
        this.literal = literal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLiteral() {
        return literal;
    }

}
