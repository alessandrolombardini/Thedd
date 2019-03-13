package model.combat.enums;

import model.combat.interfaces.Tag;

/**
 * Identifies all the possible values of {@link Tag}
 * related to actions.
 */
public enum ActionTag implements Tag {
    //TO BE FILLED
    ;

    private final String literal;

    ActionTag(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

}
