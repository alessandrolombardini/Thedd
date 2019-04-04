package model.combat.tag;

/**
 * Identifies all the possible values of {@link Tag}
 * related to actions.
 */
public enum ActionTag implements Tag {

    OFFENSIVE("Offensive action"),
    DEFENSIVE("Defensive action"),
    BUFF("Buff");

    private final String literal;

    ActionTag(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

}
