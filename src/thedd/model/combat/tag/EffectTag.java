package thedd.model.combat.tag;

public enum EffectTag implements Tag {

    NORMAL_DAMAGE("Normal damage"),
    POISON_DAMAGE("Poison damage"),
    FIRE_DAMAGE("Fire damage"),
    HOLY_DAMAGE("Holy damage");

    private String literal;

    EffectTag(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

}
