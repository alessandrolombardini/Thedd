package model.combat.tag;

/**
 * Identifies all the possible values of {@link Tag}
 * related to statues.
 */
public enum StatusTag implements Tag {
    ;

    private final String literal;

    StatusTag(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

}
