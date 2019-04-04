package model.combat.tag;

public enum EffectTag implements Tag {

    NORMAL_DAMAGE("Normal damage"),
    POISON_DAMAGE("Poisond damage");

    private String literal;

    EffectTag(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

}
