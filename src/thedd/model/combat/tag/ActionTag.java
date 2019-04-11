package thedd.model.combat.tag;

/**
 * Identifies all the possible values of {@link Tag}
 * related to actions.
 */
public enum ActionTag implements Tag {

    /**
     * To be applied to offensive actions.
     */
    OFFENSIVE("Offensive action", false),
    /**
     * To be applied to defensive actions.
     */
    DEFENSIVE("Defensive action", false),
    /**
     * To be applied to actions that provide a buff.
     */
    BUFF("Buff", false),
    /**
     * To be applied to actions that provide a debuff.
     */
    DEBUFF("Debuff", false),
    /**
     * To be applied to actions that are not modifiable.
     */
    IGNORES_MODIFIERS("Ignores modifiers", true),
    /**
     * To be applied to actions that can block another action.
     */
    PARRY("Parry", true),
    /**
     * To be applied to actions that cannot be blocked by another action.
     */
    UNBLOCKABLE("Unblockable", true);

    private final String literal;
    private final boolean hidden;

    ActionTag(final String literal, final boolean hidden) {
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
