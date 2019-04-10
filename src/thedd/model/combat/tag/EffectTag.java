package thedd.model.combat.tag;

/**
 * Identifies all the possible values of {@link Tag}
 * related to effects.
 */
public enum EffectTag implements Tag {

    /**
     * To be applied to effects that deal normal physical damage.
     */
    NORMAL_DAMAGE("Normal damage", false),
    /**
     * To be applied to effects that deal poison damage.
     */
    POISON_DAMAGE("Poison damage", false),
    /**
     * TO be applied to effects that cannot be modified.
     */
    IGNORES_MODIFIERS("Ignores modifiers", true);

    private final String literal;
    private final boolean hidden;

    EffectTag(final String literal, final boolean hidden) {
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
