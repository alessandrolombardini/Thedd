package thedd.model.item;

/**
 * 
 *
 */
public enum ItemRarityImpl implements ItemRarity {
    /**
     * The least rare. It has the highest weight.
     */
    COMMON("Common", 6),
    /**
     * The median rare. It has an average weight.
     */
    UNCOMMON("Uncommon", 3),
    /**
     * The rarest. It has the lowest weight.
     */
    RARE("Rare", 1);

    private final int baseWeight;
    private final String literal;

    ItemRarityImpl(final String literal, final int baseWeight) {
        this.baseWeight = baseWeight;
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public int getBaseWeight() {
        return baseWeight;
    }
}
