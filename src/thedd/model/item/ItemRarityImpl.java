package thedd.model.item;

import java.util.Objects;

/**
 * Implementation of {@link thedd.model.item.ItemRarity}.
 * It provides a basic rarity system with 3 tier of rarity.
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
        this.literal = Objects.requireNonNull(literal);
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
